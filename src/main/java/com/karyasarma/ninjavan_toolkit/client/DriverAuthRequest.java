package com.karyasarma.ninjavan_toolkit.client;

/**
 *
 * @author Daniel Joi Partogi Hutapea
 */
public class DriverAuthRequest
{
    private String username;
    private String password;

    public DriverAuthRequest()
    {
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
