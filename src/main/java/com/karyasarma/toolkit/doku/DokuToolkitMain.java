package com.karyasarma.toolkit.doku;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karyasarma.toolkit.doku.model.Log;
import com.karyasarma.toolkit.doku.ui.SimpleMenu;
import com.karyasarma.toolkit.doku.util.EncryptionUtils;
import com.karyasarma.toolkit.util.XmlUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 *
 * @author Daniel Joi Partogi Hutapea
 */
@SuppressWarnings("WeakerAccess")
public class DokuToolkitMain implements ActionListener
{
    private final java.util.List<SimpleMenu> listOfSimpleMenu = new ArrayList<>();
    private final SimpleMenu separatorSm = new SimpleMenu("Separator");

    private final SimpleMenu parseLogsSimplifiedSm = new SimpleMenu("Parse Logs (Simplified)");
    private final SimpleMenu parseLogsSm = new SimpleMenu("Parse Logs");
    private final SimpleMenu parseLogsRemoveFailedLineSm = new SimpleMenu("Parse Logs (Remove Un-parsed Line)");

    private final SimpleMenu prettyJsonSm = new SimpleMenu("Pretty JSON");
    private final SimpleMenu compactJsonSm = new SimpleMenu("Compact JSON");

    private final SimpleMenu prettyXmlSm = new SimpleMenu("Pretty XML");
    private final SimpleMenu compactXmlSm = new SimpleMenu("Compact XML");

    private final SimpleMenu aesEncryptSm = new SimpleMenu("AES Encrypt (UAT)");
    private final SimpleMenu aesDecryptSm = new SimpleMenu("AES Decrypt (UAT)");

    private final SimpleMenu toOldCurlSm = new SimpleMenu("To Old cURL");

    private final SimpleMenu passwordVpnSm = new SimpleMenu("Password VPN");
    private final SimpleMenu passwordLdapSm = new SimpleMenu("Password LDAP");
    private MenuItem quitMi;

    private final ObjectMapper objectMapper = new ObjectMapper();

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

        listOfSimpleMenu.add(parseLogsSimplifiedSm);
        listOfSimpleMenu.add(parseLogsSm);
        listOfSimpleMenu.add(parseLogsRemoveFailedLineSm);

        listOfSimpleMenu.add(separatorSm);

        listOfSimpleMenu.add(prettyJsonSm);
        listOfSimpleMenu.add(compactJsonSm);

        listOfSimpleMenu.add(separatorSm);

        listOfSimpleMenu.add(prettyXmlSm);
        listOfSimpleMenu.add(compactXmlSm);

