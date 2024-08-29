package com.karyasarma.toolkit.doku.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Daniel Joi Partogi Hutapea
 */
public class EncryptionUtils
{
    private EncryptionUtils()
    {
    }

    public static String encrypt(String plainPassword)
    {
        try
        {
            Map<String, String> queryParams = new HashMap<>();
            queryParams.put("plainPassword", plainPassword);
            return doSimpleHttpCall("https://api.karyasarma.com/encryptor/doku-aes/encrypt", queryParams);
        }
        catch(Exception ex)
        {
            throw new RuntimeException("Error occurs when encrypting plaintext: " + plainPassword, ex);
        }
    }

    public static String decrypt(String cipherPassword)
    {
        try
        {
            Map<String, String> queryParams = new HashMap<>();
            queryParams.put("cipherPassword", cipherPassword);
            return doSimpleHttpCall("https://api.karyasarma.com/encryptor/doku-aes/decrypt", queryParams);
        }
        catch(Exception ex)
        {
            throw new RuntimeException("Error occurs when decrypting cipher: " + cipherPassword, ex);
        }
    }

    @SuppressWarnings("ExtractMethodRecommender")
    public static String doSimpleHttpCall(String stringUrl, Map<String, String> queryParams) throws IOException
    {
        URL url = queryParams==null || queryParams.isEmpty()?
                new URL(stringUrl) :
                new URL(stringUrl+"?"+encodeQueryParams(queryParams));

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(1000);
        con.setReadTimeout(1000);

        con.setDoOutput(true);

        Reader streamReader;

        if(con.getResponseCode()>299)
        {
            streamReader = new InputStreamReader(con.getErrorStream());
        }
        else
        {
            streamReader = new InputStreamReader(con.getInputStream());
        }

        BufferedReader in = new BufferedReader(streamReader);
        String inputLine;
        StringBuilder responseBody = new StringBuilder();

        while((inputLine = in.readLine()) != null)
        {
            responseBody.append(inputLine);
        }

        in.close();

        return responseBody.toString();
    }

    private static String encodeQueryParams(Map<String, String> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();

        for(Map.Entry<String, String> entry : params.entrySet())
        {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }

        String resultString = result.toString();

        return !resultString.isEmpty()?
                resultString.substring(0, resultString.length() - 1) :
                resultString;
    }
}
