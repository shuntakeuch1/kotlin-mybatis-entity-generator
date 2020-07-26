package com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.generator.entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

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

//    @ParameterizedTest
//    fun stringTypeConverter() {
//        assertEquals("String", columnType.typeConverter())
//    }
}
