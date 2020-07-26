package com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.generator.entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class ColumnTest {

    @Test
    fun tesTypeConvert() {
        val columnType = Column(
            field = "",
            type = "varchar(10)",
            nullFlag = "",
            key = "",
            defaultFlag = "",
            extra = ""
        )
        val intColumnType = Column(
            field = "",
            type = "int(11)",
            nullFlag = "",
            key = "",
            defaultFlag = "",
            extra = ""
        )
        val localDateTimeColumnType = Column(
            field = "",
            type = "timestamp",
            nullFlag = "",
            key = "",
            defaultFlag = "",
            extra = ""
        )
        assertEquals("String", columnType.typeConverter())
        assertEquals("Int", intColumnType.typeConverter())
        assertEquals("LocalDateTime", localDateTimeColumnType.typeConverter())
    }

    @ParameterizedTest
    @MethodSource("stringDataProvider")
    fun stringTypeConverter(expect: String, type: String, nullFlag: String) {
        val columnType = Column(
            field = "",
            type = type,
            nullFlag = nullFlag,
            key = "",
            defaultFlag = "",
            extra = ""
        )
        assertEquals(expect, columnType.typeConverter())
    }

    companion object {
        @JvmStatic
        fun stringDataProvider() = listOf(
            Arguments.of("String", "varchar(255)", "NO"),
            Arguments.of("String", "char(255)", "NO"),
            Arguments.of("String", "binary(255)", "NO"),
            Arguments.of("String", "blob", "NO"),
            Arguments.of("String", "text", "NO"),
            Arguments.of("String", "json", "NO"),
            // nullable
            Arguments.of("String?", "varchar(255)", "YES"),
            Arguments.of("String?", "char(255)", "YES"),
            Arguments.of("String?", "binary(255)", "YES"),
            Arguments.of("String?", "blob", "YES"),
            Arguments.of("String?", "text", "YES"),
            Arguments.of("String?", "json", "YES")
        )
    }
}
