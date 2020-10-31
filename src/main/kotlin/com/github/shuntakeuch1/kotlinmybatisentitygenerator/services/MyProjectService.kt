package com.github.shuntakeuch1.kotlinmybatisentitygenerator.services

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
    name = "KotlinMyBatisGenerator",
    storages = [Storage("kotlinMybatisEntityGenerator.xml")]
)
class MyProjectService : PersistentStateComponent<MyProjectService> {

    var jdbcURL: String? = null
    var user: String? = null
    var schema: String? = null

    override fun getState(): MyProjectService? {
        return this
    }

    override fun loadState(state: MyProjectService) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        fun getInstance(project: Project): MyProjectService {
            return ServiceManager.getService(project, MyProjectService::class.java)
        }
    }
}
