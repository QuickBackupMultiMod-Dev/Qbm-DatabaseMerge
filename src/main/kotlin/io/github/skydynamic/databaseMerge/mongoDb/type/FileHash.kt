package io.github.skydynamic.databaseMerge.mongoDb.type

import dev.morphia.annotations.Entity
import dev.morphia.annotations.Id
import org.bson.types.ObjectId

@Suppress("unused")
@Entity(value = "FileHash", useDiscriminator = false)
class FileHash {
    @Id
    private val id: ObjectId? = null
    var name: String = ""
    var fileHashMap: MutableMap<String, String> = mutableMapOf()

    constructor()

    constructor(name: String, fileHashMap: MutableMap<String, String>) {
        this.name = name
        this.fileHashMap = fileHashMap
    }
}
