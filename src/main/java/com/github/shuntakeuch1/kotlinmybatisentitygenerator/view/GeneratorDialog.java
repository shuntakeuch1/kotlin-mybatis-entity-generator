package com.github.shuntakeuch1.kotlinmybatisentitygenerator.view;

import com.github.shuntakeuch1.kotlinmybatisentitygenerator.services.MyProjectService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class GeneratorDialog extends DialogWrapper {
    private JPanel contentPane;
    private JButton connectButton;
    private JButton cancelButton;
    private JTextField url;
    private JTextField schemaTextField;
    private JTextField userTextField;
    private JTextField passwordTextField;
    private JLabel schemeLabel;
    private JLabel urlLabel;
    private JLabel passwordLabel;
    private JLabel userLabel;
    private JComboBox databaseComboBox;
    private JButton folderSelectButton;
    private JLabel directoryLabel;
    private JButton createButton;
    private JTable databaseTable;
    private JCheckBox nullableCheckBox;
    private JLabel portLabel;
    private JLabel port;
    private JTextField portTextField;
    private JLabel DBLabel;
    private final Project project;
    private final MyProjectService service;

    public GeneratorDialog(Project project, MyProjectService service) {
        super(project);
        this.project = project;
        this.service = service;
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

    protected JButton getFolderSelectButton() {
        return folderSelectButton;
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

    protected JTextField getUrlTextField() {
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

    protected JTextField getPortTextField() {
        return portTextField;
    }

    protected JTable getDatabaseTable() {
        return databaseTable;
    }

    protected JButton getCreateButton() {
        return createButton;
    }

    protected Project getProject() {
        return project;
    }

    protected MyProjectService getService() {
        return service;
    }

    protected JCheckBox getNullableCheckBox() {
        return nullableCheckBox;
    }
}
