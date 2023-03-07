package com.karyasarma.ninjavan_toolkit;

import org.apache.commons.codec.binary.Base64OutputStream;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPOutputStream;

/**
 * @author Daniel Joi Partogi Hutapea
 */
public class Test
{
    public static String gzipAndBase64(String rawValue)
    {
        String result = rawValue;
        long l1 = System.currentTimeMillis();

        try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Base64OutputStream b64os = new Base64OutputStream(baos);
            GZIPOutputStream gzip = new GZIPOutputStream(b64os);
            gzip.write(rawValue.getBytes(StandardCharsets.UTF_8));
            gzip.close();
            b64os.close();
            String base64Result = new String(baos.toByteArray(), StandardCharsets.UTF_8).replaceAll("\r\n", "");
            String newValue = "GZIP_BASE64(" + base64Result + ')';

            if(newValue.length()<rawValue.length())
            {
                result = newValue;
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            long l2 = System.currentTimeMillis();
            System.out.printf("Duration (gzipAndBase64): %dms\n", (l2-l1));
        }

        return result;
    }

    public static void main(String[] args)
    {
        String rawValue = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        System.out.println(gzipAndBase64(rawValue));
    }
}
