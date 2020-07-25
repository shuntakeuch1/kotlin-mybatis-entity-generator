package com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.repository

import org.junit.Test
import kotlin.test.assertNotNull

class MySQLRepositoryTest {

    @Test
    fun testGetTable(){
        val repository = MySQLRepository()
        assertNotNull(repository.getTables(),"")
    }
}
