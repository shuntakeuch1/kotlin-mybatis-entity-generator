package com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.generator

import com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.generator.entity.Table
import com.google.common.base.CaseFormat
import java.io.File
import java.io.PrintWriter

class EntityGenerator {
    var targetDirectory = "outputFile" // project root
    var isAllNullable = false

    companion object {
        const val rootDirectory = "./"
        const val indexString = "src/"
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

            if (targetDirectory.contains(indexString)) {
                val index = targetDirectory.indexOf(indexString)
                // TODO Support Windows
                val packageList = targetDirectory.substring(index + indexString.length).split("/")
                val packageName = packageList.joinToString(".")
                pw.println("package $packageName \n")
            }
            // add import
            if (it.isLocalDateTimeExist()) {
                pw.println("import java.time.LocalDateTime \n")
            }
            pw.println("class $className (${it.toColumnAllString(isAllNullable)} \n)")
            pw.flush()
            pw.close()
        }
    }
}
