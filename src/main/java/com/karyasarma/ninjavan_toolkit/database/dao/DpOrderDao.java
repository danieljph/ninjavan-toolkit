package com.karyasarma.ninjavan_toolkit.database.dao;

import com.karyasarma.ninjavan_toolkit.database.ConnectionManager;
import com.karyasarma.ninjavan_toolkit.database.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel Joi Partogi Hutapea
 */
public class DpOrderDao
{
    public DpOrderDao()
    {
    }

    public List<Order> cleanDpOrders(String databaseName, String shipperPrefix)
    {
        List<Order> listOfOrder = new ArrayList<>();
        String sql = String.format("UPDATE %s.dp_orders SET deleted_at = NOW() WHERE deleted_at IS NULL AND tracking_id LIKE '%%%s%%'", databaseName, shipperPrefix);

        try(Connection conn = ConnectionManager.getConnectionManager())
        {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            int affectedRow = pstmt.executeUpdate();
            System.out.println("[INFO] Affected Row: "+affectedRow);
        }
        catch(SQLException ex)
        {
            throw new RuntimeException(ex);
        }

        return listOfOrder;
    }
}
