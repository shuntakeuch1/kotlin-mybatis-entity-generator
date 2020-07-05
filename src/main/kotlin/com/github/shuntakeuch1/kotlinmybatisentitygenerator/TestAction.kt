package com.github.shuntakeuch1.kotlinmybatisentitygenerator;

import com.github.shuntakeuch1.kotlinmybatisentitygenerator.generator.EntityGenerator
import com.github.shuntakeuch1.kotlinmybatisentitygenerator.repository.MySQLRepository
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.ui.Messages

class TestAction() : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.getData(PlatformDataKeys.PROJECT)
        val txt = Messages.showInputDialog(project, "What is your name?", "Input your name", Messages.getQuestionIcon())

        val tables = MySQLRepository().getTables()
        val eg = EntityGenerator()
        if (txt != null){
            eg.targetDirectory = txt
        }
        eg.execute(tables)
        Messages.showMessageDialog(project, "Hello, $txt!\n I am glad to see you.", "Information", Messages.getInformationIcon())
    }
}
