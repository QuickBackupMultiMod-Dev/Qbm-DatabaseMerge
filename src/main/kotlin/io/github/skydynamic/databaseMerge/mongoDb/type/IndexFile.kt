package io.github.skydynamic.databaseMerge.mongoDb.type

import dev.morphia.annotations.Entity
import dev.morphia.annotations.Id
import org.bson.types.ObjectId

@Suppress("unused")
@Entity(value = "IndexFile", useDiscriminator = false)
class IndexFile {
    @Id
    private val id: ObjectId? = null
    var name: String = ""
    var indexFileMap: MutableMap<String, String> = mutableMapOf()

    constructor()

    constructor(name: String, indexFileMap: MutableMap<String, String>) {
        this.name = name
        this.indexFileMap = indexFileMap
    }
}
