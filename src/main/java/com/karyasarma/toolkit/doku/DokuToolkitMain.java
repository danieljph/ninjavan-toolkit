package com.karyasarma.toolkit.doku;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karyasarma.toolkit.doku.model.Log;
import com.karyasarma.toolkit.doku.ui.SimpleMenu;
import com.karyasarma.toolkit.doku.util.DbeaverUtils;
import com.karyasarma.toolkit.doku.util.EncryptionUtils;
import com.karyasarma.toolkit.doku.util.JsonSchemaUtil;
import com.karyasarma.toolkit.doku.util.LiquibaseYamlUtils;
import com.karyasarma.toolkit.util.XmlUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 *
 * @author Daniel Joi Partogi Hutapea
 */
@SuppressWarnings("WeakerAccess")
public class DokuToolkitMain implements ActionListener
{
    private final java.util.List<SimpleMenu> listOfSimpleMenu = new ArrayList<>();
    private final SimpleMenu separatorSm = new SimpleMenu("Separator");

    private Menu getPreviousClipboardDataMi;
    private MenuItem clearPreviousClipboardDataMi;

    private final SimpleMenu parseLogsSimplifiedSm = new SimpleMenu("Parse Logs (Simplified)", new MenuShortcut(KeyEvent.VK_P));
    private final SimpleMenu parseLogsSm = new SimpleMenu("Parse Logs", new MenuShortcut(KeyEvent.VK_P, true));
    private final SimpleMenu parseLogsRemoveFailedLineSm = new SimpleMenu("Parse Logs (Remove Un-parsed Line)");

    private final SimpleMenu prettyJsonSm = new SimpleMenu("Pretty JSON", new MenuShortcut(KeyEvent.VK_J));
    private final SimpleMenu compactJsonSm = new SimpleMenu("Compact JSON", new MenuShortcut(KeyEvent.VK_J, true));

    private final SimpleMenu dbeaverCopyAsJsonToTextParentSm = new SimpleMenu("DBeaver \"Copy as JSON\" to Text", new MenuShortcut(KeyEvent.VK_D));
    private final SimpleMenu dbeaverCopyAsJsonToTextPrintNullAsEmptyStringSm = new SimpleMenu("Print [NULL] as Empty String", new MenuShortcut(KeyEvent.VK_D, true));
    private final SimpleMenu dbeaverCopyAsJsonToTextPrintNullAsEmptyStringAndSortByColumnNameSm = new SimpleMenu("Print [NULL] as Empty String + Sort by Column Name", new MenuShortcut(KeyEvent.VK_E, true));
    private final SimpleMenu dbeaverCopyAsJsonToTextSortByColumnNameSm = new SimpleMenu("Sort by Column Name", new MenuShortcut(KeyEvent.VK_E));

    private final SimpleMenu createJsonSchemaSmMode1 = new SimpleMenu("Create JSON Schema", new MenuShortcut(KeyEvent.VK_H));
    private final SimpleMenu createJsonSchemaSmMode2 = new SimpleMenu("Create JSON Schema (Array Contains)", new MenuShortcut(KeyEvent.VK_I));

    private final SimpleMenu prettyXmlSm = new SimpleMenu("Pretty XML", new MenuShortcut(KeyEvent.VK_X));
    private final SimpleMenu compactXmlSm = new SimpleMenu("Compact XML", new MenuShortcut(KeyEvent.VK_X, true));

    private final SimpleMenu aesEncryptSm = new SimpleMenu("AES Encrypt (UAT)", new MenuShortcut(KeyEvent.VK_A));
    private final SimpleMenu aesDecryptSm = new SimpleMenu("AES Decrypt (UAT)", new MenuShortcut(KeyEvent.VK_A, true));

    private final SimpleMenu getFileContentSm = new SimpleMenu("Get File Content", new MenuShortcut(KeyEvent.VK_F));
    private final SimpleMenu sortAlphabeticallySm = new SimpleMenu("Sort Alphabetically", new MenuShortcut(KeyEvent.VK_S));
    private final SimpleMenu generateLiquibaseYamlIdSm = new SimpleMenu("Generate Liquibase YAML ID", new MenuShortcut(KeyEvent.VK_Y));
    private final SimpleMenu toOldCurlSm = new SimpleMenu("To Old cURL");

    private final SimpleMenu passwordVpnSm = new SimpleMenu("Password VPN", new MenuShortcut(KeyEvent.VK_V));
    private final SimpleMenu passwordLdapSm = new SimpleMenu("Password LDAP", new MenuShortcut(KeyEvent.VK_L));
    private MenuItem quitMi;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String previousClipboardData;

    public DokuToolkitMain()
    {
    }

