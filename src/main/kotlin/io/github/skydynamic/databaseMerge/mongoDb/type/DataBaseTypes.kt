package io.github.skydynamic.databaseMerge.mongoDb.type

@Suppress("unused")
enum class DataBaseTypes(val type: String, val cls: Class<*>) {
    FILES_HASHES("FileHash", FileHash::class.java),
    STORAGE_INFO("StorageInfo", StorageInfo::class.java),
    FILE_INDEX("IndexFile", IndexFile::class.java);
}
