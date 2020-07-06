package com.github.shuntakeuch1.kotlinmybatisentitygenerator.view;

import com.github.shuntakeuch1.kotlinmybatisentitygenerator.entity.Table;
import com.github.shuntakeuch1.kotlinmybatisentitygenerator.repository.MySQLRepository;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

public class TestDialog extends DialogWrapper {
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
    private JTable table1;
    private JButton fileSelectButton;
    private JTextField directoryTextField;
    private JButton createButton;
    private JTable mysqlTable;

    public TestDialog() {
        super(true);
        fileSelectButton.addActionListener(this::actionPerformed);
        connectButton.addActionListener(this::connectActionPerformed);
        databaseComboBox.addItem("mysql");
        databaseComboBox.setSelectedIndex(0);
        init();
    }

    private void actionPerformed(ActionEvent e) {
        JFileChooser filechooser = new JFileChooser();
        filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int selected = filechooser.showOpenDialog(createCenterPanel());
        if (selected == JFileChooser.APPROVE_OPTION) {
            File file = filechooser.getSelectedFile();
            directoryTextField.setText(file.getName());
        } else if (selected == JFileChooser.CANCEL_OPTION) {
//            label.setText("キャンセルされました");
        } else if (selected == JFileChooser.ERROR_OPTION) {
//            label.setText("エラー又は取消しがありました");
        }
    }

    private void connectActionPerformed(ActionEvent e){
        System.out.println(url.getText());
        MySQLRepository repository = new MySQLRepository();
        List<Table> tables = repository.getTables();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return contentPane;
    }
}
