package com.github.shuntakeuch1.kotlinmybatisentitygenerator.view;

import com.github.shuntakeuch1.kotlinmybatisentitygenerator.entity.Table;
import com.github.shuntakeuch1.kotlinmybatisentitygenerator.generator.EntityGenerator;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

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
    private List<Table> tables;

    public GeneratorDialog() {
        super(true);
        GeneratorDialogDelegate.init(this);
        createButton.addActionListener(this::createActionPerformed);
        init();
    }

    JButton getCreateButton() {
        return createButton;
    }

    private void createActionPerformed(ActionEvent e) { ;
        EntityGenerator eg = new EntityGenerator();
        eg.setTargetDirectory(directoryTextField.getText());
        eg.execute(tables);
    }

    JComboBox getDatabaseComboBox(){
        return databaseComboBox;
    }

    @Override
    protected  @Nullable JComponent createCenterPanel() {
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
}
