package com.github.shuntakeuch1.kotlinmybatisentitygenerator;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class TestDialog extends DialogWrapper {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable mysqlTable;

    public TestDialog() {
        super(true);
        init();
    }
    JTable getMysqlTable(){
        return mysqlTable;
    }
    @Override
    protected @Nullable JComponent createCenterPanel() {
        return contentPane;
    }
}
