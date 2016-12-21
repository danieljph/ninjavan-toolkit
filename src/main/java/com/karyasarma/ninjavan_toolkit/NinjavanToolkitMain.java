package com.karyasarma.ninjavan_toolkit;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Daniel Joi Partogi Hutapea
 */
public class NinjavanToolkitMain implements ActionListener
{
    private static final SimpleDateFormat CURRENT_DATE_SDF = new SimpleDateFormat("EEE, MMMM dd, yyyy hh:mm:ss a 'UTC'X");
    private static final SimpleDateFormat TRACKING_ID_SDF = new SimpleDateFormat("MMddyy");
    private final java.util.List<SimpleMenu> listOfSimpleMenu = new ArrayList<>();
    private MenuItem getDateMi;
    private MenuItem quitMi;

    public NinjavanToolkitMain()
    {
    }

    public void showSystemTray() throws AWTException
    {
        if(!SystemTray.isSupported())
        {
            JOptionPane.showMessageDialog(null, "SystemTray is not supported.", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        PopupMenu popupMenu = new PopupMenu();

        getDateMi = new MenuItem("Get Date");
        getDateMi.addActionListener(this);
        popupMenu.add(getDateMi);

        listOfSimpleMenu.add(new SimpleMenu("Pick Up"));
        listOfSimpleMenu.add(new SimpleMenu("Order"));
        listOfSimpleMenu.add(new SimpleMenu("Route"));
        listOfSimpleMenu.add(new SimpleMenu("Reschedule"));
        listOfSimpleMenu.add(new SimpleMenu("Normal Order"));
        listOfSimpleMenu.add(new SimpleMenu("C2C Order"));
        listOfSimpleMenu.add(new SimpleMenu("Return Order"));

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

        Image image = Toolkit.getDefaultToolkit().getImage(NinjavanToolkitMain.class.getResource("/images/ninjavan.ico"));

        TrayIcon trayIcon = new TrayIcon(image, "Ninja Van Toolkit");
        trayIcon.setPopupMenu(popupMenu);

        SystemTray systemTray = SystemTray.getSystemTray();
        systemTray.add(trayIcon);
    }

    @Override
    public void actionPerformed(ActionEvent evt)
    {
        String actionCommand = evt.getActionCommand().toLowerCase();

        for(SimpleMenu simpleMenu : listOfSimpleMenu)
        {
            String simpleMenuName = simpleMenu.getName().toLowerCase();

            if(actionCommand.equals(simpleMenuName))
            {
                String createdAt = "Created at "+CURRENT_DATE_SDF.format(new Date())+'.';
                copyToClipboard(String.format("This \"%s\" is created for testing purpose only. Ignore this \"%s\". %s", simpleMenuName, simpleMenuName, createdAt));
                return;
            }
        }

        Object source = evt.getSource();

        if(source==getDateMi)
        {
            String trackingIdPrefix = TRACKING_ID_SDF.format(new Date());
            copyToClipboard(trackingIdPrefix);
        }
        else if(source==quitMi)
        {
            System.exit(0);
        }
    }

    private void copyToClipboard(String data)
    {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(new StringSelection(data), null);
    }

    public static void main(String[] args) throws AWTException
    {
        System.setProperty("apple.awt.UIElement", "true");
        new NinjavanToolkitMain().showSystemTray();
    }
}
