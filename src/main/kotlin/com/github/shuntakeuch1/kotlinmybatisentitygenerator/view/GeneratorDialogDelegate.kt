@file:JvmName("GeneratorDialogDelegate")

package com.github.shuntakeuch1.kotlinmybatisentitygenerator.view

import java.awt.event.ActionEvent
import javax.swing.JFileChooser

fun init(dialog: GeneratorDialog) {
    dialog.apply {
        fileSelectButton.addActionListener {
            actionPerformed(it)
        }
        initComboBox()
    }
}

private fun GeneratorDialog.initComboBox() {
    databaseComboBox.addItem("mysql")
    databaseComboBox.selectedIndex = 0
}

private fun GeneratorDialog.actionPerformed(e: ActionEvent) {
    val filechooser = JFileChooser()
    filechooser.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY

    val selected = filechooser.showOpenDialog(createCenterPanel())
    if (selected == JFileChooser.APPROVE_OPTION) {
        val file = filechooser.selectedFile
        directoryTextField.text = file.absolutePath
    } else if (selected == JFileChooser.CANCEL_OPTION) {
//            label.setText("キャンセルされました");
    } else if (selected == JFileChooser.ERROR_OPTION) {
//            label.setText("エラー又は取消しがありました");
    }
}
