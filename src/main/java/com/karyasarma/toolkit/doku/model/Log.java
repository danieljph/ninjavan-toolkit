package com.karyasarma.toolkit.doku.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * Sample Log:
 * {"@timestamp":"2022-11-22T07:35:53.716+00:00","severity":"INFO","service":"gtw-config-api","trace":"2da9887db258b1dd","span":"dfa905e8399ebb54","parent":"2da9887db258b1dd","exportable":"false","pid":"6","thread":"http-nio-8080-exec-5","class":"c.d.g.c.api.interceptor.JwtInterceptor","rest":"validate jwt: GET=/v2/merchant-channel/channel/VIRTUAL_ACCOUNT_BANK_PERMATA/MCH-8927-1636448248076 "}
 * {"@timestamp":"2022-11-22T07:35:53.716+00:00","severity":"INFO","service":"gtw-config-api","trace":"2da9887db258b1dd","span":"dfa905e8399ebb54","parent":"2da9887db258b1dd","exportable":"false","pid":"6","thread":"http-nio-8080-exec-5","class":"c.d.g.c.api.interceptor.JwtInterceptor","rest":"isValidatePermission data: GET=/v2/merchant-channel/channel/VIRTUAL_ACCOUNT_BANK_PERMATA/MCH-8927-1636448248076:JwtUserDTO(businessServiceClientId=MCH-8927-1636448248076, businessClientId=BSN-8925-1636448247511, userFullName=Aldy surya wahyudi, userEmail=aldy@doku.com, userType=Internal, roleName=IT, permission=[CRUD]) "}
 * {"@timestamp":"2022-11-22T07:35:53.807+00:00","severity":"INFO","service":"gtw-config-api","trace":"ae4ca3b0f23d868a","span":"c6257d58c31eb40b","parent":"ae4ca3b0f23d868a","exportable":"false","pid":"6","thread":"http-nio-8080-exec-9","class":"c.d.g.c.api.interceptor.JwtInterceptor","rest":"validate jwt: GET=/v2/merchant-channel/channel/VIRTUAL_ACCOUNT_BANK_DANAMON/MCH-8927-1636448248076 "}
 * <br/>
 * Sample Beautify Log:
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
@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Log
{
    @JsonProperty("@timestamp") private String timestamp;
    private String severity;
    private String service;
    private String trace;
    private String span;
    private String parent;
    private String thread;
    @JsonProperty("class") private String clazz;
    private String rest;
    private String message;

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

    public String getTrace()
    {
        return trace;
    }

    public void setTrace(String trace)
    {
        this.trace = trace;
    }

    public String getSpan()
    {
        return span;
    }

    public void setSpan(String span)
    {
        this.span = span;
    }

    public String getParent()
    {
        return parent;
    }

    public void setParent(String parent)
    {
        this.parent = parent;
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

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public static String parse(String logsData, boolean removeFailedLine, boolean simplified)
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
                    sb.append(log.toString(simplified)).append("\n");
                }
                catch(Exception ignore)
                {
                    if(!removeFailedLine)
                    {
                        sb.append(logStr).append("\n");
                    }
                }
            }
        }
        catch(Exception ex)
        {
            sb = new StringBuilder();
            sb.append("Failed parse. Cause: ").append(ex.getMessage()).append("\n");
            sb.append(logsData);
        }

        return sb.toString();
    }

    private String getInfo()
    {
        return Optional.ofNullable(getRest()).orElse(getMessage());
    }

    public String toString(boolean simplified)
    {
        if(simplified)
        {
            return String.format("%s %s %s - %s",
                    getTimestamp(),
                    StringUtils.rightPad(getSeverity(), 5),
                    Optional.ofNullable(getClazz()).orElse("class-null"),
                    getInfo());
        }
        else
        {
            return toString();
        }
    }

    @Override
    public String toString()
    {
        return String.format("%s %s [%s]-[t:%s, s:%s, p:%s]-[%s] %s - %s",
                getTimestamp(),
                StringUtils.rightPad(getSeverity(), 5),
                Optional.ofNullable(getService()).orElse("service-null"),
                Optional.ofNullable(getTrace()).orElse(""),
                Optional.ofNullable(getSpan()).orElse(""),
                Optional.ofNullable(getParent()).orElse(""),
                Optional.ofNullable(getThread()).orElse("thread-null"),
                Optional.ofNullable(getClazz()).orElse("class-null"),
                getInfo());
    }
}
