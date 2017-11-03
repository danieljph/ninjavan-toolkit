package com.karyasarma.ninjavan_toolkit.util;

/**
 *
 * @author Daniel Joi Partogi Hutapea
 */
public class IntegerCounter
{
    private int value;

    public IntegerCounter()
    {
    }

    public synchronized void inc()
    {
        value++;
    }

    public synchronized int getValue()
    {
        return value;
    }
}
