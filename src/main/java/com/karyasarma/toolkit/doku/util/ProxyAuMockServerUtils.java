package com.karyasarma.toolkit.doku.util;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Daniel Joi Partogi Hutapea
 */
public class ProxyAuMockServerUtils
{
    private ProxyAuMockServerUtils()
    {
    }

    public static String convertUrlToProxyAuMockServerOrViceVersa(String url)
    {
        String result = url;

        try
        {
            URI uri = URI.create(url);
            String uriScheme = uri.getScheme();
            String uriPort = uri.getPort() == -1? "" : ":" + uri.getPort();
            String uriHost = uri.getHost();
            String uriHostLowerCase = uriHost.toLowerCase();
            String uriPath = uri.getPath();
            String uriPathLowerCase = uriPath.toLowerCase();

            String basePathAuMockserver = "/au-mockserver";
            String basePathGtwApiMockServer = "/gtw-api-mock-server/proxy-au-mockserver";

            if(uriHostLowerCase.contains("doku.com"))
            {
                if(uriPathLowerCase.startsWith(basePathAuMockserver + "/"))
                {
                    String urlParams = buildUrlParams(uri, false);
                    result = uriScheme + "://" + uriHost + uriPort + basePathGtwApiMockServer + uriPath + urlParams;
                }
                else if(uriPathLowerCase.startsWith(basePathGtwApiMockServer + "/"))
                {
                    String urlParams = buildUrlParams(uri, true);
                    String pathAuMockserver = uriPath.replaceFirst(basePathGtwApiMockServer + "/", "/");
                    result = uriScheme + "://" + uriHost + uriPort + pathAuMockserver + urlParams;
                }
            }
            else if(uriHostLowerCase.contains("au-mockserver.techno"))
            {
                String hostAndPortGtwApiMockServerTemplate = "http://gtw-api-mock-server.jokul-gateway%s.svc:8080";

                String env = Optional.ofNullable(findEnvByHost(uriHost))
                    .map(it -> "-" + it)
                    .orElse("");

                String hostAndPortGtwApiMockServer = String.format(hostAndPortGtwApiMockServerTemplate, env);
                String pathGtwApiMockServer = basePathGtwApiMockServer + uriPath;
                String urlParams = buildUrlParams(uri, false);

                result = hostAndPortGtwApiMockServer + pathGtwApiMockServer + urlParams;
            }
            else if(uriHostLowerCase.contains("gtw-api-mock-server.jokul-gateway"))
            {
                String hostAndPortAuMockserverTemplate = "http://au-mockserver.techno%s.svc:1080";

                String env = Optional.ofNullable(findEnvByHost(uriHost))
                    .map(it -> "-" + it)
                    .orElse("");

                String hostAndPortAuMockserver = String.format(hostAndPortAuMockserverTemplate, env);
                String pathAuMockserver = uriPath.replaceFirst(basePathGtwApiMockServer + "/", "/");
                String urlParams = buildUrlParams(uri, true);
                result = hostAndPortAuMockserver + pathAuMockserver + urlParams;
            }
        }
        catch(Exception ex)
        {
            throw new RuntimeException("Failed on translateUrlToProxyAuMockServerOrViceVersa.", ex);
        }

        return result;
    }

    private static String findEnvByHost(String host)
    {
        String env = null;

        if(host.toLowerCase().contains("sit"))
        {
            env = "sit";
        }
        else if(host.toLowerCase().contains("uat"))
        {
            env = "uat";
        }
        else if(host.toLowerCase().contains("sandbox"))
        {
            env = "sandbox";
        }

        return env;
    }

    private static String buildUrlParams(URI uri, boolean isForAuMockserver)
    {
        try
        {
            String query = uri.getRawQuery();
            Map<String, String> queryPairs = new LinkedHashMap<>();

            if(StringUtils.isNotBlank(query))
            {
                String[] pairs = query.split("&");

                for(String pair : pairs)
                {
                    int idx = pair.indexOf("=");
                    String key = URLDecoder.decode(pair.substring(0, idx), "UTF-8");
                    String value = URLDecoder.decode(pair.substring(idx + 1), "UTF-8");
                    queryPairs.put(key, value);
                }
            }

            if(isForAuMockserver)
            {
                queryPairs.remove("validate-request-signature");
                queryPairs.remove("generate-response-signature");
                queryPairs.remove("secret-key");
            }
            else
            {
                queryPairs.putIfAbsent("validate-request-signature", "false");
                queryPairs.putIfAbsent("generate-response-signature", "true");
                queryPairs.putIfAbsent("secret-key", "SK-ChangeThis");
            }

            String queryResultTemp = queryPairs
                .entrySet()
                .stream()
                .map(entry ->
                {
                    try
                    {
                        return entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "UTF-8");
                    }
                    catch(UnsupportedEncodingException ex)
                    {
                        ex.printStackTrace(System.err);
                        return entry.getKey() + "=" + entry.getValue();
                    }
                })
                .collect(Collectors.joining("&"));

            return queryPairs.isEmpty()
                ? ""
                : "?" + queryResultTemp;
        }
        catch(Exception ex)
        {
            throw new RuntimeException("Failed on buildUrlParams.", ex);
        }
    }
}
