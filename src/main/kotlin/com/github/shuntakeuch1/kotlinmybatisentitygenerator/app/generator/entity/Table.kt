package com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.generator.entity

class Table(
    val name: String,
    val columns: Array<Column>
) {
    fun toColumnAllString(): String {
        return columns.map { column ->
            "\n val " + column.field.toString() + ": " + column.typeConverter()
        }.joinToString(",")
    }
}
