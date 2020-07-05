package com.github.shuntakeuch1.kotlinmybatisentitygenerator.entity

class Column(
    val field: String,
    val type: String,
    val nullFlag: String,
    val key: String,
    val defaultFlag: String?,
    val extra: String
) {
    fun typeConverter(): String {
        if (type.startsWith("int")) {
            return "Int"
        }
        if (type.startsWith("varchar")) {
            return "String"
        }
        return type
    }
}