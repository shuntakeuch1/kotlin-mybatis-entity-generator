package com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.generator.entity

class Column(
    val field: String,
    val type: String,
    val nullFlag: String,
    val key: String,
    val defaultFlag: String?,
    val extra: String
) {
    fun typeConverter(): String {
        return when {
            type.startsWith("int") -> {
                "Int"
            }
            type.startsWith("varchar") -> {
                "String"
            }
            else -> {
                type
            }
        }
    }
}
