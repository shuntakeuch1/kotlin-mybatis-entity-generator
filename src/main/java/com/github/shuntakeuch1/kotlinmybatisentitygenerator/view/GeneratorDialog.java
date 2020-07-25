package com.github.shuntakeuch1.kotlinmybatisentitygenerator.view;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class GeneratorDialog extends DialogWrapper {
    private JPanel contentPane;
    private JButton connectButton;
    private JButton buttonCancel;
    private JTextField url;
    private JTextField schema;
    private JTextField user;
    private JTextField password;
    private JLabel schemeLabel;
    private JLabel urlLabel;
    private JLabel passwordLabel;
    private JLabel userLabel;
    private JComboBox databaseComboBox;
    private JButton fileSelectButton;
    private JTextField directoryTextField;
    private JButton createButton;
    private JTable mysqlTable;
    private final String projectBasePath;

    public GeneratorDialog(Project project) {
        super(project);
        projectBasePath = project.getBasePath();
        GeneratorDialogDelegate.init(this);
        init();
    }

    JComboBox getDatabaseComboBox() {
        return databaseComboBox;
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return contentPane;
    }

    JButton getFileSelectButton() {
        return fileSelectButton;
    }

    JTextField getDirectoryTextField() {
        return directoryTextField;
    }


    JButton getConnectButton() {
        return connectButton;
    }

    JTextField getUrl() {
        return url;
    }

    JTable getMysqlTable() {
        return mysqlTable;
    }

    JButton getCreateButton() {
        return createButton;
    }

    public String getProjectBasePath() {
        return projectBasePath;
    }
}
