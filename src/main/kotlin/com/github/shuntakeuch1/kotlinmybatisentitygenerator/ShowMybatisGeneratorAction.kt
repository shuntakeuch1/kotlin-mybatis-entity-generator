package com.github.shuntakeuch1.kotlinmybatisentitygenerator

import com.github.shuntakeuch1.kotlinmybatisentitygenerator.view.GeneratorDialog
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys

class ShowMybatisGeneratorAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {

        val config = PluginConfig.getInstance(e.project)

        if (config != null) {
            if (config.isEmpty) {
                config.init()
                println("init count")
            }
            println("start plugin " + config.count)
            config.increment()
        }
        val project = e.getData(PlatformDataKeys.PROJECT)
        if (GeneratorDialog(project).showAndGet()) {
            println("end form")
        }
    }
}
