package io.github.skydynamic.databaseMerge

import io.github.skydynamic.databaseMerge.exposed.ExposedUtils
import io.github.skydynamic.databaseMerge.mongoDb.MongoDbUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.UUID

class MergeDatabase {
    val logger: Logger = LoggerFactory.getLogger(MergeDatabase::class.java)

    fun startMerge(
        mongoDbUtils: MongoDbUtils,
        exposedUtils: ExposedUtils
    ) {
        val collections = mongoDbUtils.mongoClient?.listDatabases()

        for (collection in collections!!) {
            val collectionName = collection.get("name").toString()
            println("Start to merge collection $collectionName")
            mongoDbUtils.newDataStore(collectionName)
            try {
                val storageInfo = mongoDbUtils.getAllStorageInfo()
                val indexFile = mongoDbUtils.getAllIndexFile()
                val fileHash = mongoDbUtils.getAllHashFile()

                val collectionUuid = UUID.nameUUIDFromBytes(collectionName.toByteArray())

                for (info in storageInfo) {
                    exposedUtils.insertStorageInfo(
                        info.name,
                        info.desc,
                        info.timestamp,
                        info.useIncrementalStorage,
                        info.indexStorage,
                        collectionUuid
                    )
                }

                for (file in indexFile) {
                    exposedUtils.insertIndexFile(
                        file.name,
                        file.indexFileMap,
                        collectionUuid
                    )
                }

                for (hash in fileHash) {
                    exposedUtils.insertFileHash(
                        hash.name,
                        hash.fileHashMap,
                        collectionUuid
                    )
                }
            } catch (e: Exception) {
                logger.error("Error when merge collection $collectionName", e)
            }
        }

        mongoDbUtils.mongoClient?.close()
        mongoDbUtils.mongoServer?.shutdown()

        println("Merge database success")
    }
}