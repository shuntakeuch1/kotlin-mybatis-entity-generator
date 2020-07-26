package com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.generator.entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ColumnTest {

    @Test
    fun tesTypeConvert() {
        val column = Column(
            field = "",
            type = "",
            nullFlag = "",
            key = "",
            defaultFlag = "",
            extra = ""
        )
        assertEquals("", column.typeConverter())
    }
}
