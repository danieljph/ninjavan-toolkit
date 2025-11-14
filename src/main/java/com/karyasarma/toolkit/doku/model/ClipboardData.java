package com.karyasarma.toolkit.doku.model;

import org.apache.commons.lang3.Strings;

import java.util.Objects;

/**
 * @author Daniel Joi Partogi Hutapea
 */
public class ClipboardData
{
    private final String content;
    private final String contentHtml;

    public ClipboardData(String content, String contentHtml)
    {
        this.content = content;
        this.contentHtml = contentHtml;
    }

    public boolean isHtmlFlavor()
    {
        return contentHtml != null;
    }

    public String getContent()
    {
        return content;
    }

    public String getContentHtml()
    {
        return contentHtml;
    }

    @Override
    public boolean equals(Object o)
    {
        if(o == null || getClass() != o.getClass())
        {
            return false;
        }

        return isHtmlFlavor()
            ? Strings.CS.equals(getContentHtml(), ((ClipboardData) o).getContentHtml())
            : Strings.CS.equals(getContent(), ((ClipboardData) o).getContent());
    }

    @Override
    public int hashCode()
    {
        return isHtmlFlavor()
            ? Objects.hash(getContentHtml())
            : Objects.hash(getContent());
    }
}
