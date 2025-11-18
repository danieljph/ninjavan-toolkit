package com.karyasarma.toolkit.doku;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.karyasarma.toolkit.doku.ausecurity.EncryptionService;
import com.karyasarma.toolkit.doku.ausecurity.InputDataType;
import com.karyasarma.toolkit.doku.model.Log;
import com.karyasarma.toolkit.doku.service.ClipboardListener;
import com.karyasarma.toolkit.doku.support.CheckedRunnable;
import com.karyasarma.toolkit.doku.ui.SimpleMenu;
import com.karyasarma.toolkit.doku.util.AuSecurityCommonUtils;
import com.karyasarma.toolkit.doku.util.Base64Utils;
import com.karyasarma.toolkit.doku.util.CheatNoBugsUtils;
import com.karyasarma.toolkit.doku.util.ClipboardUtils;
import com.karyasarma.toolkit.doku.util.ConfluenceUtils;
import com.karyasarma.toolkit.doku.util.DbeaverUtils;
import com.karyasarma.toolkit.doku.util.JsonSchemaUtil;
import com.karyasarma.toolkit.doku.util.JwtUtils;
import com.karyasarma.toolkit.doku.util.LiquibaseYamlUtils;
import com.karyasarma.toolkit.doku.util.Mp3Utils;
import com.karyasarma.toolkit.util.XmlUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 *
 * @author Daniel Joi Partogi Hutapea
 */
@SuppressWarnings("WeakerAccess")
public class DokuToolkitMain implements ActionListener, NativeKeyListener
{
    private final java.util.List<SimpleMenu> listOfSimpleMenu = new ArrayList<>();
    private final SimpleMenu separatorSm = new SimpleMenu("Separator");

    private ClipboardListener clipboardListener;

    private Menu clipboardHistoryMenu;
    private MenuItem clipboardToPlainTextMi;

    @SuppressWarnings("FieldCanBeLocal")
    private CheckboxMenuItem keepPositionMi;

    private MenuItem clearClipboardHistoryMi;

    private final SimpleMenu parseLogsSimplifiedSm = new SimpleMenu("Parse Logs (Simplified)", new MenuShortcut(KeyEvent.VK_P));
    private final SimpleMenu parseLogsSm = new SimpleMenu("Parse Logs", new MenuShortcut(KeyEvent.VK_P, true));
    private final SimpleMenu parseLogsRemoveFailedLineSm = new SimpleMenu("Parse Logs (Remove Un-parsed Line)");

    private final SimpleMenu prettyJsonSm = new SimpleMenu("Pretty JSON ⭐", new MenuShortcut(KeyEvent.VK_J));
    private final SimpleMenu compactJsonSm = new SimpleMenu("Compact JSON ⭐", new MenuShortcut(KeyEvent.VK_J, true));
    private final SimpleMenu orderedJsonSm = new SimpleMenu("Ordered JSON", new MenuShortcut(KeyEvent.VK_O, true));

    private final SimpleMenu dbeaverCopyAsJsonToTextParentSm = new SimpleMenu("DBeaver \"Copy as JSON\" to Text", new MenuShortcut(KeyEvent.VK_D));
    private final SimpleMenu dbeaverCopyAsJsonToTextPrintNullAsEmptyStringSm = new SimpleMenu("Print [NULL] as Empty String", new MenuShortcut(KeyEvent.VK_D, true));
    private final SimpleMenu dbeaverCopyAsJsonToTextPrintNullAsEmptyStringAndSortByColumnNameSm = new SimpleMenu("Print [NULL] as Empty String + Sort by Column Name");
    private final SimpleMenu dbeaverCopyAsJsonToTextSortByColumnNameSm = new SimpleMenu("Sort by Column Name");

    private final SimpleMenu createJsonSchemaSmMode1 = new SimpleMenu("Create JSON Schema", new MenuShortcut(KeyEvent.VK_H));
    private final SimpleMenu createJsonSchemaSmMode2 = new SimpleMenu("Create JSON Schema (Array Contains)", new MenuShortcut(KeyEvent.VK_I));

