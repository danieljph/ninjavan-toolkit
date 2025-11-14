package com.karyasarma.toolkit.doku;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.karyasarma.toolkit.doku.model.Log;
import com.karyasarma.toolkit.doku.service.ClipboardListener;
import com.karyasarma.toolkit.doku.ui.SimpleMenu;
import com.karyasarma.toolkit.doku.util.AuSecurityCommonUtils;
import com.karyasarma.toolkit.doku.util.Base64Utils;
import com.karyasarma.toolkit.doku.util.CheatNoBugsUtils;
import com.karyasarma.toolkit.doku.util.ClipboardUtils;
import com.karyasarma.toolkit.doku.util.ConfluenceUtils;
import com.karyasarma.toolkit.doku.util.DbeaverUtils;
import com.karyasarma.toolkit.doku.util.EncryptionUtils;
import com.karyasarma.toolkit.doku.util.JsonSchemaUtil;
import com.karyasarma.toolkit.doku.util.JwtUtils;
import com.karyasarma.toolkit.doku.util.LiquibaseYamlUtils;
import com.karyasarma.toolkit.util.XmlUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Files;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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

    private ClipboardListener clipboardListener;

    private Menu clipboardHistoryMenu;
    private MenuItem clearClipboardHistoryMi;

    private final SimpleMenu parseLogsSimplifiedSm = new SimpleMenu("Parse Logs (Simplified)", new MenuShortcut(KeyEvent.VK_P));
    private final SimpleMenu parseLogsSm = new SimpleMenu("Parse Logs", new MenuShortcut(KeyEvent.VK_P, true));
    private final SimpleMenu parseLogsRemoveFailedLineSm = new SimpleMenu("Parse Logs (Remove Un-parsed Line)");

    private final SimpleMenu prettyJsonSm = new SimpleMenu("Pretty JSON", new MenuShortcut(KeyEvent.VK_J));
    private final SimpleMenu compactJsonSm = new SimpleMenu("Compact JSON", new MenuShortcut(KeyEvent.VK_J, true));
    private final SimpleMenu orderedJsonSm = new SimpleMenu("Ordered JSON", new MenuShortcut(KeyEvent.VK_O, true));

    private final SimpleMenu dbeaverCopyAsJsonToTextParentSm = new SimpleMenu("DBeaver \"Copy as JSON\" to Text", new MenuShortcut(KeyEvent.VK_D));
    private final SimpleMenu dbeaverCopyAsJsonToTextPrintNullAsEmptyStringSm = new SimpleMenu("Print [NULL] as Empty String", new MenuShortcut(KeyEvent.VK_D, true));
    private final SimpleMenu dbeaverCopyAsJsonToTextPrintNullAsEmptyStringAndSortByColumnNameSm = new SimpleMenu("Print [NULL] as Empty String + Sort by Column Name", new MenuShortcut(KeyEvent.VK_E, true));
    private final SimpleMenu dbeaverCopyAsJsonToTextSortByColumnNameSm = new SimpleMenu("Sort by Column Name", new MenuShortcut(KeyEvent.VK_E));

    private final SimpleMenu createJsonSchemaSmMode1 = new SimpleMenu("Create JSON Schema", new MenuShortcut(KeyEvent.VK_H));
    private final SimpleMenu createJsonSchemaSmMode2 = new SimpleMenu("Create JSON Schema (Array Contains)", new MenuShortcut(KeyEvent.VK_I));

    private final SimpleMenu prettyXmlSm = new SimpleMenu("Pretty XML", new MenuShortcut(KeyEvent.VK_X));
    private final SimpleMenu compactXmlSm = new SimpleMenu("Compact XML", new MenuShortcut(KeyEvent.VK_X, true));

    private final SimpleMenu getFileContentSm = new SimpleMenu("Get File Content", new MenuShortcut(KeyEvent.VK_F));
    private final SimpleMenu sortAlphabeticallySm = new SimpleMenu("Sort Alphabetically", new MenuShortcut(KeyEvent.VK_S));
    private final SimpleMenu generateLiquibaseYamlIdSm = new SimpleMenu("Generate Liquibase YAML ID", new MenuShortcut(KeyEvent.VK_Y));

    private final SimpleMenu aesEncryptSm = new SimpleMenu("AES Encrypt (SIT / UAT)", new MenuShortcut(KeyEvent.VK_A));
    private final SimpleMenu aesDecryptSm = new SimpleMenu("AES Decrypt (SIT / UAT)", new MenuShortcut(KeyEvent.VK_A, true));
    private final SimpleMenu auSecurityCommonDevContentSm = new SimpleMenu("AU Security Common Dev Content", new MenuShortcut(KeyEvent.VK_U, true));

    private final SimpleMenu passwordVpnSm = new SimpleMenu("Password VPN", new MenuShortcut(KeyEvent.VK_V));
    private final SimpleMenu passwordLdapSm = new SimpleMenu("Password LDAP", new MenuShortcut(KeyEvent.VK_L));

    private final SimpleMenu miscParentSm = new SimpleMenu("Misc");

    private final SimpleMenu confluenceToCodeBlockPlaintextSm = new SimpleMenu("Confluence - To Code Block - Plaintext", new MenuShortcut(KeyEvent.VK_T));
    private final SimpleMenu confluenceToCodeBlockSqlSm = new SimpleMenu("Confluence - To Code Block - SQL", new MenuShortcut(KeyEvent.VK_T));

    private final SimpleMenu jwtDecodeSm = new SimpleMenu("JWT Decode", new MenuShortcut(KeyEvent.VK_W));
    private final SimpleMenu jwtEncodeSm = new SimpleMenu("JWT Encode", new MenuShortcut(KeyEvent.VK_W, true));

    private final SimpleMenu base64EncodeSm = new SimpleMenu("Base64 Encode", new MenuShortcut(KeyEvent.VK_B));
    private final SimpleMenu base64EncodeUrlSafeSm = new SimpleMenu("Base64 Encode URL Safe");
    private final SimpleMenu base64DecodeSm = new SimpleMenu("Base64 Decode", new MenuShortcut(KeyEvent.VK_B, true));

    private final SimpleMenu epochSecondsSm = new SimpleMenu("Epoch Seconds");
    private final SimpleMenu epochMillisecondsSm = new SimpleMenu("Epoch Milliseconds");
    private final SimpleMenu nowAtUtcSm = new SimpleMenu("Now - ISO-8601 UTC");
    private final SimpleMenu nowAtUtcPlus7Sm = new SimpleMenu("Now - ISO-8601 UTC+7");

    private final SimpleMenu toOldCurlSm = new SimpleMenu("To Old cURL");

    private final SimpleMenu cheatNoBugsSm = new SimpleMenu("Cheat No Bugs");

    private MenuItem quitMi;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final ObjectMapper objectMapperPrettyAndSortPropertiesAlphabetically = new ObjectMapper()
        .enable(SerializationFeature.INDENT_OUTPUT)
        .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

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

        clipboardHistoryMenu = new Menu("Clipboard History On");
        clipboardHistoryMenu.addActionListener(this);
        popupMenu.add(clipboardHistoryMenu);

        clearClipboardHistoryMi = new MenuItem("Clear History");
        clearClipboardHistoryMi.addActionListener(this);
        clearClipboardHistoryMi.setShortcut(new MenuShortcut(KeyEvent.VK_C, true));

        popupMenu.addSeparator();

        listOfSimpleMenu.add(parseLogsSimplifiedSm);
        listOfSimpleMenu.add(parseLogsSm);
        listOfSimpleMenu.add(parseLogsRemoveFailedLineSm);

        listOfSimpleMenu.add(separatorSm);

        listOfSimpleMenu.add(prettyJsonSm);
        listOfSimpleMenu.add(compactJsonSm);
        listOfSimpleMenu.add(orderedJsonSm);
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

        listOfSimpleMenu.add(separatorSm);

        listOfSimpleMenu.add(aesEncryptSm);
        listOfSimpleMenu.add(aesDecryptSm);
        listOfSimpleMenu.add(auSecurityCommonDevContentSm);

        listOfSimpleMenu.add(separatorSm);

        listOfSimpleMenu.add(passwordVpnSm);
        listOfSimpleMenu.add(passwordLdapSm);

        listOfSimpleMenu.add(0, miscParentSm);

        listOfSimpleMenu.add(1, separatorSm);

        miscParentSm.addChild(confluenceToCodeBlockPlaintextSm);
        miscParentSm.addChild(confluenceToCodeBlockSqlSm);

        miscParentSm.addChild(separatorSm);

        miscParentSm.addChild(jwtDecodeSm);
        miscParentSm.addChild(jwtEncodeSm);

        miscParentSm.addChild(separatorSm);

        miscParentSm.addChild(base64EncodeSm);
        miscParentSm.addChild(base64EncodeUrlSafeSm);
        miscParentSm.addChild(base64DecodeSm);

        miscParentSm.addChild(separatorSm);

        miscParentSm.addChild(epochSecondsSm);
        miscParentSm.addChild(epochMillisecondsSm);
        miscParentSm.addChild(nowAtUtcSm);
        miscParentSm.addChild(nowAtUtcPlus7Sm);

        miscParentSm.addChild(separatorSm);

        miscParentSm.addChild(toOldCurlSm);

        miscParentSm.addChild(separatorSm);

        miscParentSm.addChild(cheatNoBugsSm);

        for(SimpleMenu simpleMenu : listOfSimpleMenu)
        {
            if(simpleMenu == separatorSm)
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
                        if(simpleMenuChild == separatorSm)
                        {
                            menu.addSeparator();
                        }
                        else
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

        clipboardListener = new ClipboardListener(clipboardHistoryMenu, clearClipboardHistoryMi);
        clipboardListener.startListening();
    }

    @SuppressWarnings("DuplicatedCode")
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
                String jsonData = ClipboardUtils.getDataFromStringFlavor();
                System.out.println("JSON Data: \n"+jsonData);
                Object temp = objectMapper.readValue(jsonData, Object.class);
                ClipboardUtils.copyToClipboard(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(temp));
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
                String jsonData = ClipboardUtils.getDataFromStringFlavor();
                System.out.println("JSON Data: \n"+jsonData);
                Object temp = objectMapper.readValue(jsonData, Object.class);
                ClipboardUtils.copyToClipboard(objectMapper.writeValueAsString(temp));
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
        }
        else if(orderedJsonSm.getName().equals(actionCommand))
        {
            try
            {
                String jsonData = ClipboardUtils.getDataFromStringFlavor();
                System.out.println("JSON Data: \n"+jsonData);
                Object temp = objectMapperPrettyAndSortPropertiesAlphabetically.readValue(jsonData, Object.class);
                ClipboardUtils.copyToClipboard(objectMapperPrettyAndSortPropertiesAlphabetically.writeValueAsString(temp));
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
                String jsonData = ClipboardUtils.getDataFromStringFlavor();
                System.out.println("JSON Data: \n"+jsonData);
                ClipboardUtils.copyToClipboard(JsonSchemaUtil.generateJsonSchema(jsonData, false));
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
                String jsonData = ClipboardUtils.getDataFromStringFlavor();
                System.out.println("JSON Data: \n"+jsonData);
                ClipboardUtils.copyToClipboard(JsonSchemaUtil.generateJsonSchema(jsonData, true));
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
                String xmlData = ClipboardUtils.getDataFromStringFlavor();
                System.out.println("XML Data: \n"+xmlData);
                ClipboardUtils.copyToClipboard(XmlUtils.prettyPrint(xmlData, 4, false));
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
                String xmlData = ClipboardUtils.getDataFromStringFlavor();
                System.out.println("XML Data: \n"+xmlData);
                ClipboardUtils.copyToClipboard(XmlUtils.compactPrint(xmlData));
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
                java.util.List<File> listOfFile = ClipboardUtils.getData(DataFlavor.javaFileListFlavor);

                if(ObjectUtils.isNotEmpty(listOfFile))
                {
                    File file = listOfFile.get(0); // We only get the first File from a list.
                    String fileContent = new String(Files.readAllBytes(file.toPath()));
                    ClipboardUtils.copyToClipboard(fileContent);
                }
            }
            catch(Exception ex)
            {
                ClipboardUtils.copyToClipboard(ex.getClass()+": "+ex.getMessage());
                ex.printStackTrace(System.err);
            }
        }
        else if(sortAlphabeticallySm.getName().equals(actionCommand))
        {
            try
            {
                String unsorted = ClipboardUtils.getDataFromStringFlavor();
                System.out.println("Unsorted Data: \n"+unsorted);
                String sorted = Arrays.stream(unsorted.split("\n")).map(String::trim).sorted().collect(Collectors.joining("\n"));
                ClipboardUtils.copyToClipboard(sorted);
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
                String liquibaseYaml = ClipboardUtils.getDataFromStringFlavor();
                ClipboardUtils.copyToClipboard(LiquibaseYamlUtils.generateLiquibaseChangesetId(liquibaseYaml));
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
                ClipboardUtils.copyToClipboard("ERROR: "+ex.getMessage());
            }
        }
        else if(aesEncryptSm.getName().equals(actionCommand))
        {
            try
            {
                String plainPassword = ClipboardUtils.getDataFromStringFlavor();
                System.out.println("plainPassword Data: "+plainPassword);

                if(StringUtils.isNotBlank(plainPassword))
                {
                    ClipboardUtils.copyToClipboard(EncryptionUtils.encrypt(plainPassword));
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
                String cipherPassword = ClipboardUtils.getDataFromStringFlavor();
                System.out.println("cipherPassword Data: "+cipherPassword);

                if(StringUtils.isNotBlank(cipherPassword))
                {
                    ClipboardUtils.copyToClipboard(EncryptionUtils.decrypt(cipherPassword));
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
        }
        else if(auSecurityCommonDevContentSm.getName().equals(actionCommand))
        {
            ClipboardUtils.copyToClipboard(AuSecurityCommonUtils.getAuSecurityCommonDevContent());
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
                ClipboardUtils.copyToClipboard(passwordVpn);
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
                ClipboardUtils.copyToClipboard(passwordVpn);
            }
        }
        else if(confluenceToCodeBlockPlaintextSm.getName().equals(actionCommand))
        {
            try
            {
                String data = ClipboardUtils.getDataFromStringFlavor();
                System.out.println("Data: \n" + data);
                ClipboardUtils.copyToClipboard(ConfluenceUtils.toCodeBlockPlainText(data));
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
        }
        else if(confluenceToCodeBlockSqlSm.getName().equals(actionCommand))
        {
            try
            {
                String data = ClipboardUtils.getDataFromStringFlavor();
                System.out.println("Data: \n" + data);
                ClipboardUtils.copyToClipboard(ConfluenceUtils.toCodeBlockSql(data));
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
        }
        else if(jwtDecodeSm.getName().equals(actionCommand))
        {
            try
            {
                String data = ClipboardUtils.getDataFromStringFlavor();
                System.out.println("Data: \n" + data);
                ClipboardUtils.copyToClipboard(JwtUtils.decode(data));
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
        }
        else if(jwtEncodeSm.getName().equals(actionCommand))
        {
            try
            {
                String data = ClipboardUtils.getDataFromStringFlavor();
                System.out.println("Data: \n" + data);
                ClipboardUtils.copyToClipboard(JwtUtils.encode(data));
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
        }
        else if(base64EncodeSm.getName().equals(actionCommand))
        {
            try
            {
                String data = ClipboardUtils.getDataFromStringFlavor();
                System.out.println("Data: \n" + data);
                ClipboardUtils.copyToClipboard(Base64Utils.encode(data));
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
        }
        else if(base64EncodeUrlSafeSm.getName().equals(actionCommand))
        {
            try
            {
                String data = ClipboardUtils.getDataFromStringFlavor();
                System.out.println("Data: \n" + data);
                ClipboardUtils.copyToClipboard(Base64Utils.encodeUrlSafe(data));
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
        }
        else if(base64DecodeSm.getName().equals(actionCommand))
        {
            try
            {
                String data = ClipboardUtils.getDataFromStringFlavor();
                System.out.println("Data: \n" + data);
                ClipboardUtils.copyToClipboard(Base64Utils.decode(data));
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
        }
        else if(epochSecondsSm.getName().equals(actionCommand))
        {
            ClipboardUtils.copyToClipboard(String.valueOf(System.currentTimeMillis()/1000));
        }
        else if(epochMillisecondsSm.getName().equals(actionCommand))
        {
            ClipboardUtils.copyToClipboard(String.valueOf(System.currentTimeMillis()));
        }
        else if(nowAtUtcSm.getName().equals(actionCommand))
        {
            ClipboardUtils.copyToClipboard(ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        }
        else if(nowAtUtcPlus7Sm.getName().equals(actionCommand))
        {
            ClipboardUtils.copyToClipboard(ZonedDateTime.now().withZoneSameInstant(ZoneId.of("Asia/Jakarta")).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        }
        else if(toOldCurlSm.getName().equals(actionCommand))
        {
            try
            {
                String curlData = ClipboardUtils.getDataFromStringFlavor();
                System.out.println("cURL Data: \n"+curlData);
                String curlDataOld = curlData.replaceAll("--data-raw", "--data");
                ClipboardUtils.copyToClipboard(curlDataOld);
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
        }
        else if(cheatNoBugsSm.getName().equals(actionCommand))
        {
            ClipboardUtils.copyToClipboard(CheatNoBugsUtils.getCheatNoBugs());
        }

        Object source = evt.getSource();

        if(source == clipboardHistoryMenu)
        {
            clipboardListener.onMenuClicked();
        }
        else if(source == clearClipboardHistoryMi)
        {
            clipboardListener.clearClipboardHistory();
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
            String logsData = ClipboardUtils.getDataFromStringFlavor();
            System.out.println("Data: \n"+logsData);
            String result = Log.parse(logsData, removeFailedLine, simplified);
            ClipboardUtils.copyToClipboard(result);
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
            String dbeaverCopyAsJsonData = ClipboardUtils.getDataFromStringFlavor();
            System.out.println("DBeaver 'Copy as JSON' Data: \n"+dbeaverCopyAsJsonData);
            String result = DbeaverUtils.fromCopyAsJsonToText(dbeaverCopyAsJsonData, printNullAsEmptyString, sortedByColumnName);
            ClipboardUtils.copyToClipboard(result);
        }
        catch(Exception ex)
        {
            ex.printStackTrace(System.err);
        }
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
            System.setProperty("apple.awt.UIElement", "true"); // Make no Java Icon in macOS Doc
        }

        new DokuToolkitMain().start();
    }
}
