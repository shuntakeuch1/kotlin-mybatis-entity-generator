package com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.repository

import com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.generator.entity.Column
import com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.generator.entity.Table
import java.sql.DriverManager

/**
 * DB Access
 */
class PostgreSQLRepositoryImpl : DBRepository {
    override lateinit var jdbcURL: String
    override lateinit var user: String
    override lateinit var password: String
    override lateinit var schema: String

    override fun getTables(): List<Table> {
        Class.forName("org.postgresql.Driver")
        val conn = DriverManager.getConnection(jdbcURL, user, password)
        val statement = conn.createStatement()
        val tableNameQuery =
            """
            SELECT
              tablename AS TABLE
            FROM
              pg_tables
            WHERE
              schemaname = 'public';
            """.trimIndent()
        var resultSet = statement.executeQuery(tableNameQuery)
        var tablesName = arrayOf<String>()

        while (resultSet.next()) {
            val table = resultSet.getString("table")
            tablesName += table
        }
        val tables = tablesName.map {
            resultSet = statement.executeQuery(
                """
                SELECT
                  table_name,
                  column_name,
                  data_type,
                  is_nullable
                FROM
                  information_schema.columns
                WHERE
                  table_name = '$it';
                """.trimIndent()
            )
            var columns = arrayOf<Column>()
            while (resultSet.next()) {
                val column = Column(
                    field = resultSet.getString("column_name"),
                    type = resultSet.getString("data_type"),
                    nullFlag = resultSet.getString("is_nullable"),
                    key = null,
                    defaultFlag = null,
                    extra = null
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