        listOfSimpleMenu.add(separatorSm);

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
                MenuItem menuItem = new MenuItem(simpleMenu.getName());
                menuItem.setActionCommand(simpleMenu.getName());
                menuItem.addActionListener(this);
                popupMenu.add(menuItem);
            }
        }

        popupMenu.addSeparator();

        quitMi = new MenuItem("Quit");
        quitMi.addActionListener(this);
        popupMenu.add(quitMi);

        Image image = Toolkit.getDefaultToolkit().getImage(DokuToolkitMain.class.getResource("/images/ninjavan.ico"));

        TrayIcon trayIcon = new TrayIcon(image, "Doku Toolkit");
        trayIcon.setPopupMenu(popupMenu);

        SystemTray systemTray = SystemTray.getSystemTray();
        systemTray.add(trayIcon);
    }

    @Override
    public void actionPerformed(ActionEvent evt)
    {
        String actionCommand = evt.getActionCommand();

        if(parseLogsSimplifiedSm.getName().equals(actionCommand))
        {
            try
            {
                String logsData = (String) getSystemClipboard().getData(DataFlavor.stringFlavor);
                System.out.println("Data: \n"+logsData);
                copyToClipboard(Log.parse(logsData, false, true));
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
            return;
        }
        else if(parseLogsSm.getName().equals(actionCommand))
        {
            try
            {
                String logsData = (String) getSystemClipboard().getData(DataFlavor.stringFlavor);
                System.out.println("Data: \n"+logsData);
                copyToClipboard(Log.parse(logsData, false, false));
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
            return;
        }
        else if(parseLogsRemoveFailedLineSm.getName().equals(actionCommand))
        {
            try
            {
                String logsData = (String) getSystemClipboard().getData(DataFlavor.stringFlavor);
                System.out.println("Data: \n"+logsData);
                copyToClipboard(Log.parse(logsData, true, false));
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
            return;
        }
        else if(prettyJsonSm.getName().equals(actionCommand))
        {
            try
            {
                String jsonData = (String) getSystemClipboard().getData(DataFlavor.stringFlavor);
                System.out.println("JSON Data: \n"+jsonData);
                Object temp = objectMapper.readValue(jsonData, Object.class);
                copyToClipboard(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(temp));
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
            return;
        }
        else if(compactJsonSm.getName().equals(actionCommand))
        {
            try
            {
                String jsonData = (String) getSystemClipboard().getData(DataFlavor.stringFlavor);
                System.out.println("JSON Data: \n"+jsonData);
                Object temp = objectMapper.readValue(jsonData, Object.class);
                copyToClipboard(objectMapper.writeValueAsString(temp));
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
            return;
        }
        else if(prettyXmlSm.getName().equals(actionCommand))
        {
            try
            {
                String xmlData = (String) getSystemClipboard().getData(DataFlavor.stringFlavor);
                System.out.println("XML Data: \n"+xmlData);
                copyToClipboard(XmlUtils.prettyPrint(xmlData, 4, false));
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
            return;
        }
        else if(compactXmlSm.getName().equals(actionCommand))
        {
            try
            {
                String xmlData = (String) getSystemClipboard().getData(DataFlavor.stringFlavor);
                System.out.println("XML Data: \n"+xmlData);
                copyToClipboard(XmlUtils.compactPrint(xmlData));
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
            return;
        }
        else if(toOldCurlSm.getName().equals(actionCommand))
        {
            try
            {
                String curlData = (String) getSystemClipboard().getData(DataFlavor.stringFlavor);
                System.out.println("cURL Data: \n"+curlData);
                String curlDataOld = curlData.replaceAll("--data-raw", "--data");
                copyToClipboard(curlDataOld);
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
            return;
        }
        else if(aesEncryptSm.getName().equals(actionCommand))
        {
            try
            {
                String plainPassword = (String) getSystemClipboard().getData(DataFlavor.stringFlavor);
                System.out.println("plainPassword Data: "+plainPassword);

                if(StringUtils.isNotBlank(plainPassword))
                {
                    copyToClipboard(EncryptionUtils.encrypt(plainPassword));
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
            return;
        }
        else if(aesDecryptSm.getName().equals(actionCommand))
        {
            try
            {
                String cipherPassword = (String) getSystemClipboard().getData(DataFlavor.stringFlavor);
                System.out.println("cipherPassword Data: "+cipherPassword);

                if(StringUtils.isNotBlank(cipherPassword))
                {
                    copyToClipboard(EncryptionUtils.decrypt(cipherPassword));
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
            return;
        }
        else if(passwordVpnSm.getName().equals(actionCommand))
        {
            String passwordVpn = System.getenv("VPN_PASSWORD");

            if(StringUtils.isBlank(passwordVpn))
            {
                JOptionPane.showMessageDialog(null, "Environment variable with name 'PASSWORD_VPN' does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                copyToClipboard(passwordVpn);
            }
        }
        else if(passwordLdapSm.getName().equals(actionCommand))
        {
            String passwordVpn = System.getenv("LDAP_PASSWORD");

            if(StringUtils.isBlank(passwordVpn))
            {
                JOptionPane.showMessageDialog(null, "Environment variable with name 'PASSWORD_LDAP' does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                copyToClipboard(passwordVpn);
            }
        }

        Object source = evt.getSource();

        if(source==quitMi)
        {
            System.exit(0);
        }
    }

    private void copyToClipboard(String data)
    {
        Clipboard clipboard = getSystemClipboard();
        clipboard.setContents(new StringSelection(data), null);
    }

    public Clipboard getSystemClipboard()
    {
        return Toolkit.getDefaultToolkit().getSystemClipboard();
    }

    public void start()
    {
        try
        {
            showSystemTray();
        }
        catch(AWTException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public static void main(String[] args)
    {
        System.setProperty("apple.awt.UIElement", "true");
        new DokuToolkitMain().start();
    }
}
