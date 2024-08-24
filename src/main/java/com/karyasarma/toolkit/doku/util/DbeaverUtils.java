package com.karyasarma.toolkit.doku.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Daniel Joi Partogi Hutapea
 */
public class DbeaverUtils
{
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private DbeaverUtils()
    {
    }

    @SuppressWarnings("unchecked")
    public static String fromCopyAsJsonToText(String dbeaverCopyAsJsonData, boolean printNullAsEmptyString, boolean sortedByColumnName)
    {
        StringBuilder result = new StringBuilder();

        try
        {
            Map<String, Object> jsonAsMap = OBJECT_MAPPER.readValue(dbeaverCopyAsJsonData, new TypeReference<Map<String, Object>>(){});
            Object firstValue = jsonAsMap.get(jsonAsMap.keySet().iterator().next());

            if(firstValue instanceof List)
            {
                List<Map<String, Object>> listOfRows = (List<Map<String, Object>>) firstValue;
                Pair<Integer, Integer> maxLengthForKeysAndValues = calculateMaxLengthForKeysAndValues(listOfRows);
                int maxLengthKeys = maxLengthForKeysAndValues.getLeft();
                int maxLengthValues = maxLengthForKeysAndValues.getRight();
                int counter = 1;

                for(Map<String, Object> rows : listOfRows)
                {
                    String recordInfo = StringUtils.rightPad(String.format("-[ RECORD %d ]", counter), maxLengthKeys + 1, '-');

                    if(recordInfo.length()<=maxLengthKeys+1)
                    {
                        recordInfo += "+";
                    }

                    String recordInfoSuffix = StringUtils.rightPad("-", maxLengthValues+1, '-');

                    result.append(recordInfo).append(recordInfoSuffix).append('\n');

                    Map<String, Object> rowsNormalized = sortedByColumnName? new TreeMap<>(rows) : rows;

                    for(Map.Entry<String, Object> entry : rowsNormalized.entrySet())
                    {
                        Object value = entry.getValue();

                        String[] valueSplitByNewLine = value==null?
                            new String[]{printNullAsEmptyString? "" : "[NULL]"} :
                            value.toString().split("\n");

                        boolean firstLine = true;

                        for(String valueAsString : valueSplitByNewLine)
                        {
                            String key = firstLine?
                                StringUtils.rightPad(entry.getKey(), maxLengthKeys, ' ') :
                                StringUtils.rightPad("", maxLengthKeys, ' ');

                            result.append(key).append(" | ").append(valueAsString).append('\n');
                            firstLine = false;
                        }
                    }
                    counter++;
                }
            }
            else
            {
                throw new RuntimeException("JSON input is not a valid DBeaver 'Copy as JSON' data.");
            }
        }
        catch(JsonProcessingException ex)
        {
            throw new RuntimeException("Failed on method rowsAsJsonToVerticalText. Cause: " + ex.getMessage(), ex);
        }

        return result.toString();
    }

    private static Pair<Integer, Integer> calculateMaxLengthForKeysAndValues(List<Map<String, Object>> listOfRows)
    {
        int maxLengthKeys = 0;
        int maxLengthValues = 0;

        for(Map<String, Object> rows : listOfRows)
        {
            for(Map.Entry<String, Object> entry : rows.entrySet())
            {
                int lengthKey = entry.getKey().length();

                if(lengthKey > maxLengthKeys)
                {
                    maxLengthKeys = lengthKey;
                }

                Object value = entry.getValue();

                if(value!=null)
                {
                    String[] valueAsStringSplitByNewLine = value.toString().split("\n");

                    for(String valueAsString : valueAsStringSplitByNewLine)
                    {
                        int lengthValue = valueAsString.length();

                        if(lengthValue > maxLengthValues)
                        {
                            maxLengthValues = lengthValue;
                        }
                    }
                }
            }
        }

        return Pair.of(maxLengthKeys, maxLengthValues);
    }
}
