package com.karyasarma.toolkit.doku.model;

import java.util.ArrayList;
import java.util.Optional;

/**
 * @author Daniel Joi Partogi Hutapea
 */
public class ClipboardHistoryData extends ArrayList<ClipboardData>
{
    private final int maxSize;

    public ClipboardHistoryData(int size)
    {
        super();
        this.maxSize = size;
    }

    public void push(ClipboardData clipboardData)
    {
        remove(clipboardData);

        // If the data is too big, remove elements until it's the right size.
        while(size() >= maxSize)
        {
            remove(size() - 1);
        }

        add(0, clipboardData);
    }

    public ClipboardData peek()
    {
        return size()==0 ? null : get(0);
    }

    public boolean isClipboardDataChanged(ClipboardData currentClipboardData)
    {
        ClipboardData peek = peek();

        if(peek == null)
        {
            return true;
        }

        return !peek.equals(currentClipboardData);
    }
}
