package com.github.shuntakeuch1.kotlinmybatisentitygenerator.listeners

import com.github.shuntakeuch1.kotlinmybatisentitygenerator.services.MyProjectService
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener

internal class MyProjectManagerListener : ProjectManagerListener {

    override fun projectOpened(project: Project) {
        project.getService(MyProjectService::class.java)
    }
}
