package com.karyasarma.ninjavan_toolkit.doku.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

/**
 * Sample Log:
 * {
 *   "@timestamp":"2022-11-22T07:35:53.716+00:00",
 *   "severity":"INFO",
 *   "service":"gtw-config-api",
 *   "trace":"2da9887db258b1dd",
 *   "span":"dfa905e8399ebb54",
 *   "parent":"2da9887db258b1dd",
 *   "exportable":"false",
 *   "pid":"6",
 *   "thread":"http-nio-8080-exec-5",
 *   "class":"c.d.g.c.api.interceptor.JwtInterceptor",
 *   "rest":"validate jwt: GET=/v2/merchant-channel/channel/VIRTUAL_ACCOUNT_BANK_PERMATA/MCH-8927-1636448248076 "
 * }
 *
 * @author Daniel Joi Partogi Hutapea
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Log
{
    @JsonProperty("@timestamp") private String timestamp;
    private String severity;
    private String service;
    private String thread;
    @JsonProperty("class") private String clazz;
    private String rest;

    public Log()
    {
    }

    public String getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(String timestamp)
    {
        this.timestamp = timestamp;
    }

    public String getSeverity()
    {
        return severity;
    }

    public void setSeverity(String severity)
    {
        this.severity = severity;
    }

    public String getService()
    {
        return service;
    }

    public void setService(String service)
    {
        this.service = service;
    }

    public String getThread()
    {
        return thread;
    }

    public void setThread(String thread)
    {
        this.thread = thread;
    }

    public String getClazz()
    {
        return clazz;
    }

    public void setClazz(String clazz)
    {
        this.clazz = clazz;
    }

    public String getRest()
    {
        return rest;
    }

    public void setRest(String rest)
    {
        this.rest = rest;
    }

    public static String parse(String logsData)
    {
        StringBuilder sb = new StringBuilder();
        ObjectMapper om = new ObjectMapper();

        try
        {
            String[] logsStr = logsData.split("\n");

            for(String logStr : logsStr)
            {
                try
                {
                    Log log = om.readValue(logStr, Log.class);
                    sb.append(log.toString()).append("\n");
                }
                catch(Exception ignore)
                {
                }
            }
        }
        catch(Exception ignore)
        {
        }

        return sb.toString();
    }

    @Override
    public String toString()
    {
        //2022-11-24 19:28:26.771 DEBUG [gtw-va-mandiri,,,] 7 --- [nio-8080-exec-7] o.apache.catalina.valves.RemoteIpValve   : Incoming request /mandiri-virtual-account/actuator/heal
        return String.format("%s %s [%s]-[%s] %s - %s", getTimestamp(), StringUtils.rightPad(getSeverity(), 5), getService(), getThread(), getClazz(), getRest());
    }
}
