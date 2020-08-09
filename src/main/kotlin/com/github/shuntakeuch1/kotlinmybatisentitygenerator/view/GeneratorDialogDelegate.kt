@file:JvmName("GeneratorDialogDelegate")

package com.github.shuntakeuch1.kotlinmybatisentitygenerator.view

import com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.generator.EntityGenerator
import com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.generator.entity.Table
import com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.repository.MySQLRepository
import com.intellij.icons.AllIcons
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.LocalFileSystem
import java.io.File
import javax.swing.table.DefaultTableModel

/** connection table data */
private var tables: List<Table> = mutableListOf()
private const val CHECKBOX_MIN_WIDTH = 40
private const val CHECKBOX_MAX_WIDTH = 40

fun init(dialog: GeneratorDialog) {
    dialog.apply {
        initActionListener()
        initComboBox()
        initDirectoryText()
        initTables()
    }
}

private fun GeneratorDialog.initActionListener() {
    folderSelectButton.addActionListener {
        folderSelectActionPerformed()
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
private fun GeneratorDialog.initComboBox() {
    databaseComboBox.apply {
        DatabaseType.values().forEach {
            addItem(it.typeName)
        }
        selectedIndex = DatabaseType.MYSQL.index
    }
}

/**
 * Directory Text Init Value
 */
private fun GeneratorDialog.initDirectoryText() {
    directoryLabel.text = project.basePath
}

private fun GeneratorDialog.initTables() {
    setTables()
}

/**
 * File Select Action
 */
private fun GeneratorDialog.folderSelectActionPerformed() {
    /** Pop Up file selection dialog */
    val fileChooserDescriptor =
        FileChooserDescriptorFactory.createSingleFolderDescriptor()

    val file = File(project.basePath!!)
    val projectBaseDir = LocalFileSystem.getInstance().findFileByIoFile(file)
    val folder = FileChooser.chooseFile(fileChooserDescriptor, project, projectBaseDir)

    if (folder != null) {
        directoryLabel.text = folder.path
    } else {
        Notifications.Bus.notify(
            Notification("entity-generator", "entity-generator", "Please select a folder.", NotificationType.WARNING)
        )
    }
}

/**
 * Draw Table View
 * with Connect Button Action
 */
private fun GeneratorDialog.connectActionPerformed() {
    /** table connection */
    val database = databaseComboBox.selectedItem
    tables = MySQLRepository().apply {
        jdbcURL = "jdbc:$database://${url.text}/${schemaTextField.text}"
        user = userTextField.text
        password = passwordTextField.text
        schema = schemaTextField.text
    }.getTables()

    setTables()
}

/** draw table name */
private fun GeneratorDialog.setTables() {
    val columnNames = GenerateTableColumn.values().map { it.columnName }.toTypedArray()
    val data = Array(0) { arrayOfNulls<Any>(2) }
    val tableModel = object : DefaultTableModel(data, columnNames) {
        override fun getColumnClass(columnIndex: Int): Class<*> {
            return getValueAt(0, columnIndex).javaClass
        }

        override fun isCellEditable(row: Int, column: Int): Boolean {
            return column == GenerateTableColumn.CHECKBOX.index
        }
    }
    tables.forEach { table ->
        tableModel.addRow(arrayOf(true, table.name))
    }

    mysqlTable.model = tableModel
    mysqlTable.columnModel.getColumn(GenerateTableColumn.CHECKBOX.index).apply {
        minWidth = CHECKBOX_MIN_WIDTH
        maxWidth = CHECKBOX_MAX_WIDTH
    }
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
    val eg = EntityGenerator().apply {
        isAllNullable = nullableCheckBox.isSelected
        targetDirectory = directoryLabel.text
    }
    var denyGenerateFileName = arrayOf<String>()
    val maxCount = mysqlTable.rowCount - 1
    for (i in 0..maxCount) {
        if (mysqlTable.model.getValueAt(i, GenerateTableColumn.CHECKBOX.index) == false) {
            denyGenerateFileName +=
                mysqlTable.model.getValueAt(i, GenerateTableColumn.TABLE_NAME.index).toString()
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
