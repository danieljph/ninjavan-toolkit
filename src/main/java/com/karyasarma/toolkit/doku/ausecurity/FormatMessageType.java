package com.karyasarma.toolkit.doku.ausecurity;

/**
 * Copied from au-security-common with some modifications.
 *
 * @author Daniel Joi Partogi Hutapea
 */
public enum FormatMessageType
{
    BINARY ("0"), HEX ("1"), TEXT ("2");

    private final String formatFlag;

    FormatMessageType(String c)
    {
        formatFlag= c;
    }

    public String value()
    {
        return formatFlag;
    }
}
