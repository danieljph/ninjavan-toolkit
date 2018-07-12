package com.karyasarma.ninjavan_toolkit;

import com.karyasarma.ninjavan_toolkit.client.OperatorClient;
import com.karyasarma.ninjavan_toolkit.database.dao.DpOrderDao;
import com.karyasarma.ninjavan_toolkit.database.dao.OrderDao;
import com.karyasarma.ninjavan_toolkit.database.dao.RouteDao;
import com.karyasarma.ninjavan_toolkit.database.model.Order;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author Daniel Joi Partogi Hutapea
 */
public class NinjavanToolkitMain implements ActionListener
{
    private static final SimpleDateFormat CURRENT_DATE_SDF = new SimpleDateFormat("EEE, MMMM dd, yyyy hh:mm:ss a 'UTC'X");
    private static final SimpleDateFormat TRACKING_ID_SDF = new SimpleDateFormat("MMddyy");
    private static final SimpleDateFormat DATE_AND_TIME_SDF = new SimpleDateFormat("ddMMyyyyhhmmss");
    private static final SimpleDateFormat ROUTE_GROUP_NAME_SDF = new SimpleDateFormat("'DJPH-RG' EEE, MMMM dd, yyyy hh:mm:ss");
    private final java.util.List<SimpleMenu> listOfSimpleMenu = new ArrayList<>();
    private MenuItem getDateMi;
    private MenuItem getDateAndTimeMi;
    private MenuItem getRouteGroupNameMi;
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

        if(source==getDateMi)
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
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(new StringSelection(data), null);
    }

    public static void main(String[] args) throws AWTException
    {
        //System.setProperty("apple.awt.UIElement", "true");
        //new NinjavanToolkitMain().showSystemTray();

        /**
         * Shipper:
         *
         * qa-oc-postpaid-v3-webhook = 3339 (Felix) -> NVSGOCKW3000009785
         * QA Account = 3275 -> QANV7508900196C
         * Shipper OpV2OcV2 = 18546 -> SOCV2516874521C
         * Driver App Shipper #1 = 15261 -> DAS01508903243
         * Driver App Shipper Auto RSVN = 15747
         * Shipper Jeri = 17474
         * Shipper Binti = 17455, 16431
         * Shipper Lite Shipper = 3314
         * Ian Shipper = 3308
         * Dini Seprilia = 16981
         *
         * Shipper Load Test: 17093, 17095, 17097, 17099, 17101, 17103, 17105, 17107, 17109, 17111, 17113, 17115, 17117, 17119, 17121, 17123, 17125, 17127, 17129, 17131
         */

        /**
         * Driver:
         *
         * automation1 = 1650
         * automation2 = 1652
         * opv1no1 = 1608
         * bintinrc1 = 2389
         * bintinr2 = 2679
         * jerisg01 = 2441
         *
         * Driver Load Test: 2451, 2453, 2455, 2457, 2459, 2461, 2463, 2465, 2467, 2469, 2471, 2473, 2475, 2477, 2479, 2481, 2483, 2485, 2487, 2489
         */

        while(true)
        {

            int[] shipperIds = new int[]{1651};
            int[] driverIds = new int[]{1608, 2451, 2453, 2455, 2457, 2459, 2461, 2463, 2465, 2467, 2469, 2471, 2473, 2475, 2477, 2479, 2481, 2483, 2485, 2487, 2489};

            OperatorClient operatorClient = new OperatorClient();
            operatorClient.login("AUTOMATION", "95h]BWjRYg27og4gt5n_4T8D5L1v2u");

            java.util.List<Order> listOfOrder = new OrderDao().getNotCompletedOrder("core_qa_id", shipperIds);

            //System.out.println(listOfOrder.stream().mapToInt(Order::getId).boxed().collect(Collectors.toList()));
            System.out.println("Number of Orders: " + listOfOrder.size());
            operatorClient.forceSuccessFast(listOfOrder);
        }
    }
}
