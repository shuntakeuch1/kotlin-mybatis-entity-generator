package com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.repository

import com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.generator.entity.Column
import com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.generator.entity.Table
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled
class MySQLRepositoryImplTest {

    private val testGetTableExpect = arrayListOf(
        Table(
            name = "product",
            columns = listOf(
                Column(
                    field = "id",
                    type = "int(11)",
                    nullFlag = "YES",
                    key = "",
                    defaultFlag = null,
                    extra = ""
                ),
                Column(
                    field = "name",
                    type = "varchar(10)",
                    nullFlag = "YES",
                    key = "",
                    defaultFlag = null,
                    extra = ""
                ),
                Column(
                    field = "col",
                    type = "varchar(10)",
                    nullFlag = "YES",
                    key = "",
                    defaultFlag = null,
                    extra = ""
                )
            )
        ),
        Table(
            name = "user",
            columns = listOf(
                Column(
                    field = "id",
                    type = "int(11)",
                    nullFlag = "YES",
                    key = "",
                    defaultFlag = null,
                    extra = ""
                ),
                Column(
                    field = "name",
                    type = "varchar(10)",
                    nullFlag = "YES",
                    key = "",
                    defaultFlag = null,
                    extra = ""
                ),
                Column(
                    field = "col",
                    type = "varchar(10)",
                    nullFlag = "YES",
                    key = "",
                    defaultFlag = null,
                    extra = ""
                )
            )
        )
    )

    @Test
    fun testGetTable() {
        val actual = MySQLRepositoryImpl().apply {
            url = "localhost"
            user = "root"
            schema = "example"
            port = "3306"
            password = "root"
        }.getTables()

        val expect = testGetTableExpect

        assertEquals(expect, actual)
    }
}
