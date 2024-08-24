package com.karyasarma.toolkit.doku.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

/**
 * Sample Log:
 * [
 *   {
 *     "container_name": "gtw-core-mib",
 *     "pod_name": "gtw-core-mib-67c58f7cff-xmgvf",
 *     "timestamp": "2023-11-30T09:17:29.076+00:00",
 *     "content": {
 *       "@timestamp": "2023-11-30T09:17:29.076+00:00",
 *       "severity": "INFO",
 *       "service": "gtw-core-mib",
 *       "trace": "af711f8c742d86a4",
 *       "span": "af711f8c742d86a4",
 *       "pid": "6",
 *       "thread": "http-nio-8080-exec-6",
 *       "class": "c.d.g.gtwcoremib.common.LoggingTimer",
 *       "rest": "SCHEDULER CHECK STATUS REQUEST: "
 *     }
 *   },
 *   {
 *     "container_name": "gtw-core-mib",
 *     "pod_name": "gtw-core-mib-67c58f7cff-xmgvf",
 *     "timestamp": "2023-11-30T09:17:29.077+00:00",
 *     "content": {
 *       "@timestamp": "2023-11-30T09:17:29.077+00:00",
 *       "severity": "INFO",
 *       "service": "gtw-core-mib",
 *       "trace": "af711f8c742d86a4",
 *       "span": "af711f8c742d86a4",
 *       "pid": "6",
 *       "thread": "http-nio-8080-exec-6",
 *       "class": "c.d.g.g.service.SchedullerService",
 *       "rest": "Expired Time:24"
 *     }
 *   }
 * ]
 *
 * @author Daniel Joi Partogi Hutapea
 */
@SuppressWarnings("unused")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AckLogV1
{
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private String containerName;
    private Log content;

    public String getContainerName()
    {
        return containerName;
    }

    public void setContainerName(String containerName)
    {
        this.containerName = containerName;
    }

    public Log getContent()
    {
        return content;
    }

    public void setContent(Log content)
    {
        this.content = content;
    }

    public static String parse(String logsData, boolean simplified)
    {
        try
        {
            List<AckLogV1> listOfAckLogV1 = OBJECT_MAPPER.readValue(logsData, new TypeReference<List<AckLogV1>>(){});

            if(listOfAckLogV1==null || listOfAckLogV1.isEmpty())
            {
                throw new RuntimeException(String.format("This is not a valid %s.", AckLogV1.class.getSimpleName()));
            }

            StringBuilder sb = new StringBuilder();

            for(AckLogV1 ackLogV1 : listOfAckLogV1)
            {
                sb.append(ackLogV1.getContent().toString(simplified)).append("\n");
            }

            return sb.toString();

        }
        catch(Exception ex)
        {
            throw new RuntimeException(String.format("Failed to parse logsData. Cause: %s", ex.getMessage()), ex);
        }
    }
}
