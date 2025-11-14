package com.karyasarma.toolkit.doku.util;

import com.karyasarma.toolkit.doku.model.ClipboardData;

/**
 * @author Daniel Joi Partogi Hutapea
 */
public class ConfluenceUtils
{
    private static final String CODE_BLOCK_PLAINTEXT_TEMPLATE = "<meta charset='utf-8'><table class=\"wysiwyg-macro\" data-macro-name=\"code\" data-macro-parameters=\"language=text\" data-macro-schema-version=\"1\" data-macro-body-type=\"PLAIN_TEXT\" style=\"background-color: rgb(240, 240, 240); background-position: 0px 0px; background-repeat: no-repeat; border: 1px solid rgb(221, 221, 221); margin-top: 10px; padding: 24px 2px 2px; width: 1432px; border-collapse: separate; cursor: move; background-image: url(&quot;/plugins/servlet/confluence/placeholder/macro-heading?definition=e2NvZGU6bGFuZ3VhZ2U9dGV4dH0&amp;locale=en_GB&amp;version=2&quot;);\"><tbody><tr><td class=\"wysiwyg-macro-body\" style=\"white-space: pre-wrap; background-color: rgb(255, 255, 255); border: 1px solid rgb(221, 221, 221); margin: 0px; padding: 10px; cursor: text;\"><pre style=\"margin: 0px; tab-size: 4; white-space: pre-wrap;\">%s</pre></td></tr></tbody></table><p class=\"auto-cursor-target\" style=\"margin: 10px 0px 0px;\"><br style=\"color: rgb(23, 43, 77); font-family: -apple-system, &quot;system-ui&quot;, &quot;Segoe UI&quot;, Roboto, Oxygen, Ubuntu, &quot;Fira Sans&quot;, &quot;Droid Sans&quot;, &quot;Helvetica Neue&quot;, sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: left; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial;\">";
    private static final String CODE_BLOCK_SQL_TEMPLATE = "<meta charset='utf-8'><table class=\"wysiwyg-macro\" data-macro-name=\"code\" data-macro-parameters=\"language=sql\" data-macro-schema-version=\"1\" data-macro-body-type=\"PLAIN_TEXT\" style=\"background-color: rgb(240, 240, 240); background-position: 0px 0px; background-repeat: no-repeat; border: 1px solid rgb(221, 221, 221); padding: 24px 2px 2px; width: 1432px; border-collapse: separate; cursor: move; background-image: url(&quot;/plugins/servlet/confluence/placeholder/macro-heading?definition=e2NvZGU6bGFuZ3VhZ2U9c3FsfQ&amp;locale=en_GB&amp;version=2&quot;);\"><tbody><tr><td class=\"wysiwyg-macro-body\" style=\"white-space: pre-wrap; background-color: rgb(255, 255, 255); border: 1px solid rgb(221, 221, 221); margin: 0px; padding: 10px; cursor: text;\"><pre style=\"margin: 0px; tab-size: 4; white-space: pre-wrap;\">%s<br></pre></td></tr></tbody></table><p class=\"auto-cursor-target\" style=\"margin: 10px 0px 0px;\"><br style=\"color: rgb(23, 43, 77); font-family: -apple-system, &quot;system-ui&quot;, &quot;Segoe UI&quot;, Roboto, Oxygen, Ubuntu, &quot;Fira Sans&quot;, &quot;Droid Sans&quot;, &quot;Helvetica Neue&quot;, sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: left; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial;\">";

    private ConfluenceUtils()
    {
    }

    public static ClipboardData toCodeBlockPlainText(String content)
    {
        return toCodeBlock(CODE_BLOCK_PLAINTEXT_TEMPLATE, content);
    }

    public static ClipboardData toCodeBlockSql(String content)
    {
        return toCodeBlock(CODE_BLOCK_SQL_TEMPLATE, content);
    }

    public static ClipboardData toCodeBlock(String template, String content)
    {
        String contentHtml = String.format
        (
            template,
            content
        );

        return new ClipboardData(content, contentHtml);
    }
}
