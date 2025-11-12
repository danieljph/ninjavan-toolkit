package com.karyasarma.toolkit.doku.util;

import java.util.Base64;

/**
 * @author Daniel Joi Partogi Hutapea
 */
public class Base64Utils
{
    private Base64Utils()
    {
    }

    public static String encode(String data)
    {
        return Base64.getEncoder().encodeToString(data.getBytes());
    }

    public static String encodeUrlSafe(String data)
    {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(data.getBytes());
    }

    public static String decode(String base64String)
    {
        return new String(Base64.getDecoder().decode(base64String));
    }
}
