package com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.generator.entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TableTest {

    @Test
    fun testExecute() {
        val table = Table(
            name = "",
            columns = arrayOf<Column>(
                Column(
                    field = "",
                    type = "varchar(10)",
                    nullFlag = "",
                    key = "",
                    defaultFlag = "",
                    extra = ""
                ),
                Column(
                    field = "",
                    type = "int(11)",
                    nullFlag = "",
                    key = "",
                    defaultFlag = "",
                    extra = ""
                ),
                Column(
                    field = "",
                    type = "timestamp",
                    nullFlag = "",
                    key = "",
                    defaultFlag = "",
                    extra = ""
                )
            )
        )
        val expect = """
             val : String,
             val : Int,
             val : LocalDateTime
        """.trimIndent()
        assertEquals(expect, table.toColumnAllString(false).trimIndent())
    }
}
