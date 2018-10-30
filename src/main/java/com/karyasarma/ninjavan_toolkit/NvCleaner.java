package com.karyasarma.ninjavan_toolkit;

import com.karyasarma.ninjavan_toolkit.client.OperatorClient;
import com.karyasarma.ninjavan_toolkit.database.dao.DpOrderDao;
import com.karyasarma.ninjavan_toolkit.database.dao.OrderDao;
import com.karyasarma.ninjavan_toolkit.database.dao.RouteDao;
import com.karyasarma.ninjavan_toolkit.database.model.Order;

import java.util.List;

/**
 * @author Daniel Joi Partogi Hutapea
 */
public class NvCleaner
{
    public static void main(String[] args)
    {
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

        int[] shipperIds = new int[]{16592};
        int[] driverIds = new int[]{1608, 1610};

        boolean enableCleanOrders = false;
        boolean enableCleanRoutes = false;
        boolean enableCleanDp = true;

        String countryCode = "SG";
        String coreDatabaseName;

        switch(countryCode.toUpperCase())
        {
            case "ID": coreDatabaseName = "core_qa_id"; break;
            case "SG": coreDatabaseName = "core_qa_sg"; break;
            default: coreDatabaseName = "core_qa_sg";
        }

        //while(true)
        {
            OperatorClient operatorClient = new OperatorClient(countryCode);
            operatorClient.login("AUTOMATION", "95h]BWjRYg27og4gt5n_4T8D5L1v2u");

            if(enableCleanOrders)
            {
                List<Order> listOfOrder = new OrderDao().getNotCompletedOrder(coreDatabaseName, shipperIds);
                //System.out.println(listOfOrder.stream().mapToInt(Order::getId).boxed().collect(Collectors.toList()));
                System.out.println("Number of Orders: " + listOfOrder.size());
                operatorClient.forceSuccessFast(listOfOrder);
            }

            if(enableCleanRoutes)
            {
                java.util.List<Integer> listOfRouteIds = new RouteDao().getUnarchivedRouteIds(coreDatabaseName, driverIds);
                //System.out.println("Number of Routes: "+listOfRouteIds.size());
                operatorClient.newArchiveRoute(listOfRouteIds);
            }
        }

        if(enableCleanDp)
        {
            new DpOrderDao().cleanDpOrders("dpms_qa_sg", "MCJ2O");
        }
    }
}
