package com.karyasarma.toolkit.doku.model;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel Joi Partogi Hutapea
 */
public class ClipboardHtmlSelection implements Transferable
{
    private static final List<DataFlavor> SUPPORTED_DATA_FLAVORS = new ArrayList<>(2);

    static
    {
        SUPPORTED_DATA_FLAVORS.add(DataFlavor.stringFlavor);
        SUPPORTED_DATA_FLAVORS.add(DataFlavor.allHtmlFlavor);
    }

    private final ClipboardData clipboardData;

    public ClipboardHtmlSelection(ClipboardData clipboardData)
    {
        this.clipboardData = clipboardData;
    }

    public boolean isDataFlavorSupported(DataFlavor flavor)
    {
        return SUPPORTED_DATA_FLAVORS.contains(flavor);
    }

    @Override
    public DataFlavor[] getTransferDataFlavors()
    {
        // Define the supported data flavors for HTML and plain text.
        return SUPPORTED_DATA_FLAVORS.toArray(new DataFlavor[0]);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException
    {
        String toBeExported = clipboardData.getContent();

        if(flavor == DataFlavor.stringFlavor)
        {
            toBeExported = clipboardData.getContent();
        }
        else if(flavor == DataFlavor.allHtmlFlavor)
        {
            toBeExported = clipboardData.getContentHtml();
        }

        if (String.class.equals(flavor.getRepresentationClass()))
        {
            return toBeExported;
        }

        throw new UnsupportedFlavorException(flavor);
    }
}
