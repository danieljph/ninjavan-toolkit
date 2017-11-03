package com.karyasarma.ninjavan_toolkit.database.dao;

import com.karyasarma.ninjavan_toolkit.database.ConnectionManager;
import com.karyasarma.ninjavan_toolkit.database.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public List<Order> getNotCompletedOrder(String databaseName, int shipperId)
    {
        List<Order> listOfOrder = new ArrayList<>();
        String sql = String.format("SELECT id, tracking_id FROM %s.orders WHERE shipper_id = ? AND granular_status NOT IN ('Completed', 'Cancelled', 'Returned to Sender')", databaseName);

        try(Connection conn = ConnectionManager.getConnectionManager())
        {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, shipperId);
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
