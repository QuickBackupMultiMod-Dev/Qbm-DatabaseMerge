package io.github.skydynamic.databaseMerge

import com.github.ajalt.clikt.core.main

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        DatabaseMergeCommand().main(arrayOf("--help"))
        return
    }
    DatabaseMergeCommand().main(args)
}