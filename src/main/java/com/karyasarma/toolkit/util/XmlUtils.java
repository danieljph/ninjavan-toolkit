package com.karyasarma.toolkit.util;

import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.StringWriter;

/**
 * @author Daniel Joi Partogi Hutapea
 */
public class XmlUtils
{
    private XmlUtils()
    {
    }

    public static String prettyPrint(String xmlString, int indent, boolean skipDeclaration)
    {
        try
        {
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setIndentSize(indent);
            format.setNewLineAfterDeclaration(false);
            format.setSuppressDeclaration(skipDeclaration);
            System.out.println(format.getEncoding());

            org.dom4j.Document document = DocumentHelper.parseText(xmlString);
            StringWriter sw = new StringWriter();
            XMLWriter writer = new XMLWriter(sw, format);
            writer.write(document);
            return sw.toString();
        }
        catch(Exception ex)
        {
            throw new RuntimeException("Error occurs when pretty-printing xml:\n" + xmlString, ex);
        }
    }

    public static String compactPrint(String xmlString)
    {
        try
        {
            OutputFormat format = OutputFormat.createCompactFormat();

            org.dom4j.Document document = DocumentHelper.parseText(xmlString);
            StringWriter sw = new StringWriter();
            XMLWriter writer = new XMLWriter(sw, format);
            writer.write(document);
            return sw.toString();
        }
        catch(Exception ex)
        {
            throw new RuntimeException("Error occurs when compact-printing xml:\n" + xmlString, ex);
        }
    }
}
