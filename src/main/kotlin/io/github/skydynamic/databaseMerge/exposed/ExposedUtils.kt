package io.github.skydynamic.databaseMerge.exposed

import com.google.gson.Gson
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

val gson = Gson()

fun String.toMap(): Map<String, String> {
    val map = gson.fromJson(this, Map::class.java)
    return if (map is Map<*, *>) map.mapKeys { it.key.toString() }.mapValues { it.value.toString() } else emptyMap()
}

class ExposedUtils(val databasePath: String) {
    private val database: Database = Database.connect(
        url = "jdbc:h2:file:$databasePath/QuickbackupMulti",
        driver = "org.h2.Driver"
    )

    init {
        transaction {
            SchemaUtils.create(StorageInfoTable, IndexFileTable, FileHashTable)
        }
    }

    fun insertStorageInfo(
        name: String,
        desc: String,
        timestamp: Long,
        useIncrementalStorage: Boolean,
        indexStorage: List<String>,
        collectionUuid: UUID
    ) {
        transaction(database) {
            StorageInfoTable.insert {
                it[StorageInfoTable.collectionUuid] = collectionUuid
                it[StorageInfoTable.name] = name
                it[StorageInfoTable.desc] = desc
                it[StorageInfoTable.timestamp] = timestamp
                it[StorageInfoTable.useIncrementalStorage] = useIncrementalStorage
                it[StorageInfoTable.indexStorage] = indexStorage
            }
        }
    }

    fun insertIndexFile(name: String, indexFileMap: Map<String, String>, collectionUuid: UUID) {
        transaction(database) {
            IndexFileTable.insert {
                it[IndexFileTable.collectionUuid] = collectionUuid
                it[IndexFileTable.name] = name
                it[IndexFileTable.indexFileMap] = gson.toJson(indexFileMap)
            }
        }
    }

    fun insertFileHash(name: String, fileHashMap: Map<String, String>, collectionUuid: UUID) {
        transaction(database) {
            FileHashTable.insert {
                it[FileHashTable.collectionUuid] = collectionUuid
                it[FileHashTable.name] = name
                it[FileHashTable.fileHashMap] = gson.toJson(fileHashMap)
            }
        }
    }
}