package com.github.shuntakeuch1.kotlinmybatisentitygenerator

import com.github.shuntakeuch1.kotlinmybatisentitygenerator.services.MyProjectService
import com.github.shuntakeuch1.kotlinmybatisentitygenerator.view.GeneratorDialog
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys

class ShowMybatisGeneratorAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {


        val project = e.getData(PlatformDataKeys.PROJECT)
        val config = MyProjectService.getInstance(project!!)
        if (GeneratorDialog(project, config).showAndGet()) {
            println("end form")
        }
    }
}
