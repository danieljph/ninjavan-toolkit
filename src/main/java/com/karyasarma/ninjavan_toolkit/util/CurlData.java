package com.karyasarma.ninjavan_toolkit.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author Daniel Joi Partogi Hutapea
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class CurlData
{
    private String requestMethod;
    private String requestUri;
    private List<CurlHeader> listOfHeaders;
    private String rawBody;

    public CurlData()
    {
    }

    public String getRequestMethod()
    {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod)
    {
        this.requestMethod = requestMethod;
    }

    public String getRequestUri()
    {
        return requestUri;
    }

    public void setRequestUri(String requestUri)
    {
        this.requestUri = requestUri;
    }

    public List<CurlHeader> getListOfHeaders()
    {
        return listOfHeaders;
    }

    public void setListOfHeaders(List<CurlHeader> listOfHeaders)
    {
        this.listOfHeaders = listOfHeaders;
    }

    public String getRawBody()
    {
        return rawBody;
    }

    public void setRawBody(String rawBody)
    {
        this.rawBody = rawBody;
    }

    public static CurlData parse(String restAssuredLog)
    {
        String[] lines = restAssuredLog.split("\n");
        String currentKeyword = "";

        Map<String, String> mapOfKeywords = new HashMap<>();
        mapOfKeywords.put("Request Name:", "requestName");
        mapOfKeywords.put("Request method:", "requestMethod");
        mapOfKeywords.put("Request URI:", "requestUri");
        mapOfKeywords.put("Proxy:", "proxy");
        mapOfKeywords.put("Request params:", "requestParams");
        mapOfKeywords.put("Query params:", "queryParams");
        mapOfKeywords.put("Form params:", "formParams");
        mapOfKeywords.put("Path params:", "pathParams");
        mapOfKeywords.put("Headers:", "headers");
        mapOfKeywords.put("Cookies:", "cookies");
        mapOfKeywords.put("Multiparts:", "multiparts");
        mapOfKeywords.put("Body:", "body");

        boolean startLineFound = false;
        int indexOfUnusedInfo = 21;

        Map<String, StringBuilder> mapOfKeywordData = new LinkedHashMap<>();

        for(String line : lines)
        {
            if(line.contains("==================== REQUEST ===================="))
            {
                startLineFound = true;
                continue;
            }

            if(startLineFound && line.contains("================================================="))
            {
                break;
            }

            if(!startLineFound)
            {
                continue;
            }

            if(line.contains("Request Name:"))
            {
                indexOfUnusedInfo = line.indexOf("Request Name:");
            }

            line = line.substring(indexOfUnusedInfo);
            String tempLine = line;
            Optional<Map.Entry<String,String>> keywordOpt = mapOfKeywords.entrySet().parallelStream().filter(entry -> tempLine.contains(entry.getKey())).findFirst();

            if(keywordOpt.isPresent())
            {
                Map.Entry<String,String> entry = keywordOpt.get();
                currentKeyword = entry.getValue();
                String splitter = entry.getKey();

                if(!currentKeyword.equals("body"))
                {
                    String value = line.split(splitter)[1].trim();
                    mapOfKeywordData.computeIfAbsent(currentKeyword, k -> new StringBuilder()).append(value).append('\n');
                }
            }
            else
            {
                if(!currentKeyword.equals("body"))
                {
                    String[] splitValues = line.split(" {5}");
                    String value = splitValues[splitValues.length-1].trim();
                    mapOfKeywordData.computeIfAbsent(currentKeyword, k -> new StringBuilder()).append(value).append('\n');
                }
                else
                {
                    mapOfKeywordData.computeIfAbsent(currentKeyword, k -> new StringBuilder()).append(line).append('\n');
                }
            }
        }

        for(Map.Entry<String,StringBuilder> entry : mapOfKeywordData.entrySet())
        {
            if(entry.getValue()!=null)
            {
                StringBuilder value = entry.getValue();
                value.deleteCharAt(value.length()-1);
            }
        }

        CurlData curlData = new CurlData();
        curlData.setRequestMethod(mapOfKeywordData.getOrDefault("requestMethod", new StringBuilder("NOT_FOUND")).toString());
        curlData.setRequestUri(mapOfKeywordData.getOrDefault("requestUri", new StringBuilder("NOT_FOUND")).toString());

        StringBuilder headers = mapOfKeywordData.get("headers");

        if(headers!=null)
        {
            List<CurlHeader> listOfCurlHeader = new ArrayList<>();
            curlData.setListOfHeaders(listOfCurlHeader);
            String[] arrayOfHeader = headers.toString().split("\n");

            for(String header : arrayOfHeader)
            {
                String[] temp = header.split("=");
                CurlHeader curlHeader = new CurlHeader();
                curlHeader.setKey(temp[0]);
                curlHeader.setValue(temp[1]);
                listOfCurlHeader.add(curlHeader);
            }
        }

        StringBuilder body = mapOfKeywordData.get("body");

        if(body!=null)
        {
            curlData.setRawBody(body.toString());
        }

        return curlData;
    }

    public String toCurl()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("curl -X ").append(requestMethod).append(" \\\n");
        sb.append("  ").append(requestUri).append(" \\\n");

        if(listOfHeaders!=null)
        {
            for(CurlHeader curlHeader : listOfHeaders)
            {
                sb.append("  -H '").append(curlHeader.getKey()).append(": ").append(curlHeader.getValue()).append("' \\\n");
            }
        }

        if(rawBody!=null)
        {
            sb.append("  -d '").append(rawBody.replaceAll("'", "'\\\\''")).append('\'');
        }

        return sb.toString();
    }
}
