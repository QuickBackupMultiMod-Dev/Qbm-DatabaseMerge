package io.github.skydynamic.databaseMerge

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.boolean
import io.github.skydynamic.databaseMerge.exposed.ExposedUtils
import io.github.skydynamic.databaseMerge.mongoDb.MongoDbUtils
import java.io.File
import java.io.FileNotFoundException
import kotlin.system.exitProcess

class DatabaseMergeCommand : CliktCommand() {
    val internalDatabase: Boolean by option()
        .boolean()
        .default(true)
        .help("If use this option, should provide database file path")
    val databaseFilePath: String? by option()
        .help("The path of internal database file")
    val databaseUri: String by option()
        .default("mongodb://localhost:27017")
        .help("The uri of database")
    val exportPath: String by option()
        .default("./")
        .help("The path of export database file")

    override fun run() {
        if (internalDatabase && databaseFilePath == null) {
            println("Please provide database file path")
            return
        } else if (internalDatabase && databaseFilePath != null) {
            if (!File(databaseFilePath).exists()) {
                throw FileNotFoundException("Database file not found")
            }
        }

        val mongoDbUtils = MongoDbUtils(internalDatabase, databaseFilePath, databaseUri)
        mongoDbUtils.initDatabase()
        val exposedUtils = ExposedUtils(exportPath)
        MergeDatabase().startMerge(mongoDbUtils, exposedUtils)

        exitProcess(0)
    }
}