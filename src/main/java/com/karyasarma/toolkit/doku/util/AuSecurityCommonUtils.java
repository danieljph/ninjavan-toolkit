package com.karyasarma.toolkit.doku.util;

/**
 * @author Daniel Joi Partogi Hutapea
 */
public class AuSecurityCommonUtils
{
    private AuSecurityCommonUtils()
    {
    }

    public static String getAuSecurityCommonDevContent()
    {
        return "# MODULE ENCRYPTION CONFIG\n" +
            "\n" +
            "# set as false for development environment\n" +
            "\n" +
            "encryption.data-type.1.using.hsm=false\n" +
            "\n" +
            "encryption.data-type.2.using.hsm=false\n" +
            "\n" +
            "# encrypt with HSM\n" +
            "\n" +
            "# currently hsm only available for prod environment\n" +
            "\n" +
            "encryption.hsm.enabled=false\n" +
            "\n" +
            "encryption.hsm.address=hsm1.doku.local:1500\n" +
            "\n" +
            "# key HSM\n" +
            "\n" +
            "encryption.hsm.key.TI.value=S1012821AN01S00004CAB288B194F3D1B363FDC162B218B514EA886B1FF7832362FAE55A5298D98271546723E05DBBCB17CF82E2F791C617D0D746A8AFA1453D1\n" +
            "\n" +
            "encryption.hsm.key.TI.algorithm=AES/ECB/PKCS5Padding\n" +
            "\n" +
            "\n" +
            "\n" +
            "# active key\n" +
            "\n" +
            "encryption.hsm.active-key.aliases=TI\n" +
            "\n" +
            "encryption.hsm.active-key.value=${encryption.hsm.key.TI.value}\n" +
            "\n" +
            "encryption.hsm.active-key.algorithm=\n" +
            "\n" +
            "\n" +
            "\n" +
            "# encrypt with internal AU encryption\n" +
            "\n" +
            "# key AU\n" +
            "\n" +
            "encryption.au.key.DEV-AA.value={cipher}e566e25e27c48fe5777bdc2f2422fe3f7721232def7ab0616719215cc7e3ef4ef771600d7eeb1b906f84f968dff23ea64559003d420f133b2ea59162655e273b\n" +
            "\n" +
            "encryption.au.key.DEV-AA.algorithm=AES/CBC/PKCS5Padding\n" +
            "\n" +
            "encryption.au.key.DEV-AA.length=256\n" +
            "\n" +
            "\n" +
            "\n" +
            "encryption.au.key.AA.value={cipher}e566e25e27c48fe5777bdc2f2422fe3f7721232def7ab0616719215cc7e3ef4ef771600d7eeb1b906f84f968dff23ea64559003d420f133b2ea59162655e273b\n" +
            "\n" +
            "encryption.au.key.AA.algorithm=AES/CBC/PKCS5Padding\n" +
            "\n" +
            "encryption.au.key.AA.length=256\n" +
            "\n" +
            "\n" +
            "\n" +
            "encryption.au.key.XX.value={cipher}3d1fbad9ba62d8e2d7b149cfe3f88634e456d9c9110c033573485d056411e9d536212ff2eb2c6a16ef8b5d4057901dcf\n" +
            "\n" +
            "encryption.au.key.XX.algorithm=AES/ECB/PKCS5Padding\n" +
            "\n" +
            "encryption.au.key.XX.length=128\n" +
            "\n" +
            "\n" +
            "\n" +
            "# active key\n" +
            "\n" +
            "encryption.au.active-key.aliases=DEV-AA\n" +
            "\n" +
            "encryption.au.active-key.value=${encryption.au.key.DEV-AA.value}\n" +
            "\n" +
            "encryption.au.active-key.algorithm=${encryption.au.key.DEV-AA.algorithm}\n" +
            "\n" +
            "encryption.au.active-key.length=${encryption.au.key.DEV-AA.length}";
    }
}
