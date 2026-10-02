package com.karyasarma.toolkit.doku.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.Separators;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @author Daniel Joi Partogi Hutapea
 */
public class JsonUtils
{
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
        .enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS); // Enabled to keep precision.

    public static final ObjectMapper OBJECT_MAPPER_PRETTY = new ObjectMapper()
        .setDefaultPrettyPrinter(
            new DefaultPrettyPrinter()
                .withSeparators(
                    Separators
                        .createDefaultInstance()
                        .withObjectFieldValueSpacing(Separators.Spacing.AFTER)
                )
                .withArrayIndenter(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE)
        )
        .enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS) // Enabled to keep precision.
        .enable(SerializationFeature.INDENT_OUTPUT);

    public static final ObjectMapper OBJECT_MAPPER_PRETTY_AND_SORT_PROPERTIES_ALPHABETICALLY = new ObjectMapper()
        .setDefaultPrettyPrinter(
            new DefaultPrettyPrinter()
                .withSeparators(
                    Separators
                        .createDefaultInstance()
                        .withObjectFieldValueSpacing(Separators.Spacing.AFTER)
                )
                .withArrayIndenter(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE)
        )
        .enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS) // Enabled to keep precision.
        .enable(SerializationFeature.INDENT_OUTPUT)
        .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

    public static String minify(String json) throws JsonProcessingException
    {
        Object temp = JsonUtils.OBJECT_MAPPER.readValue(json, Object.class);
        return JsonUtils.OBJECT_MAPPER.writeValueAsString(temp);
    }

    public static String prettify(String json) throws JsonProcessingException
    {
        Object temp = JsonUtils.OBJECT_MAPPER.readValue(json, Object.class);
        return JsonUtils.OBJECT_MAPPER_PRETTY.writeValueAsString(temp);
    }

    public static String prettifyIgnoreException(String json)
    {
        try
        {
            return prettify(json);
        }
        catch(Exception ex)
        {
            ex.printStackTrace(System.err);
            return json;
        }
    }

    public static String ordered(String json) throws JsonProcessingException
    {
        Object temp = JsonUtils.OBJECT_MAPPER.readValue(json, Object.class);
        return JsonUtils.OBJECT_MAPPER_PRETTY_AND_SORT_PROPERTIES_ALPHABETICALLY.writeValueAsString(temp);
    }
}
