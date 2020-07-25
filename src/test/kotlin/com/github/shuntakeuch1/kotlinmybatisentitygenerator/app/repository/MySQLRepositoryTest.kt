package com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.repository

import kotlin.test.assertNotNull
import org.junit.Test

class MySQLRepositoryTest {

    @Test
    fun testGetTable() {
        val repository = MySQLRepository()
        assertNotNull(repository.getTables(), "")
    }
}
