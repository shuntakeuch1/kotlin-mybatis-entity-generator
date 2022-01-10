@file:JvmName("GeneratorDialogDelegate")

package com.github.shuntakeuch1.kotlinmybatisentitygenerator.view

import com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.generator.EntityGenerator
import com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.generator.entity.Table
import com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.repository.MySQLRepositoryImpl
import com.github.shuntakeuch1.kotlinmybatisentitygenerator.app.repository.PostgreSQLRepositoryImpl
import com.intellij.credentialStore.CredentialAttributes
import com.intellij.credentialStore.Credentials
import com.intellij.credentialStore.generateServiceName
import com.intellij.icons.AllIcons
import com.intellij.ide.passwordSafe.PasswordSafe
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.LocalFileSystem
import java.io.File
import javax.swing.table.DefaultTableModel

/** connection table data */
private var tables: List<Table> = mutableListOf()
private const val CHECKBOX_MIN_WIDTH = 40
private const val CHECKBOX_MAX_WIDTH = 40
private const val DB_CONNECT_PASSWORD_KEY = "kotlin_mybatis_generator"
private const val DEFAULT_USER = "example"
private const val DEFAULT_URL = "127.0.0.1"
private const val DEFAULT_SCHEMA = "example"
private const val DEFAULT_PORT = "3306"
private val LOG = logger<GeneratorDialog>()

fun init(dialog: GeneratorDialog) {
    dialog.apply {
        /** init Action Listener */
        folderSelectButton.addActionListener {
            folderSelectActionPerformed()
        }
        connectButton.addActionListener {
            connectActionPerformed()
        }
        cancelButton.addActionListener {
            clearTableActionPerformed()
        }
        createButton.addActionListener {
            createActionPerformed()
        }

        initDialogViewSettings()
    }
}

private fun GeneratorDialog.initDialogViewSettings() {
    /** support database */
    databaseComboBox.apply {
        DatabaseType.values().forEach {
            addItem(it.typeName)
        }
        selectedIndex = service.databaseTypeIndex ?: DatabaseType.MYSQL.index
    }

    setTables()

    directoryLabel.text = project.basePath
    urlTextField.text = service.url ?: DEFAULT_URL
    userTextField.text = service.user ?: DEFAULT_USER
    passwordTextField.text = PasswordSafe.instance.getPassword(createCredentialAttributes())
    schemaTextField.text = service.schema ?: DEFAULT_SCHEMA
    portTextField.text = service.port ?: DEFAULT_PORT
}

/**
 * File Select Action
 */
private fun GeneratorDialog.folderSelectActionPerformed() {
    /** Pop Up file selection dialog */
    val fileChooserDescriptor =
        FileChooserDescriptorFactory.createSingleFolderDescriptor()

    val file = File(project.basePath!!)
    val projectBaseDir = LocalFileSystem.getInstance().findFileByIoFile(file)
    val folder = FileChooser.chooseFile(fileChooserDescriptor, project, projectBaseDir)

    if (folder != null) {
        directoryLabel.text = folder.path
    } else {
        Notifications.Bus.notify(
            Notification("entity-generator", "entity-generator", "Please select a folder.", NotificationType.WARNING)
        )
    }
}

/**
 * Draw Table View
 * with Connect Button Action
 */
private fun GeneratorDialog.connectActionPerformed() {
    /** table connection */
    val databaseType = DatabaseType.fromInt(databaseComboBox.selectedIndex)
    val password = passwordTextField.text
    val dbAccess = when (databaseType) {
        DatabaseType.POSTGRESQL -> PostgreSQLRepositoryImpl()
        DatabaseType.MYSQL -> MySQLRepositoryImpl()
    }
    runCatching {
        tables = dbAccess.apply {
            this.user = userTextField.text
            this.schema = schemaTextField.text
            this.port = portTextField.text
            this.password = password
            this.url = urlTextField.text
        }.getTables()
        setTables()
        /** save connect config */
        val credentials = Credentials(userTextField.text, passwordTextField.text)
        PasswordSafe.instance.set(createCredentialAttributes(), credentials)
        service.apply {
            databaseTypeIndex = databaseType.index
            user = userTextField.text
            schema = schemaTextField.text
            url = urlTextField.text
            port = portTextField.text
        }
    }.onFailure {
        LOG.error("db connection error", it)
        Messages.showMessageDialog(
            "db connection error",
            "Error",
            AllIcons.General.ErrorDialog
        )
    }
}

private fun createCredentialAttributes(): CredentialAttributes {
    return CredentialAttributes(generateServiceName("MyProjectService", DB_CONNECT_PASSWORD_KEY))
}

/** draw table name */
private fun GeneratorDialog.setTables() {
    val columnNames = GenerateTableColumn.values().map { it.columnName }.toTypedArray()
    val data = Array(0) { arrayOfNulls<Any>(2) }
    val tableModel = object : DefaultTableModel(data, columnNames) {
        override fun getColumnClass(columnIndex: Int): Class<*> {
            return getValueAt(0, columnIndex).javaClass
        }

        override fun isCellEditable(row: Int, column: Int): Boolean {
            return column == GenerateTableColumn.CHECKBOX.index
        }
    }
    tables.forEach { table ->
        tableModel.addRow(arrayOf(true, table.name))
    }

    databaseTable.model = tableModel
    databaseTable.columnModel.getColumn(GenerateTableColumn.CHECKBOX.index).apply {
        minWidth = CHECKBOX_MIN_WIDTH
        maxWidth = CHECKBOX_MAX_WIDTH
    }
}

/**
 * clear database table
 * with Cancel Action Button
 */
private fun GeneratorDialog.clearTableActionPerformed() {
    tables = mutableListOf()
    setTables()
}

/**
 * Entity File Generate
 * with Create Action Button
 */
private fun GeneratorDialog.createActionPerformed() {
    val eg = EntityGenerator().apply {
        isAllNullable = nullableCheckBox.isSelected
        targetDirectory = directoryLabel.text
    }
    var denyGenerateFileName = arrayOf<String>()
    val maxCount = databaseTable.rowCount - 1
    for (i in 0..maxCount) {
        if (databaseTable.model.getValueAt(i, GenerateTableColumn.CHECKBOX.index) == false) {
            denyGenerateFileName +=
                databaseTable.model.getValueAt(i, GenerateTableColumn.TABLE_NAME.index).toString()
        }
    }
    val targetTables = tables.filterNot { table ->
        denyGenerateFileName.any { it == table.name }
    }
    eg.execute(tables = targetTables)
    Messages.showMessageDialog(
        "${eg.lastCreatedFileCount} file created",
        "Information",
        AllIcons.General.InformationDialog
    )
}
