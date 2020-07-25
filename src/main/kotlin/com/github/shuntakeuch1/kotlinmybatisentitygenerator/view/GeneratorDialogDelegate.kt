@file:JvmName("GeneratorDialogDelegate")

package com.github.shuntakeuch1.kotlinmybatisentitygenerator.view

import com.github.shuntakeuch1.kotlinmybatisentitygenerator.entity.Table
import com.github.shuntakeuch1.kotlinmybatisentitygenerator.generator.EntityGenerator
import com.github.shuntakeuch1.kotlinmybatisentitygenerator.repository.MySQLRepository
import java.awt.event.ActionEvent
import javax.swing.JFileChooser
import javax.swing.table.DefaultTableModel

/** connection table data */
private var tables: List<Table> = mutableListOf()

fun init(dialog: GeneratorDialog) {
    dialog.apply {
        initActionListenner()
        initComboBox()
    }
}

private fun GeneratorDialog.initActionListenner() {
    fileSelectButton.addActionListener {
        actionPerformed(it)
    }
    connectButton.addActionListener {
        connectActionPerformed(it)
    }
    createButton.addActionListener {
        createActionPerformed(it)
    }
}

private fun GeneratorDialog.initComboBox() {
    databaseComboBox.addItem("mysql")
    databaseComboBox.selectedIndex = 0
}

/**
 * File Select Action
 * @param e
 */
private fun GeneratorDialog.actionPerformed(e: ActionEvent) {
    val fileChooser = JFileChooser()
    fileChooser.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY

    val selected = fileChooser.showOpenDialog(createCenterPanel())
    if (selected == JFileChooser.APPROVE_OPTION) {
        val file = fileChooser.selectedFile
        directoryTextField.text = file.absolutePath
    } else if (selected == JFileChooser.CANCEL_OPTION) {
//            label.setText("キャンセルされました");
    } else if (selected == JFileChooser.ERROR_OPTION) {
//            label.setText("エラー又は取消しがありました");
    }
}

/**
 * Draw Table View
 * with Connect Button Acition
 * @param e
 */
private fun GeneratorDialog.connectActionPerformed(e: ActionEvent) {
    println(url.text)
    /** table connection */
    val repository = MySQLRepository()
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
 * Entity File Generate
 * with Create Action Button
 * @param e
 */
private fun GeneratorDialog.createActionPerformed(e: ActionEvent) {
    val eg = EntityGenerator()
    eg.targetDirectory = directoryTextField.text
    eg.execute(tables = tables)
}
