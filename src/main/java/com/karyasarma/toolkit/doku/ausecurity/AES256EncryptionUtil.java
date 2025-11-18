package com.karyasarma.toolkit.doku.ausecurity;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Copied from au-security-common with some modifications.
 *
 * @author Daniel Joi Partogi Hutapea
 */
public class AES256EncryptionUtil
{
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String ALGORITHM_ECB = "AES/ECB/PKCS5Padding";

    protected AES256EncryptionUtil()
    {
        throw new IllegalStateException("Utility class");
    }

    public static String encrypt(String input, SecretKey key, IvParameterSpec iv)
    {
        try
        {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] cipherText = cipher.doFinal(input.getBytes());
            return Base64.getEncoder().encodeToString(cipherText);
        }
        catch(GeneralSecurityException ex)
        {
            throw new RuntimeException("Failed encrypt with internal AU class.", ex);
        }
    }

    public static String decrypt(String cipherText, SecretKey key)
    {
        try
        {
            Cipher cipher = Cipher.getInstance(ALGORITHM_ECB);
            cipher.init(2, key);
            byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText.trim()));
            return new String(plainText);
        }
        catch(GeneralSecurityException ex)
        {
            throw new RuntimeException("Failed decrypt with internal AU class.", ex);
        }
    }

    public static String decrypt(String cipherText, SecretKey key, IvParameterSpec iv)
    {
        try
        {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText.trim()));
            return new String(plainText);
        }
        catch(GeneralSecurityException ex)
        {
            throw new RuntimeException("Failed decrypt with internal AU class.", ex);
        }
    }

    public static IvParameterSpec generateIv()
    {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }
}
