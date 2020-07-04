package com.github.shuntakeuch1.kotlinmybatisentitygenerator.services

import com.intellij.openapi.project.Project
import com.github.shuntakeuch1.kotlinmybatisentitygenerator.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
