package com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.generator.entity

class Column(
    val field: String,
    val type: String,
    val nullFlag: String,
    val key: String,
    val defaultFlag: String?,
    val extra: String
) {
    private val charRegex = Regex("char")
    private val binaryRegex = Regex("binary")
    private val blobRegex = Regex("blob")
    private val textRegex = Regex("text")
    private val jsonRegex = Regex("json")
    private val intRegex = Regex("int")

    fun typeConverter(): String {
        val isNullFlag = nullFlag == "YES"
        val convertType = when {
            intRegex.containsMatchIn(type) -> {
                "Int"
            }
            stringCheck(type) -> {
                "String"
            }
            type.startsWith("timestamp") -> {
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
     * string check function
     * @param type
     */
    private fun stringCheck(type: String): Boolean {
        return charRegex.containsMatchIn(type) ||
            binaryRegex.containsMatchIn(type) ||
            blobRegex.containsMatchIn(type) ||
            textRegex.containsMatchIn(type) ||
            jsonRegex.containsMatchIn(type)
    }
}
