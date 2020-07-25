@file:JvmName("GeneratorDialogDelegate")
package com.github.shuntakeuch1.kotlinmybatisentitygenerator.view

fun init(dialog: GeneratorDialog){
    dialog.apply {
        initComboBox()
    }
}

private fun GeneratorDialog.initComboBox(){
    databaseComboBox.addItem("mysql")
    databaseComboBox.selectedIndex = 0
}
