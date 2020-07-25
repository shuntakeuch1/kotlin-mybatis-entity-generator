package com.github.shuntakeuch1.kotlinmybatisentitygenerator.services

import com.github.shuntakeuch1.kotlinmybatisentitygenerator.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
