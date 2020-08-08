@file:JvmName("GeneratorDialogDelegate")

package com.github.shuntakeuch1.kotlinmybatisentitygenerator.view

import com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.generator.EntityGenerator
import com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.generator.entity.Table
import com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.repository.MySQLRepository
import com.intellij.icons.AllIcons
import com.intellij.openapi.ui.Messages
import javax.swing.JFileChooser
import javax.swing.table.DefaultTableModel

/** connection table data */
private var tables: List<Table> = mutableListOf()

fun init(dialog: GeneratorDialog) {
    dialog.apply {
        initActionListener()
        initComboBox()
        initDirectoryText()
        initTables()
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
 * Directory Text Init Value
 */
private fun GeneratorDialog.initDirectoryText() {
    directoryTextField.text = projectBasePath
}

private fun GeneratorDialog.initTables() {
    val data = Array(0) { arrayOfNulls<String>(1) }
    setTables(data)
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

    val data = Array(tables.size) { arrayOfNulls<String>(1) }
    var index = 0
    tables.forEach { table ->
        data[index][0] = table.name
        index++
    }
    setTables(data)
}

/** draw table name */
private fun GeneratorDialog.setTables(data: Array<Array<String?>>) {
    val columnNames = arrayOf("Table Name")
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
    initTables()
}

/**
 * Entity File Generate
 * with Create Action Button
 */
private fun GeneratorDialog.createActionPerformed() {
    val eg = EntityGenerator()
    eg.isAllNullable = nullableCheckBox.isSelected
    eg.targetDirectory = directoryTextField.text
    eg.execute(tables = tables)
    Messages.showMessageDialog(
        "${eg.lastCreatedFileCount} file created",
        "Information",
        AllIcons.General.InformationDialog
    )
}
