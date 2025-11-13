package com.karyasarma.toolkit.doku.model;

import java.util.ArrayList;

/**
 * @author Daniel Joi Partogi Hutapea
 */
public class ClipboardHistoryData extends ArrayList<String>
{
    private final int maxSize;

    public ClipboardHistoryData(int size)
    {
        super();
        this.maxSize = size;
    }

    public void push(String item)
    {
        remove(item);

        // If the data is too big, remove elements until it's the right size.
        while(size() >= maxSize)
        {
            remove(size() - 1);
        }

        add(0, item);
    }

    public String peek()
    {
        return size()==0 ? null : get(0);
    }
}
