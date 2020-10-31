package com.github.shuntakeuch1.kotlinmybatisentitygenerator;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

@State(
    name = "HelloPluginConfig",
    storages = {
        @Storage("nunyu.xml")
    }
)
public class PluginConfig implements PersistentStateComponent<PluginConfig> {
    private Integer count;
    public String stateValue;


    @Nullable
    @Override
    public PluginConfig getState() {
        return this;
    }

    @Override
    public void loadState(PluginConfig state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    @Nullable
    public static PluginConfig getInstance(Project project) {
        return ServiceManager.getService(project, PluginConfig.class);
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return this.count;
    }

    public boolean isEmpty() {
        return count == null;
    }

    public void init() {
        this.count = 0;
    }

    public void increment() {
        this.count++;
    }
}
