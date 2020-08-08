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
const val CHECKBOX_MIN_WIDTH = 40
const val CHECKBOX_MAX_WIDTH = 40

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
    setTables()
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

    setTables()
}

/** draw table name */
private fun GeneratorDialog.setTables() {
    val columnNames = arrayOf("", "Table Name")
    val data = Array(0) { arrayOfNulls<Any>(2) }
    val tableModel = object : DefaultTableModel(data, columnNames) {
        override fun getColumnClass(columnIndex: Int): Class<*> {
            return getValueAt(0, columnIndex).javaClass
        }

        override fun isCellEditable(row: Int, column: Int): Boolean {
            return column == 0
        }
    }
    tables.forEach { table ->
        tableModel.addRow(arrayOf(true, table.name))
    }

    mysqlTable.model = tableModel
    mysqlTable.columnModel.getColumn(0).minWidth = CHECKBOX_MIN_WIDTH
    mysqlTable.columnModel.getColumn(0).maxWidth = CHECKBOX_MAX_WIDTH
}

/**
 * clear mysql table
 * with Cancel Action Button
 */
private fun GeneratorDialog.clearTableActionPerformed() {
    tables = mutableListOf()
    setTables()
}

/**
 * Entity File Generate
 * with Create Action Button
 */
private fun GeneratorDialog.createActionPerformed() {
    val eg = EntityGenerator()
    eg.isAllNullable = nullableCheckBox.isSelected
    eg.targetDirectory = directoryTextField.text
    var denyGenerateFileName = arrayOf<String>()
    val maxCount = mysqlTable.rowCount - 1
    for (i in 0..maxCount) {
        if (mysqlTable.model.getValueAt(i, 0) == false) {
            denyGenerateFileName += mysqlTable.model.getValueAt(i, 1).toString()
        }
    }
    val targetTables = tables.filterNot { table ->
        denyGenerateFileName.any { it == table.name }
    }
    eg.execute(tables = targetTables)
    Messages.showMessageDialog(
        "${eg.lastCreatedFileCount} file created",
        "Information",
        AllIcons.General.InformationDialog
    )
}
