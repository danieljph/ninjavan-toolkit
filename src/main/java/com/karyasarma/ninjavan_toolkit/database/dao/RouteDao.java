package com.karyasarma.ninjavan_toolkit.database.dao;

import com.karyasarma.ninjavan_toolkit.database.ConnectionManager;

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
public class RouteDao
{
    public RouteDao()
    {
    }

    public List<Integer> getUnarchivedRouteIds(String databaseName, int driverId)
    {
        List<Integer> listOfRouteIds = new ArrayList<>();
        String sql = String.format("SELECT id FROM %s.route_logs WHERE driver_id = ? AND archived = 0 AND deleted_at IS NULL", databaseName);

        try(Connection conn = ConnectionManager.getConnectionManager())
        {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, driverId);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next())
            {
                listOfRouteIds.add(rs.getInt("id"));
            }
        }
        catch(SQLException ex)
        {
            throw new RuntimeException(ex);
        }

        return listOfRouteIds;
    }
}
