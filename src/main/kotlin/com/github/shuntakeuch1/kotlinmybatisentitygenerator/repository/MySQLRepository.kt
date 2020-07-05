package com.github.shuntakeuch1.kotlinmybatisentitygenerator.repository

import com.github.shuntakeuch1.kotlinmybatisentitygenerator.entity.Column
import com.github.shuntakeuch1.kotlinmybatisentitygenerator.entity.Table
import java.sql.DriverManager

/**
 * DB Access
 */
class MySQLRepository {
    companion object {
        const val jdbcURL = "jdbc:mysql://127.0.0.1/example"
        const val user = "root"
        const val password = "root"
    }

    fun getTables(): List<Table> {
        Class.forName("com.mysql.jdbc.Driver")
        val conn = DriverManager.getConnection(jdbcURL, user, password)
        val statement = conn.createStatement()
        var resultSet = statement.executeQuery("show tables;")
        var tablesName = arrayOf<String>()

        while (resultSet.next()) {
            val table = resultSet.getString("Tables_in_example")
            tablesName += table
        }
        val tables = tablesName.map {
            resultSet = statement.executeQuery("show columns from $it;")
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