    private final SimpleMenu prettyXmlSm = new SimpleMenu("Pretty XML", new MenuShortcut(KeyEvent.VK_X));
    private final SimpleMenu compactXmlSm = new SimpleMenu("Compact XML", new MenuShortcut(KeyEvent.VK_X, true));

    private final SimpleMenu getFileContentSm = new SimpleMenu("Get File Content", new MenuShortcut(KeyEvent.VK_F));
    private final SimpleMenu sortAlphabeticallySm = new SimpleMenu("Sort Alphabetically", new MenuShortcut(KeyEvent.VK_S));
    private final SimpleMenu generateLiquibaseYamlIdSm = new SimpleMenu("Generate Liquibase YAML ID", new MenuShortcut(KeyEvent.VK_Y));

    private final SimpleMenu aesEncryptSm = new SimpleMenu("AES Encrypt (SIT / UAT)", new MenuShortcut(KeyEvent.VK_A));
    private final SimpleMenu aesDecryptSm = new SimpleMenu("AES Decrypt (SIT / UAT)", new MenuShortcut(KeyEvent.VK_A, true));
    private final SimpleMenu auSecurityCommonDevContentSm = new SimpleMenu("AU Security Common Dev Content");

    private final SimpleMenu passwordVpnSm = new SimpleMenu("Password VPN ⭐", new MenuShortcut(KeyEvent.VK_L, true));
    private final SimpleMenu passwordLdapSm = new SimpleMenu("Password LDAP ⭐", new MenuShortcut(KeyEvent.VK_L));

    private final SimpleMenu miscParentSm = new SimpleMenu("Misc");

    private final SimpleMenu confluenceToCodeBlockPlaintextSm = new SimpleMenu("Confluence - To Code Block - Plaintext ⭐", new MenuShortcut(KeyEvent.VK_U, true));
    private final SimpleMenu confluenceToCodeBlockSqlSm = new SimpleMenu("Confluence - To Code Block - SQL");
    private final SimpleMenu confluenceBlankCodeBlockPlaintextSm = new SimpleMenu("Confluence - Blank Code Block - Plaintext");
    private final SimpleMenu confluenceBlankCodeBlockSqlSm = new SimpleMenu("Confluence - Blank Code Block - SQL");
    private final SimpleMenu confluenceBlankExpandSm = new SimpleMenu("Confluence - Blank Expand");
    private final SimpleMenu confluenceBlankExpandPlusCodeBlockSm = new SimpleMenu("Confluence - Blank Expand + Code Block");

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

    private final Map<String, AtomicLong> mapOfMethodCallLastExecution = new HashMap<>();

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

        clipboardToPlainTextMi = new MenuItem("Clipboard to Plain Text ⭐");
        clipboardToPlainTextMi.addActionListener(this);
        clipboardToPlainTextMi.setShortcut(new MenuShortcut(KeyEvent.VK_E));
        popupMenu.add(clipboardToPlainTextMi);

        keepPositionMi = new CheckboxMenuItem("Keep Position of Existing Data", true);
        keepPositionMi.addActionListener(this);

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
        miscParentSm.addChild(confluenceBlankCodeBlockPlaintextSm);
        miscParentSm.addChild(confluenceBlankCodeBlockSqlSm);
        miscParentSm.addChild(confluenceBlankExpandSm);
        miscParentSm.addChild(confluenceBlankExpandPlusCodeBlockSm);

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

