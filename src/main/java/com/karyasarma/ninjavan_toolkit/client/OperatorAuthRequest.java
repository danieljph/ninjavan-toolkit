package com.karyasarma.ninjavan_toolkit.client;

/**
 *
 * @author Daniel Joi Partogi Hutapea
 */
public class OperatorAuthRequest
{
    private String clientId;
    private String clientSecret;

    public OperatorAuthRequest()
    {
    }

    public String getClientId()
    {
        return clientId;
    }

    public void setClientId(String clientId)
    {
        this.clientId = clientId;
    }

    public String getClientSecret()
    {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret)
    {
        this.clientSecret = clientSecret;
    }
}
