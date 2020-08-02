package com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.generator.entity

class Table(
    val name: String,
    val columns: Array<Column>
) {
    fun toColumnAllString(isAllNullableOption: Boolean): String {
        return columns.map { column ->
            "\n val " + column.field.toString() + ": " + column.typeConverter(isAllNullableOption)
        }.joinToString(",")
    }

    fun isLocalDateTimeExist(): Boolean {
        return columns.firstOrNull { it.typeConverter(false) == "LocalDateTime" } != null
    }
}
