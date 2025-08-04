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
            "# set as false for development environment\n" +
            "encryption.data-type.1.using.hsm=false\n" +
            "encryption.data-type.2.using.hsm=false\n" +
            "# encrypt with HSM\n" +
            "# currently hsm only available for prod environment\n" +
            "encryption.hsm.enabled=false\n" +
            "encryption.hsm.address=hsm1.doku.local:1500\n" +
            "# key HSM\n" +
            "encryption.hsm.key.TI.value=S1012821AN01S00004CAB288B194F3D1B363FDC162B218B514EA886B1FF7832362FAE55A5298D98271546723E05DBBCB17CF82E2F791C617D0D746A8AFA1453D1\n" +
            "encryption.hsm.key.TI.algorithm=AES/ECB/PKCS5Padding\n" +
            "\n" +
            "# active key\n" +
            "encryption.hsm.active-key.aliases=TI\n" +
            "encryption.hsm.active-key.value=${encryption.hsm.key.TI.value}\n" +
            "encryption.hsm.active-key.algorithm=\n" +
            "\n" +
            "# encrypt with internal AU encryption\n" +
            "# key AU\n" +
            "encryption.au.key.DEV-AA.value=y5MU4Hnsg4kJGNzXIUKP0cgSFAVbg7JUVvUC/lKD/Ec=\n" +
            "encryption.au.key.DEV-AA.algorithm=AES/CBC/PKCS5Padding\n" +
            "encryption.au.key.DEV-AA.length=256\n" +
            "\n" +
            "encryption.au.key.AA.value=y5MU4Hnsg4kJGNzXIUKP0cgSFAVbg7JUVvUC/lKD/Ec=\n" +
            "encryption.au.key.AA.algorithm=AES/CBC/PKCS5Padding\n" +
            "encryption.au.key.AA.length=256\n" +
            "\n" +
            "encryption.au.key.XX.value=1WnJOfYc1WbMDojIXWKN3Q==\n" +
            "encryption.au.key.XX.algorithm=AES/ECB/PKCS5Padding\n" +
            "encryption.au.key.XX.length=128\n" +
            "\n" +
            "# active key\n" +
            "encryption.au.active-key.aliases=DEV-AA\n" +
            "encryption.au.active-key.value=${encryption.au.key.DEV-AA.value}\n" +
            "encryption.au.active-key.algorithm=${encryption.au.key.DEV-AA.algorithm}\n" +
            "encryption.au.active-key.length=${encryption.au.key.DEV-AA.length}\n" +
            "\n" +
            "#KeymanagementProperties\n" +
            "encryption.keymanagement.enabled=false\n" +
            "key.directory.keylib=classpath:key_management\n" +
            "keymanagement.ip.server.keylib=doku-keymanagement.apple-s-au.svc\n" +
            "keymanagement.port.server.keylib=1235\n";
    }
}
