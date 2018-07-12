package com.karyasarma.ninjavan_toolkit.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Daniel Joi Partogi Hutapea
 */
public class ConnectionManager
{
    private ConnectionManager()
    {
    }

    public static Connection getConnectionManager()
    {
        Connection conn = null;

        try
        {
            conn = DriverManager.getConnection("jdbc:mysql://10.80.0.13:6333", "ferdinand", "Qz4miH46$M28H08ZN~$10DB2b0g752");
        }
        catch(SQLException ex)
        {
        }

        return conn;
    }
}
