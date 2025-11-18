package com.karyasarma.toolkit.doku.util;

import com.karyasarma.toolkit.doku.model.ClipboardData;
import com.karyasarma.toolkit.doku.model.ClipboardHtmlSelection;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * @author Daniel Joi Partogi Hutapea
 */
public class ClipboardUtils
{
    private ClipboardUtils()
    {
    }

    public static Transferable getContents(Object requestor)
    {
        return getSystemClipboard().getContents(requestor);
    }

    public static String getDataFromStringFlavor() throws IOException, UnsupportedFlavorException
    {
        return getData(DataFlavor.stringFlavor);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getData(DataFlavor flavor) throws IOException, UnsupportedFlavorException
    {
        return (T) getSystemClipboard().getData(flavor);
    }

    public static void convertCurrentClipboardToPlainText() throws IOException, UnsupportedFlavorException
    {
        copyToClipboard(getDataFromStringFlavor());
    }

    public static void copyToClipboard(String data)
    {
        Clipboard clipboard = getSystemClipboard();
        clipboard.setContents(new StringSelection(data), null);
    }

    public static void copyToClipboard(ClipboardData data)
    {
        Clipboard clipboard = getSystemClipboard();
        clipboard.setContents(new ClipboardHtmlSelection(data), null);
    }

    public static void clearClipboard()
    {
        Clipboard clipboard = getSystemClipboard();
        clipboard.setContents(new StringSelection(""), null);
    }

    public static Clipboard getSystemClipboard()
    {
        return Toolkit.getDefaultToolkit().getSystemClipboard();
    }
}
