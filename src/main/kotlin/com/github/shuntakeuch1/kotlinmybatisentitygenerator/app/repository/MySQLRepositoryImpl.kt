package com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.repository

import com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.generator.entity.Column
import com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.generator.entity.Table
import java.sql.DriverManager

/**
 * DB Access
 */
class MySQLRepositoryImpl : DBRepository {
    override lateinit var jdbcURL: String
    override lateinit var user: String
    override lateinit var password: String
    override lateinit var schema: String

    override fun getTables(): List<Table> {
        Class.forName("com.mysql.jdbc.Driver")
        val conn = DriverManager.getConnection(jdbcURL, user, password)
        val statement = conn.createStatement()
        var resultSet = statement.executeQuery("show tables;".trimIndent())
        var tablesName = arrayOf<String>()

        while (resultSet.next()) {
            val table = resultSet.getString("Tables_in_$schema")
            tablesName += table
        }
        val tables = tablesName.map {
            resultSet = statement.executeQuery("show columns from $it;".trimIndent())
            var columns = arrayOf<Column>()
            while (resultSet.next()) {
                val column = Column(
                    field = resultSet.getString("Field"),
                    type = resultSet.getString("Type"),
                    nullFlag = resultSet.getString("Null"),
                    key = resultSet.getString("Key"),
                    defaultFlag = resultSet.getString("Default"),
                    extra = resultSet.getString("Extra")
                )
                columns += column
            }
            Table(
                name = it,
                columns = columns
            )
        }
        resultSet.close()
        statement.close()
        conn.close()

        return tables
    }
}
