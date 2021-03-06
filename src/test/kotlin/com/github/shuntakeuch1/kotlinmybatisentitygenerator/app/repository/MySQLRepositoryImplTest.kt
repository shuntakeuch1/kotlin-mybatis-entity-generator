package com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled
class MySQLRepositoryImplTest {

    @Test
    fun testGetTable() {
        val repository = MySQLRepositoryImpl()
        assertEquals("", repository)
    }
}
