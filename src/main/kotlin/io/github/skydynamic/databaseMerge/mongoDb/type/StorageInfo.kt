package io.github.skydynamic.databaseMerge.mongoDb.type

import dev.morphia.annotations.Entity
import dev.morphia.annotations.Id
import org.bson.types.ObjectId

@Entity(value = "StorageInfo", useDiscriminator = false)
@Suppress("unused")
class StorageInfo {
    @Id
    private val id: ObjectId? = null
    var name: String = ""
    var desc: String = ""
    var timestamp: Long = 0
    var useIncrementalStorage: Boolean = false
    var indexStorage: MutableList<String> = mutableListOf()

    @Deprecated("") // Morphia only!
    constructor()

    constructor(name: String, desc: String, timestamp: Long, useIncrementalStorage: Boolean, indexStorage: MutableList<String>) {
        this.name = name
        this.desc = desc
        this.timestamp = timestamp
        this.useIncrementalStorage = useIncrementalStorage
        this.indexStorage = indexStorage
    }

    constructor(name: String, desc: String) {
        this.name = name
        this.desc = desc
    }
}
