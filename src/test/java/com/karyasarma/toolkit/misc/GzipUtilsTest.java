package com.karyasarma.toolkit.misc;

import org.junit.jupiter.api.Test;

import static com.karyasarma.toolkit.misc.GzipUtils.gzipAndBase64;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Daniel Joi Partogi Hutapea
 */
class GzipUtilsTest
{
    @Test
    void gzipAndBase64Test()
    {
        String rawValue = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        String result = gzipAndBase64(rawValue);
        System.out.println("Result: "+result);
        assertNotNull(result);
    }
}
