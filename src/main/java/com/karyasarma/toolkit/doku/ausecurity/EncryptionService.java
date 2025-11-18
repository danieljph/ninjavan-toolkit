package com.karyasarma.toolkit.doku.ausecurity;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Copied from au-security-common with some modifications.
 *
 * @author Daniel Joi Partogi Hutapea
 */
public class EncryptionService
{
    private static final Properties PROPERTIES = new Properties();

    private static final String CIPHERTEXT_DELIMITER = "|";

    private static String keyAliasActive;
    private static final Map<String, SecretKey> MAP_OF_KEY_ALIAS_SECRET_KEY = new HashMap<>();

    static
    {
        try(InputStream is = EncryptionService.class.getResourceAsStream("/au-security-common/au-security-common-dev.properties"))
        {
            if(is != null)
            {
                PROPERTIES.load(is);

                keyAliasActive = PROPERTIES.getProperty("encryption.au.active-key.aliases");

                for(String propertyName : PROPERTIES.stringPropertyNames())
                {
                    if(propertyName.startsWith("encryption.au.key.") && propertyName.endsWith(".value"))
                    {
                        String keyAlias = propertyName.split("[.]")[3];
                        String keyValue = PROPERTIES.getProperty(propertyName);
                        SecretKey secretKey = stringKeyToSecretKey(keyValue);
                        MAP_OF_KEY_ALIAS_SECRET_KEY.put(keyAlias, secretKey);
                    }
                }
            }
            else
            {
                System.err.println("Properties file not found in classpath.");
            }
        }
        catch(IOException ex)
        {
            System.err.println("Error loading properties file. Cause: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
    }

    private EncryptionService()
    {
    }

    public static String encryptStringAscii(String message, InputDataType dataType)
    {
        IvParameterSpec ivSpec = AES256EncryptionUtil.generateIv();
        String ivSpecAsBase64 = Base64.getEncoder().encodeToString(ivSpec.getIV());
        String cipherText = AES256EncryptionUtil.encrypt(message, MAP_OF_KEY_ALIAS_SECRET_KEY.get(keyAliasActive), ivSpec);
        return buildResultEncrypt(cipherText, keyAliasActive, FormatMessageType.TEXT.value(), dataType.value(), ivSpecAsBase64);
    }

    public static String decrypt(String message)
    {
        String[] encMsgFormat = message.split("\\|");
        String cipherText = encMsgFormat[0];
        String keyAlias;
        String ivSpecEncoded = "-";

        if(encMsgFormat.length < 2)
        {
            keyAlias = "XX";
        }
        else if(encMsgFormat.length < 3)
        {
            keyAlias = encMsgFormat[1];
        }
        else
        {
            cipherText = encMsgFormat[0];
            keyAlias = encMsgFormat[1];
            ivSpecEncoded = encMsgFormat[4];
        }

        byte[] decodedIV;

        if("-".equals(ivSpecEncoded))
        {
            decodedIV = new byte[16];
        }
        else
        {
            decodedIV = Base64.getDecoder().decode(ivSpecEncoded);
        }

        SecretKey secretKey = MAP_OF_KEY_ALIAS_SECRET_KEY.get(keyAlias);

        IvParameterSpec ivSpec = new IvParameterSpec(decodedIV);

        if("-".equals(ivSpecEncoded) && "XX".equalsIgnoreCase(keyAlias))
        {
            return AES256EncryptionUtil.decrypt(cipherText, secretKey);
        }
        else
        {
            return AES256EncryptionUtil.decrypt(cipherText, secretKey, ivSpec);
        }
    }

    private static SecretKey stringKeyToSecretKey(String plainKey)
    {
        byte[] byteKey = Base64.getDecoder().decode(plainKey);
        return new SecretKeySpec(byteKey, 0, byteKey.length, "AES");
    }

    private static String buildResultEncrypt(String cipherText, String keyAlias, String inputFormat, String dataType, String iv)
    {
        return cipherText +
            CIPHERTEXT_DELIMITER +
            keyAlias +
            CIPHERTEXT_DELIMITER +
            inputFormat +
            CIPHERTEXT_DELIMITER +
            dataType +
            CIPHERTEXT_DELIMITER +
            iv;
    }
}
