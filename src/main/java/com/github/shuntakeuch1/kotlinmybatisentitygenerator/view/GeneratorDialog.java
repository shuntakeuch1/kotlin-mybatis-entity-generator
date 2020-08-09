package com.github.shuntakeuch1.kotlinmybatisentitygenerator.view;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class GeneratorDialog extends DialogWrapper {
    private JPanel contentPane;
    private JButton connectButton;
    private JButton cancelButton;
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
    private JCheckBox nullableCheckBox;
    private final String projectBasePath;

    public GeneratorDialog(Project project) {
        super(project);
        projectBasePath = project.getBasePath();
        GeneratorDialogDelegate.init(this);
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return contentPane;
    }

    protected JComboBox getDatabaseComboBox() {
        return databaseComboBox;
    }

    protected JButton getFileSelectButton() {
        return fileSelectButton;
    }

    protected JLabel getDirectoryLabel() {
        return directoryLabel;
    }

    protected JButton getConnectButton() {
        return connectButton;
    }

    protected JButton getCancelButton() {
        return cancelButton;
    }

    protected JTextField getUrl() {
        return url;
    }

    protected JTextField getSchemaTextField() {
        return schemaTextField;
    }

    protected JTextField getUserTextField() {
        return userTextField;
    }

    protected JTextField getPasswordTextField() {
        return passwordTextField;
    }

    protected JTable getMysqlTable() {
        return mysqlTable;
    }

    protected JButton getCreateButton() {
        return createButton;
    }

    protected String getProjectBasePath() {
        return projectBasePath;
    }

    protected JCheckBox getNullableCheckBox() {
        return nullableCheckBox;
    }
}
