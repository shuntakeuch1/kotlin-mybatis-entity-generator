package com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.generator.entity

data class Column(
    val field: String,
    val type: String,
    val nullFlag: String,
    val key: String?,
    val defaultFlag: String?,
    val extra: String?
) {
    private val intRegex = Regex("int")
    private val charRegex = Regex("char")
    private val binaryRegex = Regex("binary")
    private val blobRegex = Regex("blob")
    private val textRegex = Regex("text")
    private val jsonRegex = Regex("json")

    fun typeConverter(isAllNullableOption: Boolean): String {
        val isNullFlag = isAllNullableOption || nullFlag == "YES"
        val convertType = when {
            intRegex.containsMatchIn(type) -> {
                "Int"
            }
            stringCheck(type) -> {
                "String"
            }
            localDateTimeCheck(type) -> {
                "LocalDateTime"
            }
            else -> {
                type
            }
        }
        return if (isNullFlag) {
            "$convertType?"
        } else {
            convertType
        }
    }

    /**
     * local date time check function
     * @param type
     */
    private fun localDateTimeCheck(type: String): Boolean {
        return type.startsWith("time") ||
            type.startsWith("date") ||
            type.startsWith("year")
    }

    /**
     * string check function
     * @param type
     */
    private fun stringCheck(type: String): Boolean {
        return charRegex.containsMatchIn(type) ||
            binaryRegex.containsMatchIn(type) ||
            blobRegex.containsMatchIn(type) ||
            textRegex.containsMatchIn(type) ||
            jsonRegex.containsMatchIn(type) ||
            type.startsWith("enum")
    }
}
