package com.github.shuntakeuch1.kotlinmybatisentitygenerator;

import com.github.shuntakeuch1.kotlinmybatisentitygenerator.view.GeneratorDialog
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys

class GeneratorFormShowAction() : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.getData(PlatformDataKeys.PROJECT)
        if (GeneratorDialog().showAndGet()) {
            println("end form")
        }
    }
}
