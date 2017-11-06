package com.karyasarma.ninjavan_toolkit.database.dao;

import com.karyasarma.ninjavan_toolkit.database.ConnectionManager;
import com.karyasarma.ninjavan_toolkit.database.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Daniel Joi Partogi Hutapea
 */
public class OrderDao
{
    public OrderDao()
    {
    }

    public List<Order> getNotCompletedOrder(String databaseName, int[] shipperIds)
    {
        List<Order> listOfOrder = new ArrayList<>();
        String shipperIdsInClause = Arrays.toString(shipperIds).replace("[","(").replace("]", ")");
        String sql = String.format("SELECT id, tracking_id FROM %s.orders WHERE shipper_id IN %s AND granular_status NOT IN ('Completed', 'Cancelled', 'Returned to Sender')", databaseName, shipperIdsInClause);

        try(Connection conn = ConnectionManager.getConnectionManager())
        {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next())
            {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setTrackingId(rs.getString("tracking_id"));
                //order.setStatus(rs.getString("status"));
                //order.setGranularStatus(rs.getString("granular_status"));
                listOfOrder.add(order);
            }
        }
        catch(SQLException ex)
        {
            throw new RuntimeException(ex);
        }

        return listOfOrder;
    }
}
