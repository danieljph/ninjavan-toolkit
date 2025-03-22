package com.karyasarma.toolkit.doku.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Iterator;
import java.util.TimeZone;

public class JsonSchemaUtil
{
    public static ObjectMapper objectMapper = new ObjectMapper()
        .registerModule(new JavaTimeModule())
        .setTimeZone(TimeZone.getTimeZone(ZoneId.of("Asia/Jakarta")))
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);

    private JsonSchemaUtil()
    {
    }

    public static String generateJsonSchema(String json) throws IOException
    {
        String jsonSchema = outputAsString("${tcId}", "${tcDesc}", json, null);
        Object temp = objectMapper.readValue(jsonSchema, Object.class);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(temp);
    }

    private static String outputAsString(String title, String description, String json, JsonNodeType type) throws IOException
    {
        JsonNode jsonNode = objectMapper.readTree(json);
        StringBuilder output = new StringBuilder();
        output.append("{");

        if(type == null)
        {
            output.append("\"$schema\": \"https://json-schema.org/draft-07/schema\",")
                .append("\"title\": \"")
                .append(title)
                .append("\", \"description\": \"")
                .append(description)
                .append("\", \"type\": \"object\", \"properties\": {");
        }

        for(Iterator<String> iterator = jsonNode.fieldNames(); iterator.hasNext();)
        {
            String fieldName = iterator.next();
            JsonNodeType nodeType = jsonNode.get(fieldName).getNodeType();
            output.append(convertNodeToStringSchemaNode(jsonNode, nodeType, fieldName));

            if(iterator.hasNext())
            {
                output.append(",");
            }
        }

        if(type == null)
        {
            output.append("}");
        }

        output.append("}");
        return output.toString();
    }

    private static String convertNodeToStringSchemaNode(JsonNode parentNode, JsonNodeType parentNodeType, String key) throws IOException
    {
        StringBuilder result = new StringBuilder("\"" + key + "\": { \"type\": \"");
        JsonNode node;

        switch(parentNodeType)
        {
            case ARRAY:
                node = parentNode.get(key);
                result.append("array\", \"items\": [");

                for(int i=0; i<node.size(); i++)
                {
                    result.append("{ \"properties\":");
                    result.append(outputAsString(null, null, node.get(i).toString(), JsonNodeType.ARRAY));
                    result.append("}");

                    if(i<node.size()-1)
                    {
                        result.append(",");
                    }
                }

                result.append("]}");
                break;
            case BOOLEAN:
                result.append("boolean\" }");
                break;
            case NUMBER:
                result.append("number\"");
                result.append(", \"const\":").append(parentNode.get(key).asInt());
                result.append(" }");
                break;
            case OBJECT:
                node = parentNode.get(key);
                result.append("object\", \"properties\": ");
                result.append(outputAsString(null, null, node.toString(), JsonNodeType.OBJECT));
                result.append("}");
                break;
            case STRING:
                result.append("string\"");
                result.append(", \"const\":\"").append(parentNode.get(key).textValue()).append("\"");
                result.append(" }");
                break;
            default:
                result.append("null\"");
                result.append(" }");
                break;
        }

        return result.toString();
    }
}
