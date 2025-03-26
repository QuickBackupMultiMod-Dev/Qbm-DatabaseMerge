package io.github.skydynamic.databaseMerge.mongoDb

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import de.bwaldvogel.mongo.MongoServer
import de.bwaldvogel.mongo.backend.h2.H2Backend
import dev.morphia.Datastore
import dev.morphia.Morphia
import dev.morphia.mapping.MapperOptions
import io.github.skydynamic.databaseMerge.mongoDb.type.FileHash
import io.github.skydynamic.databaseMerge.mongoDb.type.IndexFile
import io.github.skydynamic.databaseMerge.mongoDb.type.StorageInfo

class MongoDbUtils(
    val useInternalDatabase: Boolean,
    val databaseFilePath: String?,
    val databaseUri: String?
) {
    var mongoClient: MongoClient? = null
    var mongoServer: MongoServer? = null
    var datastore: Datastore? = null

    fun initDatabase() {
        if (useInternalDatabase) {
            val path = startInternalDatabase(databaseFilePath!!)
            if (path != null) {
                connectDatabase(path)
            }
        } else {
            connectDatabase(databaseUri!!)
        }
    }

    private fun connectDatabase(databaseUri: String) {
        mongoClient = MongoClients.create(databaseUri)
    }

    private fun startInternalDatabase(path: String): String? {
        mongoServer = MongoServer(H2Backend(path))
        mongoServer?.bind()
        return mongoServer?.connectionString
    }

    fun newDataStore(collectionName: String) {
        val mapperOptions = MapperOptions.builder()
            .storeEmpties(true)
            .storeNulls(true)
            .build()

        datastore = Morphia.createDatastore(mongoClient!!, collectionName, mapperOptions)
    }

    fun getAllStorageInfo(): List<StorageInfo> {
        return datastore?.find(StorageInfo::class.java)?.toList() ?: emptyList()
    }

    fun getAllIndexFile(): List<IndexFile> {
        return datastore?.find(IndexFile::class.java)?.toList() ?: emptyList()
    }

    fun getAllHashFile(): List<FileHash> {
        return datastore?.find(FileHash::class.java)?.toList() ?: emptyList()
    }
}