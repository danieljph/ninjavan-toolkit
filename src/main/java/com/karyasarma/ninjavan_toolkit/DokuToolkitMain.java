package com.karyasarma.ninjavan_toolkit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karyasarma.ninjavan_toolkit.doku.model.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author Daniel Joi Partogi Hutapea
 */
@SuppressWarnings("WeakerAccess")
public class DokuToolkitMain implements ActionListener
{
    private static final SimpleDateFormat CURRENT_DATE_SDF = new SimpleDateFormat("EEE, MMMM dd, yyyy hh:mm:ss a 'UTC'X");

    private final java.util.List<SimpleMenu> listOfSimpleMenu = new ArrayList<>();
    private final SimpleMenu parseLogsSm = new SimpleMenu("Parse Logs");
    private final SimpleMenu parseLogsRemoveFailedLineSm = new SimpleMenu("Parse Logs Remove Failed Line");
    private final SimpleMenu prettyJsonSm = new SimpleMenu("Pretty JSON");
    private MenuItem quitMi;

    private ObjectMapper objectMapper = new ObjectMapper();

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

        listOfSimpleMenu.add(parseLogsSm);
        listOfSimpleMenu.add(parseLogsRemoveFailedLineSm);
        listOfSimpleMenu.add(prettyJsonSm);

        for(SimpleMenu simpleMenu : listOfSimpleMenu)
        {
            MenuItem menuItem = new MenuItem(simpleMenu.getName());
            menuItem.setActionCommand(simpleMenu.getName());
            menuItem.addActionListener(this);
            popupMenu.add(menuItem);
        }

        popupMenu.addSeparator();

        quitMi = new MenuItem("Quit");
        quitMi.addActionListener(this);
        popupMenu.add(quitMi);

        Image image = Toolkit.getDefaultToolkit().getImage(DokuToolkitMain.class.getResource("/images/ninjavan.ico"));

        TrayIcon trayIcon = new TrayIcon(image, "Ninja Van Toolkit");
        trayIcon.setPopupMenu(popupMenu);

        SystemTray systemTray = SystemTray.getSystemTray();
        systemTray.add(trayIcon);
    }

    @Override
    public void actionPerformed(ActionEvent evt)
    {
        String actionCommand = evt.getActionCommand();

        if(parseLogsSm.getName().equals(actionCommand))
        {
            try
            {
                String logsData = (String) getSystemClipboard().getData(DataFlavor.stringFlavor);
                System.out.println("Data: \n"+logsData);
                copyToClipboard(Log.parse(logsData, false));
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
                copyToClipboard(Log.parse(logsData, true));
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
