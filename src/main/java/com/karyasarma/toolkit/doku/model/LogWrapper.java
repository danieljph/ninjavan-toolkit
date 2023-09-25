package com.karyasarma.toolkit.doku.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Sample LogWrapper:
 * {"content":"{\"@timestamp\":\"2023-09-12T11:33:05.354+00:00\",\"severity\":\"WARN\",\"service\":\"gtw-core-mib\",\"trace\":\"8b494f69c492ac75\",\"span\":\"766d08aa46c52f3a\",\"pid\":\"6\",\"thread\":\"http-nio-8080-exec-2\",\"class\":\"c.d.g.gtwcoremib.service.NotifyService\",\"rest\":\"FAILED NOTIFY MIB with invoicenumber:2000578104\"}"}
 *
 * @author Daniel Joi Partogi Hutapea
 */
@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
public class LogWrapper
{
    private String content;

    public LogWrapper()
    {
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }
}
