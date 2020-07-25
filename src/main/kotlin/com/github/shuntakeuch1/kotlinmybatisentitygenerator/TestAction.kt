package com.github.shuntakeuch1.kotlinmybatisentitygenerator;

import com.github.shuntakeuch1.kotlinmybatisentitygenerator.view.TestDialog
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys

class TestAction() : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.getData(PlatformDataKeys.PROJECT)
        if (TestDialog().showAndGet()) {
            println("end form")
        } 
    }
}
