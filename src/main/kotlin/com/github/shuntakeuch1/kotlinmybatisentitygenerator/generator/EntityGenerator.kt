package com.github.shuntakeuch1.kotlinmybatisentitygenerator.generator

import com.github.shuntakeuch1.kotlinmybatisentitygenerator.entity.Table
import java.io.File
import java.io.PrintWriter

class EntityGenerator {
    var targetDirectory = "outputFile" // project root

    companion object {
        const val rootDirectory = "./"
    }

    fun execute(tables: List<Table>) {
        // val newDir = File(rootDirectory + targetDirectory)
        val newDir = File(targetDirectory)
        if (newDir.mkdir()) {
            println("make directory")
        }
        tables.forEach { it ->
            val className = it.name.capitalize()
            val filename = "$className.kt"
            val newFile = File("$targetDirectory/$filename")

            if (newFile.createNewFile()) {
                println("make $filename")
            }
            val pw = PrintWriter(newFile)
            var fieldVariablesString = ""
            val columnLastIndex = it.columns.lastIndex
            for ((index, column) in it.columns.withIndex()) {
                fieldVariablesString += "\n val ${column.field.toString()}: ${column.typeConverter()}"
                if (index != columnLastIndex) {
                    fieldVariablesString += ","
                }
            }
            pw.println("package $targetDirectory \n")
            pw.println("class $className ($fieldVariablesString \n)")
            pw.flush()
            pw.close()
        }
    }
}