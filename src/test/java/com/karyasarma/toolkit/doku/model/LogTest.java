package com.karyasarma.toolkit.doku.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Daniel Joi Partogi Hutapea
 */
class LogTest
{
    @Test @Disabled
    void parseTest() throws JsonProcessingException
    {
        String data = "{\"Time\":\"2023-09-12 21:10:09.000\",\"content\":\"{\\\"@timestamp\\\":\\\"2023-09-12T13:10:08.504Z\\\",\\\"severity\\\":\\\"INFO\\\",\\\"trace\\\":\\\"7880c1eb88f2b961\\\",\\\"thread\\\":\\\"http-nio-8080-exec-3\\\",\\\"class\\\":\\\"c.d.m.ocojokul.payment.common.RestClient\\\",\\\"rest\\\":\\\"http status code : 200, host : devex-check-status-api.jokul-devex.svc, path:/orders/v1/status/2000578104  \\\"}\"}";

        ObjectMapper om = new ObjectMapper();
        LogWrapper logWrapper = om.readValue(data, LogWrapper.class);
        String content = logWrapper.getContent();

        String result = Log.parse(content, true, true);
        System.out.println(result);
        assertNotNull(result);
    }
}
