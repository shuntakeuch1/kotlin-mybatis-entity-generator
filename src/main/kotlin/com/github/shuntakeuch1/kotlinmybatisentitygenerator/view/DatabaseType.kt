package com.github.shuntakeuch1.kotlinmybatisentitygenerator.view

enum class DatabaseType(val index: Int, val typeName: String) {
    MYSQL(0, "mysql"),
    POSTGRESQL(1, "postgresql");

    companion object {
        fun fromInt(index: Int): DatabaseType {
            return values().firstOrNull { it.index == index } ?: MYSQL
        }
    }
}
