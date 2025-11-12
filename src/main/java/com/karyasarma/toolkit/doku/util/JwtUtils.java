package com.karyasarma.toolkit.doku.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.ZoneId;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author Daniel Joi Partogi Hutapea
 */
public class JwtUtils
{
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
        .registerModule(new JavaTimeModule())
        .setTimeZone(TimeZone.getTimeZone(ZoneId.of("Asia/Jakarta")))
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);

    private JwtUtils()
    {
    }

    public static String decode(String jwt)
    {
        String result;

        try
        {
            String[] chunks = jwt.split("\\.");

            if(chunks.length != 3)
            {
                result = "Value is not a valid JWT.";
            }
            else
            {
                Base64.Decoder decoder = Base64.getUrlDecoder();

                String header = new String(decoder.decode(chunks[0]));
                String payload = new String(decoder.decode(chunks[1]));
                String signature = chunks[2];

                Map<String, Object> tempObj = new LinkedHashMap<>();
                tempObj.put("header", OBJECT_MAPPER.readValue(header, LinkedHashMap.class));
                tempObj.put("payload", OBJECT_MAPPER.readValue(payload, LinkedHashMap.class));
                tempObj.put("signatureAsBase64UrlSafe", signature);

                return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(tempObj);
            }
        }
        catch(Exception ex)
        {
            result = "Failed to decode JWT. Cause:" + ex.getMessage();
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    public static String encode(String jsonContainsRawJwt)
    {
        String result;

        try
        {
            Map<String, Object> tempObj = OBJECT_MAPPER.readValue(jsonContainsRawJwt, Map.class);

            Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();

            String header = encoder.encodeToString(OBJECT_MAPPER.writeValueAsBytes(tempObj.get("header")));
            String payload = encoder.encodeToString(OBJECT_MAPPER.writeValueAsBytes(tempObj.get("payload")));
            String signature = (String) tempObj.get("signatureAsBase64UrlSafe");

            return header + "." + payload + "." + signature;
        }
        catch(Exception ex)
        {
            result = "Failed to decode JWT. Cause:" + ex.getMessage();
        }

        return result;
    }
}
