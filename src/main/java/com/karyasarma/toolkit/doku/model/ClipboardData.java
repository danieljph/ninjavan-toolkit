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
    public boolean equals(Object other)
    {
        if(other == null || getClass() != other.getClass())
        {
            return false;
        }

        ClipboardData otherClipboardData = (ClipboardData) other;

        return Strings.CS.equals(getContent(), otherClipboardData.getContent())
            && Strings.CS.equals(getContentHtml(), otherClipboardData.getContentHtml());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(content, contentHtml);
    }
}
