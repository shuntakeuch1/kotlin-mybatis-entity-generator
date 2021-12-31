package com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.repository

import com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.generator.entity.Table

interface DBRepository {
    var user: String
    var password: String
    var schema: String
    var url: String
    var port: String
    fun getTables(): List<Table>
}
