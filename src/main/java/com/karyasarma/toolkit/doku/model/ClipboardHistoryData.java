package com.karyasarma.toolkit.doku.model;

import java.util.ArrayList;

/**
 * @author Daniel Joi Partogi Hutapea
 */
public class ClipboardHistoryData extends ArrayList<ClipboardData>
{
    private final int maxSize;
    private ClipboardData activeClipboardData;

    public ClipboardHistoryData(int size)
    {
        super();
        this.maxSize = size;
    }

    public void push(ClipboardData clipboardData, boolean keepPosition)
    {
        activeClipboardData = clipboardData;

        if(keepPosition)
        {
            if(contains(clipboardData))
            {
                return;
            }
        }
        else
        {
            remove(clipboardData);
        }

        // If the data is too big, remove elements until it's the right size.
        while(size() >= maxSize)
        {
            remove(size() - 1);
        }

        add(0, clipboardData);
    }

    public void setActiveClipboardData(ClipboardData activeClipboardData)
    {
        this.activeClipboardData = activeClipboardData;
    }

    public ClipboardData getActiveClipboardData()
    {
        return activeClipboardData;
    }

    public ClipboardData peek()
    {
        return size()==0 ? null : get(0);
    }

    public boolean isClipboardDataChanged(ClipboardData currentClipboardData)
    {
        ClipboardData peek = getActiveClipboardData();

        if(peek == null)
        {
            return true;
        }

        return !peek.equals(currentClipboardData);
    }
}
