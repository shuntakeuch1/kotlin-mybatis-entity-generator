package com.github.shuntakeuch1.kotlinmybatisentitygenerator.view;

import com.github.shuntakeuch1.kotlinmybatisentitygenerator.entity.Table;
import com.github.shuntakeuch1.kotlinmybatisentitygenerator.generator.EntityGenerator;
import com.github.shuntakeuch1.kotlinmybatisentitygenerator.repository.MySQLRepository;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
        connectButton.addActionListener(this::connectActionPerformed);
        createButton.addActionListener(this::createActionPerformed);
        init();
    }

    JComboBox getDatabaseComboBox(){
        return databaseComboBox;
    }

    JButton getFileSelectButton() {
        return fileSelectButton;
    }

    private void connectActionPerformed(ActionEvent e) {
        System.out.println(url.getText());
        MySQLRepository repository = new MySQLRepository();
        tables = repository.getTables();

        String[] columnNames = {"Table Name"};
        String[][] data = new String[tables.size()][1];
        int index = 0;
        for (Table table: tables){
            data[index][0] = table.getName();
            index++;
        }
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            // 編集不可にする
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        mysqlTable.setModel(tableModel);

    }

    private void createActionPerformed(ActionEvent e) { ;
        EntityGenerator eg = new EntityGenerator();
        eg.setTargetDirectory(directoryTextField.getText());
        eg.execute(tables);
    }

    @Override
    protected  @Nullable JComponent createCenterPanel() {
        return contentPane;
    }

    public JTextField getDirectoryTextField() {
        return directoryTextField;
    }
}
