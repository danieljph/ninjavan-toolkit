package com.karyasarma.ninjavan_toolkit;

import com.karyasarma.ninjavan_toolkit.util.CurlData;

import javax.swing.JOptionPane;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Daniel Joi Partogi Hutapea
 */
@SuppressWarnings("WeakerAccess")
public class NinjavanToolkitMain implements ActionListener
{
    private static final SimpleDateFormat CURRENT_DATE_SDF = new SimpleDateFormat("EEE, MMMM dd, yyyy hh:mm:ss a 'UTC'X");
    private static final SimpleDateFormat TRACKING_ID_SDF = new SimpleDateFormat("MMddyy");
    private static final SimpleDateFormat DATE_AND_TIME_SDF = new SimpleDateFormat("ddMMyyyyhhmmss");
    private static final SimpleDateFormat ROUTE_GROUP_NAME_SDF = new SimpleDateFormat("'DJPH-RG' EEE, MMMM dd, yyyy hh:mm:ss");

    private static final String START_CURL_PARSER = "Start cURL Parser";
    private static final String STOP_CURL_PARSER = "Stop cURL Parser";

    private final java.util.List<SimpleMenu> listOfSimpleMenu = new ArrayList<>();
    private MenuItem curlParser;
    private MenuItem getDateMi;
    private MenuItem getDateAndTimeMi;
    private MenuItem getRouteGroupNameMi;
    private MenuItem quitMi;

    private ClipboardReader clipboardReader;

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

        curlParser = new MenuItem(START_CURL_PARSER);
        curlParser.addActionListener(this);
        popupMenu.add(curlParser);
        popupMenu.addSeparator();

        getDateMi = new MenuItem("Get Date");
        getDateMi.addActionListener(this);
        popupMenu.add(getDateMi);

        getDateAndTimeMi = new MenuItem("Get Date & Time");
        getDateAndTimeMi.addActionListener(this);
        popupMenu.add(getDateAndTimeMi);

        getRouteGroupNameMi = new MenuItem("Get RG Name");
        getRouteGroupNameMi.addActionListener(this);
        popupMenu.add(getRouteGroupNameMi);

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

        if(source==curlParser)
        {
            if(clipboardReader!=null && clipboardReader.isAlive())
            {
                clipboardReader.stopReader();
                clipboardReader.interrupt();
            }

            switch(curlParser.getLabel())
            {
                case START_CURL_PARSER:
                {
                    curlParser.setLabel(STOP_CURL_PARSER);
                    clipboardReader = new ClipboardReader();
                    clipboardReader.start();
                    break;
                }
                case STOP_CURL_PARSER:
                {
                    curlParser.setLabel(START_CURL_PARSER);
                    break;
                }
            }
        }
        else if(source==getDateMi)
        {
            String trackingIdPrefix = TRACKING_ID_SDF.format(new Date());
            copyToClipboard(trackingIdPrefix);
        }
        else if(source==getDateAndTimeMi)
        {
            String trackingIdPrefix = DATE_AND_TIME_SDF.format(new Date());
            copyToClipboard(trackingIdPrefix);
        }
        else if(source==getRouteGroupNameMi)
        {
            String trackingIdPrefix = ROUTE_GROUP_NAME_SDF.format(new Date());
            copyToClipboard(trackingIdPrefix);
        }
        else if(source==quitMi)
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

    public class ClipboardReader extends Thread
    {
        private boolean stop;

        public ClipboardReader()
        {
            setDaemon(true);
        }

        @Override
        public void run()
        {
            while(!stop)
            {
                try
                {

                    String restAssuredLog = (String) getSystemClipboard().getData(DataFlavor.stringFlavor);

                    if(restAssuredLog.contains("==================== REQUEST ====================") && restAssuredLog.contains("================================================="))
                    {
                        CurlData curlData = CurlData.parse(restAssuredLog);
                        getSystemClipboard().setContents(new StringSelection(curlData.toCurl()), null);
                    }

                    Thread.sleep(2000);
                }
                catch(UnsupportedFlavorException | IOException | InterruptedException ex)
                {
                    System.out.println("Error: "+ex.getMessage());
                }
            }
        }

        public void stopReader()
        {
            this.stop = true;
        }
    }

    public static void main(String[] args)
    {
        System.setProperty("apple.awt.UIElement", "true");
        new NinjavanToolkitMain().start();
    }
}
