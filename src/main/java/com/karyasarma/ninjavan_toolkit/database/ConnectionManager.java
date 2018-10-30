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
            conn = DriverManager.getConnection("jdbc:mysql://10.80.0.13:6333", "qa_automation", "Jz43S0xG852hcxmG3BAbrs7YWml6x5c4");
        }
        catch(SQLException ex)
        {
        }

        return conn;
    }
}