    @SuppressWarnings("DuplicatedCode")
    public void showSystemTray() throws AWTException
    {
        if(!SystemTray.isSupported())
        {
            JOptionPane.showMessageDialog(null, "SystemTray is not supported.", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        PopupMenu popupMenu = new PopupMenu();

        getPreviousClipboardDataMi = new Menu("Get Previous Clipboard Data");
        getPreviousClipboardDataMi.addActionListener(this);
        getPreviousClipboardDataMi.setEnabled(false);
        getPreviousClipboardDataMi.setShortcut(new MenuShortcut(KeyEvent.VK_C));
        popupMenu.add(getPreviousClipboardDataMi);

        clearPreviousClipboardDataMi = new MenuItem("Clear Previous Clipboard Data");
        clearPreviousClipboardDataMi.addActionListener(this);
        clearPreviousClipboardDataMi.setEnabled(false);
        clearPreviousClipboardDataMi.setShortcut(new MenuShortcut(KeyEvent.VK_C, true));
        getPreviousClipboardDataMi.add(clearPreviousClipboardDataMi);

        popupMenu.addSeparator();

        listOfSimpleMenu.add(parseLogsSimplifiedSm);
        listOfSimpleMenu.add(parseLogsSm);
        listOfSimpleMenu.add(parseLogsRemoveFailedLineSm);

        listOfSimpleMenu.add(separatorSm);

        listOfSimpleMenu.add(prettyJsonSm);
        listOfSimpleMenu.add(compactJsonSm);
        listOfSimpleMenu.add(dbeaverCopyAsJsonToTextParentSm);

        dbeaverCopyAsJsonToTextParentSm.addChild(dbeaverCopyAsJsonToTextPrintNullAsEmptyStringSm);
        dbeaverCopyAsJsonToTextParentSm.addChild(dbeaverCopyAsJsonToTextPrintNullAsEmptyStringAndSortByColumnNameSm);
        dbeaverCopyAsJsonToTextParentSm.addChild(dbeaverCopyAsJsonToTextSortByColumnNameSm);

        listOfSimpleMenu.add(createJsonSchemaSmMode1);
        listOfSimpleMenu.add(createJsonSchemaSmMode2);

        listOfSimpleMenu.add(separatorSm);

        listOfSimpleMenu.add(prettyXmlSm);
        listOfSimpleMenu.add(compactXmlSm);

        listOfSimpleMenu.add(separatorSm);

        listOfSimpleMenu.add(getFileContentSm);
        listOfSimpleMenu.add(sortAlphabeticallySm);
        listOfSimpleMenu.add(generateLiquibaseYamlIdSm);
        listOfSimpleMenu.add(toOldCurlSm);

        listOfSimpleMenu.add(separatorSm);

        listOfSimpleMenu.add(aesEncryptSm);
        listOfSimpleMenu.add(aesDecryptSm);

        listOfSimpleMenu.add(separatorSm);

        listOfSimpleMenu.add(passwordVpnSm);
        listOfSimpleMenu.add(passwordLdapSm);

        for(SimpleMenu simpleMenu : listOfSimpleMenu)
        {
            if(simpleMenu==separatorSm)
            {
                popupMenu.addSeparator();
            }
            else
            {
                if(ObjectUtils.isEmpty(simpleMenu.getChildren()))
                {
                    MenuItem menuItem = new MenuItem(simpleMenu.getName());
                    menuItem.setActionCommand(simpleMenu.getName());
                    menuItem.addActionListener(this);
                    menuItem.setShortcut(simpleMenu.getMenuShortcut());
                    popupMenu.add(menuItem);
                }
                else
                {
                    Menu menu = new Menu(simpleMenu.getName());
                    menu.setActionCommand(simpleMenu.getName());
                    menu.addActionListener(this);
                    menu.setShortcut(simpleMenu.getMenuShortcut());
                    popupMenu.add(menu);

                    java.util.List<SimpleMenu> simpleMenuChildren = simpleMenu.getChildren();

                    for(SimpleMenu simpleMenuChild : simpleMenuChildren)
                    {
                        MenuItem menuItem = new MenuItem(simpleMenuChild.getName());
                        menuItem.setActionCommand(simpleMenuChild.getName());
                        menuItem.addActionListener(this);
                        menuItem.setShortcut(simpleMenuChild.getMenuShortcut());
                        menu.add(menuItem);
                    }
                }
            }
        }

        popupMenu.addSeparator();

        quitMi = new MenuItem("Quit");
        quitMi.setShortcut(new MenuShortcut(KeyEvent.VK_Q));
        quitMi.addActionListener(this);
        popupMenu.add(quitMi);

        Image image = Toolkit.getDefaultToolkit().getImage(DokuToolkitMain.class.getResource("/images/ninjavan.png"));

        TrayIcon trayIcon = new TrayIcon(image, "Doku Toolkit");
        trayIcon.setImageAutoSize(true);
        trayIcon.setPopupMenu(popupMenu);

        SystemTray systemTray = SystemTray.getSystemTray();
        systemTray.add(trayIcon);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void actionPerformed(ActionEvent evt)
    {
        String actionCommand = evt.getActionCommand();

        if(parseLogsSimplifiedSm.getName().equals(actionCommand))
        {
            processCommandParseLog(false, true);
        }
        else if(parseLogsSm.getName().equals(actionCommand))
        {
            processCommandParseLog(false, false);
        }
        else if(parseLogsRemoveFailedLineSm.getName().equals(actionCommand))
        {
            processCommandParseLog(true, false);
        }
        else if(prettyJsonSm.getName().equals(actionCommand))
        {
            try
            {
                String jsonData = (String) getSystemClipboard().getData(DataFlavor.stringFlavor);
                System.out.println("JSON Data: \n"+jsonData);
                Object temp = objectMapper.readValue(jsonData, Object.class);
                copyToClipboard(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(temp), jsonData);
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
        }
        else if(compactJsonSm.getName().equals(actionCommand))
        {
            try
            {
                String jsonData = (String) getSystemClipboard().getData(DataFlavor.stringFlavor);
                System.out.println("JSON Data: \n"+jsonData);
                Object temp = objectMapper.readValue(jsonData, Object.class);
                copyToClipboard(objectMapper.writeValueAsString(temp), jsonData);
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
        }
        else if(dbeaverCopyAsJsonToTextParentSm.getName().equals(actionCommand))
        {
            processCommandDbeaverCopyAsJsonToText(false, false);
        }
        else if(dbeaverCopyAsJsonToTextPrintNullAsEmptyStringSm.getName().equals(actionCommand))
        {
            processCommandDbeaverCopyAsJsonToText(true, false);
        }
        else if(dbeaverCopyAsJsonToTextPrintNullAsEmptyStringAndSortByColumnNameSm.getName().equals(actionCommand))
        {
            processCommandDbeaverCopyAsJsonToText(true, true);
        }
        else if(dbeaverCopyAsJsonToTextSortByColumnNameSm.getName().equals(actionCommand))
        {
            processCommandDbeaverCopyAsJsonToText(false, true);
        }
        else if(createJsonSchemaSmMode1.getName().equals(actionCommand))
        {
            try
            {
                String jsonData = (String) getSystemClipboard().getData(DataFlavor.stringFlavor);
                System.out.println("JSON Data: \n"+jsonData);
                copyToClipboard(JsonSchemaUtil.generateJsonSchema(jsonData, false));
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
        }
        else if(createJsonSchemaSmMode2.getName().equals(actionCommand))
        {
            try
            {
                String jsonData = (String) getSystemClipboard().getData(DataFlavor.stringFlavor);
                System.out.println("JSON Data: \n"+jsonData);
                copyToClipboard(JsonSchemaUtil.generateJsonSchema(jsonData, true));
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
        }
        else if(prettyXmlSm.getName().equals(actionCommand))
        {
            try
            {
                String xmlData = (String) getSystemClipboard().getData(DataFlavor.stringFlavor);
                System.out.println("XML Data: \n"+xmlData);
                copyToClipboard(XmlUtils.prettyPrint(xmlData, 4, false), xmlData);
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
        }
        else if(compactXmlSm.getName().equals(actionCommand))
        {
            try
            {
                String xmlData = (String) getSystemClipboard().getData(DataFlavor.stringFlavor);
                System.out.println("XML Data: \n"+xmlData);
                copyToClipboard(XmlUtils.compactPrint(xmlData), xmlData);
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
        }
        else if(getFileContentSm.getName().equals(actionCommand))
        {
            try
            {
                java.util.List<File> listOfFile = (java.util.List<File>) getSystemClipboard().getData(DataFlavor.javaFileListFlavor);

                if(ObjectUtils.isNotEmpty(listOfFile))
                {
                    File file = listOfFile.get(0); // We only get the first File from list.
                    String fileContent = new String(Files.readAllBytes(file.toPath()));
                    copyToClipboard(fileContent);
                }
            }
            catch(Exception ex)
            {
                copyToClipboard(ex.getClass()+": "+ex.getMessage());
                ex.printStackTrace(System.err);
            }
        }
        else if(sortAlphabeticallySm.getName().equals(actionCommand))
        {
            try
            {
                String unsorted = (String) getSystemClipboard().getData(DataFlavor.stringFlavor);
                System.out.println("Unsorted Data: \n"+unsorted);
                String sorted = Arrays.stream(unsorted.split("\n")).map(String::trim).sorted().collect(Collectors.joining("\n"));
                copyToClipboard(sorted, unsorted);
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
        }
        else if(generateLiquibaseYamlIdSm.getName().equals(actionCommand))
        {
            try
            {
                String liquibaseYaml = (String) getSystemClipboard().getData(DataFlavor.stringFlavor);
                copyToClipboard(LiquibaseYamlUtils.generateLiquibaseChangesetId(liquibaseYaml), liquibaseYaml);
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
                copyToClipboard("ERROR: "+ex.getMessage());
            }
        }
        else if(toOldCurlSm.getName().equals(actionCommand))
        {
            try
            {
                String curlData = (String) getSystemClipboard().getData(DataFlavor.stringFlavor);
                System.out.println("cURL Data: \n"+curlData);
                String curlDataOld = curlData.replaceAll("--data-raw", "--data");
                copyToClipboard(curlDataOld, curlData);
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
        }
        else if(aesEncryptSm.getName().equals(actionCommand))
        {
            try
            {
                String plainPassword = (String) getSystemClipboard().getData(DataFlavor.stringFlavor);
                System.out.println("plainPassword Data: "+plainPassword);

                if(StringUtils.isNotBlank(plainPassword))
                {
                    copyToClipboard(EncryptionUtils.encrypt(plainPassword), plainPassword);
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
        }
        else if(aesDecryptSm.getName().equals(actionCommand))
        {
            try
            {
                String cipherPassword = (String) getSystemClipboard().getData(DataFlavor.stringFlavor);
                System.out.println("cipherPassword Data: "+cipherPassword);

                if(StringUtils.isNotBlank(cipherPassword))
                {
                    copyToClipboard(EncryptionUtils.decrypt(cipherPassword), cipherPassword);
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
        }
        else if(passwordVpnSm.getName().equals(actionCommand))
        {
            String envVarName = "VPN_PASSWORD";
            String passwordVpn = System.getenv(envVarName);

            if(StringUtils.isBlank(passwordVpn))
            {
                JOptionPane.showMessageDialog(null, String.format("Environment variable with name '%s' does not exist!", envVarName), "Error", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                copyToClipboard(passwordVpn);
            }
        }
        else if(passwordLdapSm.getName().equals(actionCommand))
        {
            String envVarName = "LDAP_PASSWORD";
            String passwordVpn = System.getenv(envVarName);

            if(StringUtils.isBlank(passwordVpn))
            {
                JOptionPane.showMessageDialog(null, String.format("Environment variable with name '%s' does not exist!", envVarName), "Error", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                copyToClipboard(passwordVpn);
            }
        }

        Object source = evt.getSource();

        if(source==getPreviousClipboardDataMi)
        {
            copyToClipboard(previousClipboardData);
        }
        else if(source==clearPreviousClipboardDataMi)
        {
            previousClipboardData = null;
            getPreviousClipboardDataMi.setEnabled(false);
            clearPreviousClipboardDataMi.setEnabled(false);
        }
        else if(source==quitMi)
        {
            System.exit(0);
        }
    }

    private void processCommandParseLog(boolean removeFailedLine, boolean simplified)
    {
        try
        {
            String logsData = (String) getSystemClipboard().getData(DataFlavor.stringFlavor);
            System.out.println("Data: \n"+logsData);
            String result = Log.parse(logsData, removeFailedLine, simplified);
            copyToClipboard(result, logsData);
        }
        catch(Exception ex)
        {
            ex.printStackTrace(System.err);
        }
    }

    private void processCommandDbeaverCopyAsJsonToText(boolean printNullAsEmptyString, boolean sortedByColumnName)
    {
        try
        {
            String dbeaverCopyAsJsonData = (String) getSystemClipboard().getData(DataFlavor.stringFlavor);
            System.out.println("DBeaver 'Copy as JSON' Data: \n"+dbeaverCopyAsJsonData);
            String result = DbeaverUtils.fromCopyAsJsonToText(dbeaverCopyAsJsonData, printNullAsEmptyString, sortedByColumnName);
            copyToClipboard(result, dbeaverCopyAsJsonData);
        }
        catch(Exception ex)
        {
            ex.printStackTrace(System.err);
        }
    }

    private void copyToClipboard(String data)
    {
        Clipboard clipboard = getSystemClipboard();
        clipboard.setContents(new StringSelection(data), null);
    }

    private void copyToClipboard(String data, String previousClipboardData)
    {
        copyToClipboard(data);
        this.previousClipboardData = previousClipboardData;
        getPreviousClipboardDataMi.setEnabled(true);
        clearPreviousClipboardDataMi.setEnabled(true);
    }

    public Clipboard getSystemClipboard()
    {
        return Toolkit.getDefaultToolkit().getSystemClipboard();
    }

    public void start()
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            showSystemTray();
        }
        catch(Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public static void main(String[] args)
    {
        if(System.getProperty("os.name").toLowerCase().contains("mac"))
        {
            System.setProperty("apple.awt.UIElement", "true"); // Make no Java Icon in Mac-OS Doc
        }

        new DokuToolkitMain().start();
    }
}
