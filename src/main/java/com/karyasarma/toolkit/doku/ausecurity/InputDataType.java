package com.karyasarma.toolkit.doku.ausecurity;

/**
 * Copied from au-security-common with some modifications.
 *
 * @author Daniel Joi Partogi Hutapea
 */
public enum InputDataType
{
    SENSITIVE_USER_DATA("1"),
    CREDENTIAL_SYSTEM("2");

    private final String dataType;

    InputDataType(String c)
    {
        dataType= c;
    }

    public String value()
    {
        return dataType;
    }
}
