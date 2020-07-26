@file:JvmName("GeneratorDialogDelegate")

package com.github.shuntakeuch1.kotlinmybatisentitygenerator.view

import com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.generator.EntityGenerator
import com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.generator.entity.Table
import com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.repository.MySQLRepository
import javax.swing.JFileChooser
import javax.swing.table.DefaultTableModel

/** connection table data */
private var tables: List<Table> = mutableListOf()

fun init(dialog: GeneratorDialog) {
    dialog.apply {
        initActionListener()
        initComboBox()
    }
}

private fun GeneratorDialog.initActionListener() {
    fileSelectButton.addActionListener {
        fileSelectActionPerformed()
    }
    connectButton.addActionListener {
        connectActionPerformed()
    }
    cancelButton.addActionListener {
        clearTableActionPerformed()
    }
    createButton.addActionListener {
        createActionPerformed()
    }
}
/** support database */
private val items = arrayOf("mysql")
private fun GeneratorDialog.initComboBox() {
    items.forEach { item ->
        databaseComboBox.addItem(item)
    }
    databaseComboBox.selectedIndex = 0
}

/**
 * File Select Action
 */
private fun GeneratorDialog.fileSelectActionPerformed() {
    /** Pop Up file selection dialog */
    val fileChooser = JFileChooser(projectBasePath)
    fileChooser.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY

    val selected = fileChooser.showOpenDialog(createCenterPanel())
    if (selected == JFileChooser.APPROVE_OPTION) {
        val file = fileChooser.selectedFile
        directoryTextField.text = file.absolutePath
    }
}

/**
 * Draw Table View
 * with Connect Button Action
 */

private fun GeneratorDialog.connectActionPerformed() {
    /** table connection */
    val repository = MySQLRepository()
    val database = databaseComboBox.selectedItem
    val schema = schema.text
    repository.jdbcURL = "jdbc:$database://${url.text}/$schema"
    repository.user = user.text
    repository.password = password.text
    repository.schema = schema
    tables = repository.getTables()

    /** draw table name */
    val columnNames = arrayOf("Table Name")
    val data = Array(tables.size) { arrayOfNulls<String>(1) }
    var index = 0
    tables.forEach { table ->
        data[index][0] = table.name
        index++
    }
    val tableModel = object : DefaultTableModel(data, columnNames) {
        override fun isCellEditable(row: Int, column: Int): Boolean {
            return false
        }
    }
    mysqlTable.model = tableModel
}

/**
 * clear mysql table
 * with Cancel Action Button
 */
private fun GeneratorDialog.clearTableActionPerformed() {
    mysqlTable.model = DefaultTableModel()
}

/**
 * Entity File Generate
 * with Create Action Button
 */
private fun GeneratorDialog.createActionPerformed() {
    val eg = EntityGenerator()
    eg.targetDirectory = directoryTextField.text
    eg.execute(tables = tables)
}