        clipboardListener = new ClipboardListener(clipboardHistoryMenu, clearClipboardHistoryMi, keepPositionMi);
        clipboardListener.startListening();
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent keyEvent)
    {
        char keyChar = keyEvent.getKeyChar();
        int modifiers = keyEvent.getModifiers();

        if((modifiers & NativeKeyEvent.CTRL_MASK) != 0 && (keyChar >= '1' && keyChar <= '9'))
        {
            executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime
            (
                () -> clipboardListener.copyToClipboard(Integer.parseInt(keyChar + "") - 1),
                "ctrlPlusNumberClicked"
            );
        }
        else if((modifiers & NativeKeyEvent.CTRL_MASK) != 0 && (keyChar == 'j' || keyChar == 'J'))
        {
            parseLogsSmClicked();
        }
        else if((modifiers & NativeKeyEvent.META_MASK) != 0 && (keyChar == 'e' || keyChar == 'E'))
        {
            clipboardToPlainTextMiClicked();
        }
        else if((modifiers & NativeKeyEvent.META_MASK) != 0 && (modifiers & NativeKeyEvent.SHIFT_MASK) != 0 && (keyChar == 'j' || keyChar == 'J'))
        {
            compactJsonSmClicked();
        }
        else if((modifiers & NativeKeyEvent.META_MASK) != 0 && (keyChar == 'j' || keyChar == 'J'))
        {
            prettyJsonSmClicked();
        }
        else if((modifiers & NativeKeyEvent.META_MASK) != 0 && (modifiers & NativeKeyEvent.SHIFT_MASK) != 0 && (keyChar == 'l' || keyChar == 'L'))
        {
            passwordVpnSmClicked();
        }
        else if((modifiers & NativeKeyEvent.META_MASK) != 0 && (keyChar == 'l' || keyChar == 'L'))
        {
            passwordLdapSmClicked();
        }
        else if((modifiers & NativeKeyEvent.META_MASK) != 0 && (modifiers & NativeKeyEvent.SHIFT_MASK) != 0 && (keyChar == 'u' || keyChar == 'U'))
        {
            confluenceToCodeBlockPlaintextSmClicked();
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void actionPerformed(ActionEvent evt)
    {
        String actionCommand = evt.getActionCommand();

        if(parseLogsSimplifiedSm.getName().equals(actionCommand))
        {
            parseLogsSimplifiedSmClicked();
        }
        else if(parseLogsSm.getName().equals(actionCommand))
        {
            parseLogsSmClicked();
        }
        else if(parseLogsRemoveFailedLineSm.getName().equals(actionCommand))
        {
            parseLogsRemoveFailedLineSmClicked();
        }
        else if(prettyJsonSm.getName().equals(actionCommand))
        {
            prettyJsonSmClicked();
        }
        else if(compactJsonSm.getName().equals(actionCommand))
        {
            compactJsonSmClicked();
        }
        else if(orderedJsonSm.getName().equals(actionCommand))
        {
            orderedJsonSmClicked();
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
            createJsonSchemaSmMode1Clicked();
        }
        else if(createJsonSchemaSmMode2.getName().equals(actionCommand))
        {
            createJsonSchemaSmMode2Clicked();
        }
        else if(prettyXmlSm.getName().equals(actionCommand))
        {
            prettyXmlSmClicked();
        }
        else if(compactXmlSm.getName().equals(actionCommand))
        {
            compactXmlSmClicked();
        }
        else if(getFileContentSm.getName().equals(actionCommand))
        {
            getFileContentSmClicked();
        }
        else if(sortAlphabeticallySm.getName().equals(actionCommand))
        {
            sortAlphabeticallySmClicked();
        }
        else if(generateLiquibaseYamlIdSm.getName().equals(actionCommand))
        {
            generateLiquibaseYamlIdSmClicked();
        }
        else if(aesEncryptSm.getName().equals(actionCommand))
        {
            aesEncryptSmClicked();
        }
        else if(aesDecryptSm.getName().equals(actionCommand))
        {
            aesDecryptSmClicked();
        }
        else if(auSecurityCommonDevContentSm.getName().equals(actionCommand))
        {
            auSecurityCommonDevContentSmClicked();
        }
        else if(passwordVpnSm.getName().equals(actionCommand))
        {
            passwordVpnSmClicked();
        }
        else if(passwordLdapSm.getName().equals(actionCommand))
        {
            passwordLdapSmClicked();
        }
        else if(confluenceToCodeBlockPlaintextSm.getName().equals(actionCommand))
        {
            confluenceToCodeBlockPlaintextSmClicked();
        }
        else if(confluenceToCodeBlockSqlSm.getName().equals(actionCommand))
        {
            confluenceToCodeBlockSqlSmClicked();
        }
        else if(confluenceBlankCodeBlockPlaintextSm.getName().equals(actionCommand))
        {
            confluenceBlankCodeBlockPlaintextSmClicked();
        }
        else if(confluenceBlankCodeBlockSqlSm.getName().equals(actionCommand))
        {
            confluenceBlankCodeBlockSqlSmClicked();
        }
        else if(confluenceBlankExpandSm.getName().equals(actionCommand))
        {
            confluenceBlankExpandSmClicked();
        }
        else if(confluenceBlankExpandPlusCodeBlockSm.getName().equals(actionCommand))
        {
            confluenceBlankExpandPlusCodeBlockSmClicked();
        }
        else if(jwtDecodeSm.getName().equals(actionCommand))
        {
            jwtDecodeSmClicked();
        }
        else if(jwtEncodeSm.getName().equals(actionCommand))
        {
            jwtEncodeSmClicked();
        }
        else if(base64EncodeSm.getName().equals(actionCommand))
        {
            base64EncodeSmClicked();
        }
        else if(base64EncodeUrlSafeSm.getName().equals(actionCommand))
        {
            base64EncodeUrlSafeSmClicked();
        }
        else if(base64DecodeSm.getName().equals(actionCommand))
        {
            base64DecodeSmClicked();
        }
        else if(epochSecondsSm.getName().equals(actionCommand))
        {
            epochSecondsSmClicked();
        }
        else if(epochMillisecondsSm.getName().equals(actionCommand))
        {
            epochMillisecondsSmClicked();
        }
        else if(nowAtUtcSm.getName().equals(actionCommand))
        {
            nowAtUtcSmClicked();
        }
        else if(nowAtUtcPlus7Sm.getName().equals(actionCommand))
        {
            nowAtUtcPlus7SmClicked();
        }
        else if(toOldCurlSm.getName().equals(actionCommand))
        {
            toOldCurlSmClicked();
        }
        else if(cheatNoBugsSm.getName().equals(actionCommand))
        {
            cheatNoBugsSmClicked();
        }
        else
        {
            Object source = evt.getSource();

            if(source == clipboardHistoryMenu)
            {
                clipboardHistoryMenuClicked();
            }
            else if(source == clipboardToPlainTextMi)
            {
                clipboardToPlainTextMiClicked();
            }
            else if(source == clearClipboardHistoryMi)
            {
                clearClipboardHistoryMiClicked();
            }
            else if(source==quitMi)
            {
                System.exit(0);
            }
        }
    }

    private void processCommandParseLog(boolean removeFailedLine, boolean simplified) throws IOException, UnsupportedFlavorException
    {
        String logsData = ClipboardUtils.getDataFromStringFlavor();
        String result = Log.parse(logsData, removeFailedLine, simplified);
        ClipboardUtils.copyToClipboard(result);
    }

    private void processCommandDbeaverCopyAsJsonToText(boolean printNullAsEmptyString, boolean sortedByColumnName)
    {
        executeWithExceptionHandling(() ->
        {
            String dbeaverCopyAsJsonData = ClipboardUtils.getDataFromStringFlavor();
            String result = DbeaverUtils.fromCopyAsJsonToText(dbeaverCopyAsJsonData, printNullAsEmptyString, sortedByColumnName);
            ClipboardUtils.copyToClipboard(result);
        });
    }

    private void parseLogsSimplifiedSmClicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime
        (
            () -> processCommandParseLog(false, true),
            "parseLogsSimplifiedSmClicked"
        );
    }

    private void parseLogsSmClicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime
        (
            () -> processCommandParseLog(false, false),
            "parseLogsSmClicked"
        );
    }

    private void parseLogsRemoveFailedLineSmClicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime
        (
            () -> processCommandParseLog(true, false),
            "parseLogsRemoveFailedLineSmClicked"
        );
    }

    private void prettyJsonSmClicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime(() ->
        {
            String jsonData = ClipboardUtils.getDataFromStringFlavor();
            Object temp = objectMapper.readValue(jsonData, Object.class);
            ClipboardUtils.copyToClipboard(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(temp));
        }, "prettyJsonSmClicked");
    }

    private void compactJsonSmClicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime(() ->
        {
            String jsonData = ClipboardUtils.getDataFromStringFlavor();
            Object temp = objectMapper.readValue(jsonData, Object.class);
            ClipboardUtils.copyToClipboard(objectMapper.writeValueAsString(temp));
        }, "compactJsonSmClicked");
    }

    private void orderedJsonSmClicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime(() ->
        {
            String jsonData = ClipboardUtils.getDataFromStringFlavor();
            Object temp = objectMapperPrettyAndSortPropertiesAlphabetically.readValue(jsonData, Object.class);
            ClipboardUtils.copyToClipboard(objectMapperPrettyAndSortPropertiesAlphabetically.writeValueAsString(temp));
        }, "orderedJsonSmClicked");
    }

    private void createJsonSchemaSmMode1Clicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime(() ->
        {
            String jsonData = ClipboardUtils.getDataFromStringFlavor();
            ClipboardUtils.copyToClipboard(JsonSchemaUtil.generateJsonSchema(jsonData, false));
        }, "createJsonSchemaSmMode1Clicked");
    }

    private void createJsonSchemaSmMode2Clicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime(() ->
        {
            String jsonData = ClipboardUtils.getDataFromStringFlavor();
            ClipboardUtils.copyToClipboard(JsonSchemaUtil.generateJsonSchema(jsonData, true));
        }, "createJsonSchemaSmMode2Clicked");
    }

    private void prettyXmlSmClicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime(() ->
        {
            String xmlData = ClipboardUtils.getDataFromStringFlavor();
            ClipboardUtils.copyToClipboard(XmlUtils.prettyPrint(xmlData, 4, false));
        }, "prettyXmlSmClicked");
    }

    private void compactXmlSmClicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime(() ->
        {
            String xmlData = ClipboardUtils.getDataFromStringFlavor();
            ClipboardUtils.copyToClipboard(XmlUtils.compactPrint(xmlData));
        }, "compactXmlSmClicked");
    }

    private void getFileContentSmClicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime(() ->
        {
            java.util.List<File> listOfFile = ClipboardUtils.getData(DataFlavor.javaFileListFlavor);

            if(ObjectUtils.isNotEmpty(listOfFile))
            {
                File file = listOfFile.get(0); // We only get the first File from a list.
                String fileContent = new String(Files.readAllBytes(file.toPath()));
                ClipboardUtils.copyToClipboard(fileContent);
            }
        }, "getFileContentSmClicked");
    }

    private void sortAlphabeticallySmClicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime(() ->
        {
            String unsorted = ClipboardUtils.getDataFromStringFlavor();
            String sorted = Arrays.stream(unsorted.split("\n")).map(String::trim).sorted().collect(Collectors.joining("\n"));
            ClipboardUtils.copyToClipboard(sorted);
        }, "sortAlphabeticallySmClicked");
    }

    private void generateLiquibaseYamlIdSmClicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime(() ->
        {
            String liquibaseYaml = ClipboardUtils.getDataFromStringFlavor();
            ClipboardUtils.copyToClipboard(LiquibaseYamlUtils.generateLiquibaseChangesetId(liquibaseYaml));
        }, "generateLiquibaseYamlIdSmClicked");
    }

    private void aesEncryptSmClicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime(() ->
        {
            String plainPassword = ClipboardUtils.getDataFromStringFlavor();

            if(StringUtils.isNotBlank(plainPassword))
            {
                ClipboardUtils.copyToClipboard(EncryptionService.encryptStringAscii(plainPassword, InputDataType.CREDENTIAL_SYSTEM));
            }
        }, "aesEncryptSmClicked");
    }

    private void aesDecryptSmClicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime(() ->
        {
            String cipherPassword = ClipboardUtils.getDataFromStringFlavor();

            if(StringUtils.isNotBlank(cipherPassword))
            {
                ClipboardUtils.copyToClipboard(EncryptionService.decrypt(cipherPassword));
            }
        }, "aesDecryptSmClicked");
    }

    private void auSecurityCommonDevContentSmClicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime
        (
            () -> ClipboardUtils.copyToClipboard(AuSecurityCommonUtils.getAuSecurityCommonDevContent()),
            "auSecurityCommonDevContentSmClicked"
        );
    }

    private void passwordVpnSmClicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime(() ->
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
        }, "passwordVpnSmClicked");
    }

    private void passwordLdapSmClicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime(() ->
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
        }, "passwordLdapSmClicked");
    }

    private void confluenceToCodeBlockPlaintextSmClicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime(() ->
        {
            String data = ClipboardUtils.getDataFromStringFlavor();
            ClipboardUtils.copyToClipboard(ConfluenceUtils.toCodeBlockPlaintext(data));
        }, "confluenceToCodeBlockPlaintextSmClicked");
    }

    private void confluenceToCodeBlockSqlSmClicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime(() ->
        {
            String data = ClipboardUtils.getDataFromStringFlavor();
            ClipboardUtils.copyToClipboard(ConfluenceUtils.toCodeBlockSql(data));
        }, "confluenceToCodeBlockSqlSmClicked");
    }

    private void confluenceBlankCodeBlockPlaintextSmClicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime
        (
            () -> ClipboardUtils.copyToClipboard(ConfluenceUtils.blankCodeBlockPlaintext()),
            "confluenceBlankCodeBlockPlaintextSmClicked"
        );
    }

    private void confluenceBlankCodeBlockSqlSmClicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime
        (
            () -> ClipboardUtils.copyToClipboard(ConfluenceUtils.blankCodeBlockSql()),
            "confluenceBlankCodeBlockSqlSmClicked"
        );
    }

    private void confluenceBlankExpandSmClicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime
        (
            () -> ClipboardUtils.copyToClipboard(ConfluenceUtils.blankExpand()),
            "confluenceBlankExpandSmClicked"
        );
    }

    private void confluenceBlankExpandPlusCodeBlockSmClicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime
        (
            () -> ClipboardUtils.copyToClipboard(ConfluenceUtils.blankExpandPlusCodeBlock()),
            "confluenceBlankExpandPlusCodeBlockSmClicked"
        );
    }

    private void jwtDecodeSmClicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime(() ->
        {
            String data = ClipboardUtils.getDataFromStringFlavor();
            ClipboardUtils.copyToClipboard(JwtUtils.decode(data));
        }, "jwtDecodeSmClicked");
    }

    private void jwtEncodeSmClicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime(() ->
        {
            String data = ClipboardUtils.getDataFromStringFlavor();
            ClipboardUtils.copyToClipboard(JwtUtils.encode(data));
        }, "jwtEncodeSmClicked");
    }

    private void base64EncodeSmClicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime(() ->
        {
            String data = ClipboardUtils.getDataFromStringFlavor();
            ClipboardUtils.copyToClipboard(Base64Utils.encode(data));
        }, "base64EncodeSmClicked");
    }

    private void base64EncodeUrlSafeSmClicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime(() ->
        {
            String data = ClipboardUtils.getDataFromStringFlavor();
            ClipboardUtils.copyToClipboard(Base64Utils.encodeUrlSafe(data));
        }, "base64EncodeUrlSafeSmClicked");
    }

    private void base64DecodeSmClicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime(() ->
        {
            String data = ClipboardUtils.getDataFromStringFlavor();
            ClipboardUtils.copyToClipboard(Base64Utils.decode(data));
        }, "base64DecodeSmClicked");
    }

    private void epochSecondsSmClicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime
        (
            () -> ClipboardUtils.copyToClipboard(String.valueOf(System.currentTimeMillis()/1000)),
            "epochSecondsSmClicked"
        );
    }

    private void epochMillisecondsSmClicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime
        (
            () -> ClipboardUtils.copyToClipboard(String.valueOf(System.currentTimeMillis())),
            "epochMillisecondsSmClicked"
        );
    }

    private void nowAtUtcSmClicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime
        (
            () -> ClipboardUtils.copyToClipboard(ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)),
            "nowAtUtcSmClicked"
        );
    }

    private void nowAtUtcPlus7SmClicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime
        (
            () -> ClipboardUtils.copyToClipboard(ZonedDateTime.now().withZoneSameInstant(ZoneId.of("Asia/Jakarta")).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)),
            "nowAtUtcPlus7SmClicked"
        );
    }

    private void toOldCurlSmClicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime(() ->
        {
            String curlData = ClipboardUtils.getDataFromStringFlavor();
            String curlDataOld = curlData.replaceAll("--data-raw", "--data");
            ClipboardUtils.copyToClipboard(curlDataOld);
        }, "toOldCurlSmClicked");
    }

    private void cheatNoBugsSmClicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime
        (
            () -> ClipboardUtils.copyToClipboard(CheatNoBugsUtils.getCheatNoBugs()),
            "cheatNoBugsSmClicked"
        );
    }

    private void clipboardHistoryMenuClicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime
        (
            () -> clipboardListener.onMenuClicked(),
            "clipboardHistoryMenuClicked"
        );
    }

    private void clipboardToPlainTextMiClicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime
        (
            ClipboardUtils::convertCurrentClipboardToPlainText,
            "clipboardToPlainTextMiClicked"
        );
    }

    private void clearClipboardHistoryMiClicked()
    {
        executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime
        (
            () -> clipboardListener.clearClipboardHistory(),
            "clearClipboardHistoryMiClicked"
        );
    }

    @SuppressWarnings({"ReassignedVariable", "SynchronizationOnLocalVariableOrMethodParameter"})
    private void executeWithExceptionHandlingAndAllowOnly1MethodCallAtTheSameTime(CheckedRunnable<Exception> runnable, String methodName)
    {
        AtomicLong methodCallLastExecution = null;

        try
        {
            synchronized(mapOfMethodCallLastExecution)
            {
                methodCallLastExecution = mapOfMethodCallLastExecution.computeIfAbsent(methodName, k -> new AtomicLong(0L));
            }

            synchronized(methodCallLastExecution)
            {
                if(methodCallLastExecution.get() == 0L || Duration.between(Instant.ofEpochMilli(methodCallLastExecution.get()), Instant.now()).compareTo(Duration.ofMillis(500)) > 0)
                {
                    runnable.run();
                    Mp3Utils.playProcessSuccess();
                    methodCallLastExecution.set(System.currentTimeMillis());
                }
            }
        }
        catch(Exception ex)
        {
            Mp3Utils.playProcessFailed();

            if(methodCallLastExecution != null)
            {
                methodCallLastExecution.set(System.currentTimeMillis());
            }

            ex.printStackTrace(System.err);
        }
    }

    private void executeWithExceptionHandling(CheckedRunnable<Exception> runnable)
    {
        try
        {
            runnable.run();
            Mp3Utils.playProcessSuccess();
        }
        catch(Exception ex)
        {
            Mp3Utils.playProcessFailed();
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

        boolean isNativeHookEnable = Boolean.parseBoolean(System.getProperty("app.nativeHook.enable", "false"));
        System.out.println("Is NativeHook Enable: " + isNativeHookEnable);
        System.out.println("NativeHook will not work on macOS 25 and above if you not register the Jar file on 'System Settings > Privacy & Security > Accessibility'.");

        if(isNativeHookEnable)
        {
            try
            {
                System.out.println("Registering NativeHook...");
                GlobalScreen.registerNativeHook();
                GlobalScreen.addNativeKeyListener(this);
                System.out.println("Registering NativeHook has done.");

                Runtime.getRuntime().addShutdownHook
                (
                    new Thread(()->
                    {
                        try
                        {
                            System.out.println("Un-registering NativeHook...");
                            GlobalScreen.unregisterNativeHook();
                            System.out.println("Un-registering NativeHook has done.");
                        }
                        catch(Exception ex)
                        {
                            System.err.println("There was a problem un-registering the NativeHook.");
                            ex.printStackTrace(System.err);
                        }
                    })
                );
            }
            catch(Exception ex)
            {
                System.err.println("There was a problem registering the NativeHook.");
                ex.printStackTrace(System.err);
            }
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
