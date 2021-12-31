package com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.repository

import com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.generator.entity.Column
import com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.generator.entity.Table
import com.github.shuntakeuch1.kotlinmybatisentitygenerator.view.DatabaseType
import java.sql.DriverManager

/**
 * DB Access
 */
class MySQLRepositoryImpl : DBRepository {
    override lateinit var user: String
    override lateinit var password: String
    override lateinit var schema: String
    override lateinit var url: String
    override lateinit var port: String

    companion object {
        private const val JDBC_CLASS_NAME = "com.mysql.jdbc.Driver"
        private const val FIELD_COLUMN_LABEL = "Field"
        private const val TYPE_COLUMN_LABEL = "Type"
        private const val NULL_COLUMN_LABEL = "Null"
        private const val KEY_COLUMN_LABEL = "Key"
        private const val DEFAULT_COLUMN_LABEL = "Default"
        private const val EXTRA_COLUMN_LABEL = "Extra"
    }

    override fun getTables(): List<Table> {
        Class.forName(JDBC_CLASS_NAME)
        val jdbcURL = "jdbc:${DatabaseType.MYSQL.typeName}://$url:$port/$schema"
        val conn = DriverManager.getConnection(jdbcURL, user, password)
        val statement = conn.createStatement()
        val tableResultSet = statement.executeQuery("show tables;".trimIndent())
        val tablesName = mutableListOf<String>()

        while (tableResultSet.next()) {
            tablesName.add(tableResultSet.getString("Tables_in_$schema"))
        }
        val tables = tablesName.map {
            val columnResultSet = statement.executeQuery("show columns from $it;".trimIndent())
            val columns = mutableListOf<Column>()
            while (columnResultSet.next()) {
                columns.add(
                    Column(
                        field = columnResultSet.getString(FIELD_COLUMN_LABEL),
                        type = columnResultSet.getString(TYPE_COLUMN_LABEL),
                        nullFlag = columnResultSet.getString(NULL_COLUMN_LABEL),
                        key = columnResultSet.getString(KEY_COLUMN_LABEL),
                        defaultFlag = columnResultSet.getString(DEFAULT_COLUMN_LABEL),
                        extra = columnResultSet.getString(EXTRA_COLUMN_LABEL)
                    )
                )
            }
            columnResultSet.close()
            Table(
                name = it,
                columns = columns
            )
        }
        tableResultSet.close()
        statement.close()
        conn.close()

        return tables
    }
}
