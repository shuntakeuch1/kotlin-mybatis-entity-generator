package com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.generator

import com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.generator.entity.Table
import com.google.common.base.CaseFormat
import java.io.File
import java.io.PrintWriter

class EntityGenerator {
    var targetDirectory = "outputFile" // project root

    companion object {
        const val rootDirectory = "./"
    }

    fun execute(tables: List<Table>) {
        val newDir = File(targetDirectory)
        if (newDir.mkdir()) {
            println("make directory")
        }

        tables.forEach { it ->
            // to pascal
            val className = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, it.name)
            val filename = "$className.kt"
            val newFile = File("$targetDirectory/$filename")
            if (newFile.createNewFile()) {
                println("make $filename")
            }

            val pw = PrintWriter(newFile)
            pw.println("class $className (${it.toColumnAllString()} \n)")
            pw.flush()
            pw.close()
        }
    }
}
