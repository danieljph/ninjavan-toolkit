package com.karyasarma.toolkit.doku.util;

import com.karyasarma.toolkit.doku.model.ClipboardData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Daniel Joi Partogi Hutapea
 */
public class ConfluenceUtils
{
    private static final String CODE_BLOCK_PLAINTEXT_BLANK_TEMPLATE = "<meta charset='utf-8'><table class=\"wysiwyg-macro\" data-macro-name=\"code\" data-macro-parameters=\"language=text\" data-macro-schema-version=\"1\" data-macro-body-type=\"PLAIN_TEXT\" style=\"background-color: rgb(240, 240, 240); background-position: 0px 0px; background-repeat: no-repeat; border: 1px solid rgb(221, 221, 221); margin-top: 10px; padding: 24px 2px 2px; width: 1432px; border-collapse: separate; cursor: move; background-image: url(&quot;/plugins/servlet/confluence/placeholder/macro-heading?definition=e2NvZGU6bGFuZ3VhZ2U9dGV4dH0&amp;locale=en_GB&amp;version=2&quot;);\"><tbody><tr><td class=\"wysiwyg-macro-body\" style=\"white-space: pre-wrap; background-color: rgb(255, 255, 255); border: 1px solid rgb(221, 221, 221); margin: 0px; padding: 10px; cursor: text;\"><pre style=\"margin: 0px; tab-size: 4; white-space: pre-wrap;\"></pre></td></tr></tbody></table><p class=\"auto-cursor-target\" style=\"margin: 10px 0px 0px;\"><br style=\"color: rgb(23, 43, 77); font-family: -apple-system, &quot;system-ui&quot;, &quot;Segoe UI&quot;, Roboto, Oxygen, Ubuntu, &quot;Fira Sans&quot;, &quot;Droid Sans&quot;, &quot;Helvetica Neue&quot;, sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: left; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial;\">";
    private static final String CODE_BLOCK_PLAINTEXT_TEMPLATE = "<meta charset='utf-8'><table class=\"wysiwyg-macro\" data-macro-name=\"code\" data-macro-parameters=\"language=text\" data-macro-schema-version=\"1\" data-macro-body-type=\"PLAIN_TEXT\" style=\"background-color: rgb(240, 240, 240); background-position: 0px 0px; background-repeat: no-repeat; border: 1px solid rgb(221, 221, 221); margin-top: 10px; padding: 24px 2px 2px; width: 1432px; border-collapse: separate; cursor: move; background-image: url(&quot;/plugins/servlet/confluence/placeholder/macro-heading?definition=e2NvZGU6bGFuZ3VhZ2U9dGV4dH0&amp;locale=en_GB&amp;version=2&quot;);\"><tbody><tr><td class=\"wysiwyg-macro-body\" style=\"white-space: pre-wrap; background-color: rgb(255, 255, 255); border: 1px solid rgb(221, 221, 221); margin: 0px; padding: 10px; cursor: text;\"><pre style=\"margin: 0px; tab-size: 4; white-space: pre-wrap;\">%s</pre></td></tr></tbody></table><p class=\"auto-cursor-target\" style=\"margin: 10px 0px 0px;\"><br style=\"color: rgb(23, 43, 77); font-family: -apple-system, &quot;system-ui&quot;, &quot;Segoe UI&quot;, Roboto, Oxygen, Ubuntu, &quot;Fira Sans&quot;, &quot;Droid Sans&quot;, &quot;Helvetica Neue&quot;, sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: left; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial;\">";

    private static final String CODE_BLOCK_SQL_BLANK_TEMPLATE = "<meta charset='utf-8'><table class=\"wysiwyg-macro\" data-macro-name=\"code\" data-macro-parameters=\"language=sql\" data-macro-schema-version=\"1\" data-macro-body-type=\"PLAIN_TEXT\" style=\"background-color: rgb(240, 240, 240); background-position: 0px 0px; background-repeat: no-repeat; border: 1px solid rgb(221, 221, 221); padding: 24px 2px 2px; width: 1432px; border-collapse: separate; cursor: move; background-image: url(&quot;/plugins/servlet/confluence/placeholder/macro-heading?definition=e2NvZGU6bGFuZ3VhZ2U9c3FsfQ&amp;locale=en_GB&amp;version=2&quot;);\"><tbody><tr><td class=\"wysiwyg-macro-body\" style=\"white-space: pre-wrap; background-color: rgb(255, 255, 255); border: 1px solid rgb(221, 221, 221); margin: 0px; padding: 10px; cursor: text;\"><pre style=\"margin: 0px; tab-size: 4; white-space: pre-wrap;\"><br></pre></td></tr></tbody></table><p class=\"auto-cursor-target\" style=\"margin: 10px 0px 0px;\"><br style=\"color: rgb(23, 43, 77); font-family: -apple-system, &quot;system-ui&quot;, &quot;Segoe UI&quot;, Roboto, Oxygen, Ubuntu, &quot;Fira Sans&quot;, &quot;Droid Sans&quot;, &quot;Helvetica Neue&quot;, sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: left; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial;\">";
    private static final String CODE_BLOCK_SQL_TEMPLATE = "<meta charset='utf-8'><table class=\"wysiwyg-macro\" data-macro-name=\"code\" data-macro-parameters=\"language=sql\" data-macro-schema-version=\"1\" data-macro-body-type=\"PLAIN_TEXT\" style=\"background-color: rgb(240, 240, 240); background-position: 0px 0px; background-repeat: no-repeat; border: 1px solid rgb(221, 221, 221); padding: 24px 2px 2px; width: 1432px; border-collapse: separate; cursor: move; background-image: url(&quot;/plugins/servlet/confluence/placeholder/macro-heading?definition=e2NvZGU6bGFuZ3VhZ2U9c3FsfQ&amp;locale=en_GB&amp;version=2&quot;);\"><tbody><tr><td class=\"wysiwyg-macro-body\" style=\"white-space: pre-wrap; background-color: rgb(255, 255, 255); border: 1px solid rgb(221, 221, 221); margin: 0px; padding: 10px; cursor: text;\"><pre style=\"margin: 0px; tab-size: 4; white-space: pre-wrap;\">%s<br></pre></td></tr></tbody></table><p class=\"auto-cursor-target\" style=\"margin: 10px 0px 0px;\"><br style=\"color: rgb(23, 43, 77); font-family: -apple-system, &quot;system-ui&quot;, &quot;Segoe UI&quot;, Roboto, Oxygen, Ubuntu, &quot;Fira Sans&quot;, &quot;Droid Sans&quot;, &quot;Helvetica Neue&quot;, sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: left; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial;\">";

    private static final String EXPAND_BLANK_TEMPLATE = "<meta charset='utf-8'><table class=\"wysiwyg-macro\" data-macro-name=\"expand\" data-macro-schema-version=\"1\" data-macro-body-type=\"RICH_TEXT\" style=\"background-color: rgb(240, 240, 240); background-position: 0px 0px; background-repeat: no-repeat; border: 1px solid rgb(221, 221, 221); margin-top: 10px; padding: 24px 2px 2px; width: 1432px; border-collapse: separate; cursor: move; background-image: url(&quot;/plugins/servlet/confluence/placeholder/macro-heading?definition=e2V4cGFuZH0&amp;locale=en_GB&amp;version=2&quot;);\"><tbody><tr><td class=\"wysiwyg-macro-body\" style=\"white-space: pre-wrap; background-color: rgb(255, 255, 255); border: 1px solid rgb(221, 221, 221); margin: 0px; padding: 10px; cursor: text;\"><p style=\"margin: 0px;\"><br></p></td></tr></tbody></table><p style=\"margin: 10px 0px 0px;\"><br style=\"color: rgb(23, 43, 77); font-family: -apple-system, &quot;system-ui&quot;, &quot;Segoe UI&quot;, Roboto, Oxygen, Ubuntu, &quot;Fira Sans&quot;, &quot;Droid Sans&quot;, &quot;Helvetica Neue&quot;, sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: left; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial;\">";
    private static final String EXPAND_PLUS_CODE_BLOCK_BLANK_TEMPLATE = "<meta charset='utf-8'><table class=\"wysiwyg-macro\" data-macro-name=\"expand\" data-macro-schema-version=\"1\" data-macro-body-type=\"RICH_TEXT\" style=\"background-color: rgb(240, 240, 240); background-position: 0px 0px; background-repeat: no-repeat; border: 1px solid rgb(221, 221, 221); margin-top: 10px; padding: 24px 2px 2px; width: 1432px; border-collapse: separate; cursor: move; background-image: url(&quot;/plugins/servlet/confluence/placeholder/macro-heading?definition=e2V4cGFuZH0&amp;locale=en_GB&amp;version=2&quot;);\"><tbody><tr><td class=\"wysiwyg-macro-body\" style=\"white-space: pre-wrap; background-color: rgb(255, 255, 255); border: 1px solid rgb(221, 221, 221); margin: 0px; padding: 10px; cursor: text;\"><p class=\"auto-cursor-target\" style=\"margin: 0px;\"><br></p><table class=\"wysiwyg-macro\" data-macro-name=\"code\" data-macro-parameters=\"language=text\" data-macro-schema-version=\"1\" data-macro-body-type=\"PLAIN_TEXT\" style=\"background-color: rgb(240, 240, 240); background-position: 0px 0px; background-repeat: no-repeat; border: 1px solid rgb(221, 221, 221); margin-top: 10px; padding: 24px 2px 2px; width: 1400px; border-collapse: separate; cursor: move; background-image: url(&quot;/plugins/servlet/confluence/placeholder/macro-heading?definition=e2NvZGU6bGFuZ3VhZ2U9dGV4dH0&amp;locale=en_GB&amp;version=2&quot;);\"><tbody><tr><td class=\"wysiwyg-macro-body\" style=\"white-space: pre-wrap; background-color: rgb(255, 255, 255); border: 1px solid rgb(221, 221, 221); margin: 0px; padding: 10px; min-width: 200px; cursor: text;\"><pre style=\"margin: 0px; tab-size: 4; white-space: pre-wrap;\"><br></pre></td></tr></tbody></table><p class=\"auto-cursor-target\" style=\"margin: 10px 0px 0px;\"><br></p></td></tr></tbody></table><p style=\"margin: 10px 0px 0px;\"><br style=\"color: rgb(23, 43, 77); font-family: -apple-system, &quot;system-ui&quot;, &quot;Segoe UI&quot;, Roboto, Oxygen, Ubuntu, &quot;Fira Sans&quot;, &quot;Droid Sans&quot;, &quot;Helvetica Neue&quot;, sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: left; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial;\">";

    private static final String EXPAND_FULL_LOGS_TEMPLATE = """
        <table class="wysiwyg-macro" data-macro-name="expand" data-macro-parameters="title=Full Logs" data-macro-schema-version="1" data-macro-body-type="RICH_TEXT" style="background-color: rgb(240, 240, 240); background-position: 0px 0px; background-repeat: no-repeat; border: 1px solid rgb(221, 221, 221); margin-top: 10px; padding: 24px 2px 2px; width: 1432px; border-collapse: separate; cursor: move; background-image: url(&quot;/plugins/servlet/confluence/placeholder/macro-heading?definition=e2V4cGFuZDp0aXRsZT1GdWxsIExvZ3N9&amp;locale=en_GB&amp;version=2&quot;);">
          <tbody>
            <tr>
              <td class="wysiwyg-macro-body" style="white-space: pre-wrap; background-color: rgb(255, 255, 255); border: 1px solid rgb(221, 221, 221); margin: 0px; padding: 10px; cursor: text;">
                <p style="margin: 0px;">%s</p>
              </td>
            </tr>
          </tbody>
        </table>
        """;

    private static final String EXPAND_PLUS_CODE_BLOCK_PUBLISH_KAFKA_TEMPLATE = """
        <table class="wysiwyg-macro" data-macro-name="expand" data-macro-parameters="title=%s" data-macro-schema-version="1" data-macro-body-type="RICH_TEXT" style="background-color: rgb(240, 240, 240); background-position: 0px 0px; background-repeat: no-repeat; border: 1px solid rgb(221, 221, 221); margin-top: 10px; padding: 24px 2px 2px; width: 1432px; border-collapse: separate; cursor: move; background-image: url(&quot;/plugins/servlet/confluence/placeholder/macro-heading?definition=e2V4cGFuZH0&amp;locale=en_GB&amp;version=2&quot;);">
          <tbody>
            <tr>
              <td class="wysiwyg-macro-body" style="white-space: pre-wrap; background-color: rgb(255, 255, 255); border: 1px solid rgb(221, 221, 221); margin: 0px; padding: 10px; cursor: text;">
                <p class="auto-cursor-target" style="margin: 0px;"><br></p>
                <table class="wysiwyg-macro" data-macro-name="code" data-macro-parameters="language=js" data-macro-schema-version="1" data-macro-body-type="PLAIN_TEXT" style="background-color: rgb(240, 240, 240); background-position: 0px 0px; background-repeat: no-repeat; border: 1px solid rgb(221, 221, 221); margin-top: 10px; padding: 24px 2px 2px; width: 1400px; border-collapse: separate; cursor: move; background-image: url(&quot;/plugins/servlet/confluence/placeholder/macro-heading?definition=e2NvZGU6bGFuZ3VhZ2U9dGV4dH0&amp;locale=en_GB&amp;version=2&quot;);">
                  <tbody>
                    <tr>
                      <td class="wysiwyg-macro-body" style="white-space: pre-wrap; background-color: rgb(255, 255, 255); border: 1px solid rgb(221, 221, 221); margin: 0px; padding: 10px; min-width: 200px; cursor: text;">
                        <pre style="margin: 0px; tab-size: 4; white-space: pre-wrap;">%s<br></pre>
                      </td>
                    </tr>
                  </tbody>
                </table>
                <p class="auto-cursor-target" style="margin: 10px 0px 0px;"><br></p>
              </td>
            </tr>
          </tbody>
        </table>
        """;

    private static final String EXPAND_PLUS_CODE_BLOCK_HTTP_REQUEST_TEMPLATE = """
        <table class="wysiwyg-macro" data-macro-name="expand" data-macro-parameters="title=%s" data-macro-schema-version="1" data-macro-body-type="RICH_TEXT" style="background-color: rgb(240, 240, 240); background-position: 0px 0px; background-repeat: no-repeat; border: 1px solid rgb(221, 221, 221); margin-top: 10px; padding: 24px 2px 2px; width: 1432px; border-collapse: separate; cursor: move; background-image: url(&quot;/plugins/servlet/confluence/placeholder/macro-heading?definition=e2V4cGFuZH0&amp;locale=en_GB&amp;version=2&quot;);">
          <tbody>
            <tr>
              <td class="wysiwyg-macro-body" style="white-space: pre-wrap; background-color: rgb(255, 255, 255); border: 1px solid rgb(221, 221, 221); margin: 0px; padding: 10px; cursor: text;">
                <p class="auto-cursor-target" style="margin: 0px;"><br></p>
                <table class="wysiwyg-macro" data-macro-name="code" data-macro-parameters="language=text|title=Full HTTP Logs" data-macro-schema-version="1" data-macro-body-type="PLAIN_TEXT" style="background-color: rgb(240, 240, 240); background-position: 0px 0px; background-repeat: no-repeat; border: 1px solid rgb(221, 221, 221); margin-top: 10px; padding: 24px 2px 2px; width: 1400px; border-collapse: separate; cursor: move; background-image: url(&quot;/plugins/servlet/confluence/placeholder/macro-heading?definition=e2NvZGU6bGFuZ3VhZ2U9dGV4dH0&amp;locale=en_GB&amp;version=2&quot;);">
                  <tbody>
                    <tr>
                      <td class="wysiwyg-macro-body" style="white-space: pre-wrap; background-color: rgb(255, 255, 255); border: 1px solid rgb(221, 221, 221); margin: 0px; padding: 10px; min-width: 200px; cursor: text;">
                        <pre style="margin: 0px; tab-size: 4; white-space: pre-wrap;">%s<br></pre>
                      </td>
                    </tr>
                  </tbody>
                </table>
                <p class="auto-cursor-target" style="margin: 10px 0px 0px;"><br></p>
                <table class="wysiwyg-macro" data-macro-name="code" data-macro-parameters="language=text|title=HTTP Request Body" data-macro-schema-version="1" data-macro-body-type="PLAIN_TEXT" style="background-color: rgb(240, 240, 240); background-position: 0px 0px; background-repeat: no-repeat; border: 1px solid rgb(221, 221, 221); margin-top: 10px; padding: 24px 2px 2px; width: 1400px; border-collapse: separate; cursor: move; background-image: url(&quot;/plugins/servlet/confluence/placeholder/macro-heading?definition=e2NvZGU6bGFuZ3VhZ2U9dGV4dH0&amp;locale=en_GB&amp;version=2&quot;);">
                  <tbody>
                    <tr>
                      <td class="wysiwyg-macro-body" style="white-space: pre-wrap; background-color: rgb(255, 255, 255); border: 1px solid rgb(221, 221, 221); margin: 0px; padding: 10px; min-width: 200px; cursor: text;">
                        <pre style="margin: 0px; tab-size: 4; white-space: pre-wrap;">%s<br></pre>
                      </td>
                    </tr>
                  </tbody>
                </table>
                <p class="auto-cursor-target" style="margin: 10px 0px 0px;"><br></p>
                <table class="wysiwyg-macro" data-macro-name="code" data-macro-parameters="language=text|title=HTTP Response Body" data-macro-schema-version="1" data-macro-body-type="PLAIN_TEXT" style="background-color: rgb(240, 240, 240); background-position: 0px 0px; background-repeat: no-repeat; border: 1px solid rgb(221, 221, 221); margin-top: 10px; padding: 24px 2px 2px; width: 1400px; border-collapse: separate; cursor: move; background-image: url(&quot;/plugins/servlet/confluence/placeholder/macro-heading?definition=e2NvZGU6bGFuZ3VhZ2U9dGV4dH0&amp;locale=en_GB&amp;version=2&quot;);">
                  <tbody>
                    <tr>
                      <td class="wysiwyg-macro-body" style="white-space: pre-wrap; background-color: rgb(255, 255, 255); border: 1px solid rgb(221, 221, 221); margin: 0px; padding: 10px; min-width: 200px; cursor: text;">
                        <pre style="margin: 0px; tab-size: 4; white-space: pre-wrap;">%s<br></pre>
                      </td>
                    </tr>
                  </tbody>
                </table>
                <p class="auto-cursor-target" style="margin: 10px 0px 0px;"><br></p>
              </td>
            </tr>
          </tbody>
        </table>
        """;

    private static final Pattern DD_CORE_SYSTEM_LOG_PREFIX_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}.\\d{3}\\s+(ERROR|INFO|WARN|DEBUG)\\s+\\[.*");

    private static final Pattern DD_CORE_SYSTEM_LOG_PUBLISH_KAFKA_VARIANT_1_PATTERN = Pattern.compile(".*Sending\\sKafka\\sto\\stopic\\s:\\s(.*),\\smessage\\s:\\s.*");
    private static final Pattern DD_CORE_SYSTEM_LOG_PUBLISH_KAFKA_VARIANT_1_SPLIT_PATTERN = Pattern.compile("Sending\\sKafka\\sto\\stopic\\s:\\s(.*),\\smessage\\s:\\s");

    private static final Pattern DD_CORE_SYSTEM_LOG_PUBLISH_KAFKA_VARIANT_2_PATTERN = Pattern.compile(".*\\[(.*)]\\sKafka\\sevent\\shas been sent\\..*", Pattern.DOTALL);

    private ConfluenceUtils()
    {
    }

    public static String getContentName(String htmlContent)
    {
        if(CODE_BLOCK_PLAINTEXT_BLANK_TEMPLATE.equals(htmlContent))
        {
            return "[Confluence - Blank Code Block - Plaintext]";
        }
        else if(CODE_BLOCK_SQL_BLANK_TEMPLATE.equals(htmlContent))
        {
            return "[Confluence - Blank Code Block - SQL]";
        }
        else if(EXPAND_BLANK_TEMPLATE.equals(htmlContent))
        {
            return "[Confluence - Blank Expand]";
        }
        else if(EXPAND_PLUS_CODE_BLOCK_BLANK_TEMPLATE.equals(htmlContent))
        {
            return "[Confluence - Blank Expand + Code Block]";
        }

        return htmlContent;
    }

    public static ClipboardData blankCodeBlockPlaintext()
    {
        return new ClipboardData("", CODE_BLOCK_PLAINTEXT_BLANK_TEMPLATE);
    }

    public static ClipboardData blankCodeBlockSql()
    {
        return new ClipboardData("", CODE_BLOCK_SQL_BLANK_TEMPLATE);
    }

    public static ClipboardData toCodeBlockPlaintext(String content)
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

    public static ClipboardData blankExpand()
    {
        return new ClipboardData("", EXPAND_BLANK_TEMPLATE);
    }

    public static ClipboardData blankExpandPlusCodeBlock()
    {
        return new ClipboardData("", EXPAND_PLUS_CODE_BLOCK_BLANK_TEMPLATE);
    }

    public static ClipboardData parseLogsToExpandPlusCodeBlock(String logs)
    {
        var logsSplit = logs.split("\n");
        var listOfGroupedLog = new ArrayList<StringBuilder>();
        var groupedLog = new StringBuilder();

        var fullLogsTemplateData = new StringBuilder();
        var lineNumber = 1;

        for(var log : logsSplit)
        {
            if(lineNumber == 1)
            {
                fullLogsTemplateData.append(log);
            }
            else
            {
                fullLogsTemplateData.append("<br>").append(log);
            }

            if(DD_CORE_SYSTEM_LOG_PREFIX_PATTERN.matcher(log).matches())
            {
                groupedLog = new StringBuilder();
                groupedLog.append(log);
                listOfGroupedLog.add(groupedLog);
            }
            else
            {
                groupedLog.append("\n").append(log);
            }

            lineNumber++;
        }

        var fullLogsHtml = EXPAND_FULL_LOGS_TEMPLATE.formatted(fullLogsTemplateData.toString());

        var contentHtml = new StringBuilder();

        var listOf1HttpRequestLogs = new ArrayList<StringBuilder>();
        var httpRequestFound = false;
        Matcher matcher;

        for(var log : listOfGroupedLog)
        {
            if((matcher = DD_CORE_SYSTEM_LOG_PUBLISH_KAFKA_VARIANT_1_PATTERN.matcher(log)).matches())
            {
                var kafkaTopic = matcher.group(1);
                var kafkaPayload = DD_CORE_SYSTEM_LOG_PUBLISH_KAFKA_VARIANT_1_SPLIT_PATTERN.split(log, 2)[1];
                var kafkaPayloadPrettify = JsonUtils.prettifyIgnoreException(kafkaPayload);

                var contentHtmlTemp = EXPAND_PLUS_CODE_BLOCK_PUBLISH_KAFKA_TEMPLATE.formatted("Publish " + kafkaTopic, kafkaPayloadPrettify);
                contentHtml.append(contentHtmlTemp);
            }
            else if((matcher = DD_CORE_SYSTEM_LOG_PUBLISH_KAFKA_VARIANT_2_PATTERN.matcher(log)).matches())
            {
                var logSplit = log.toString().split("\n");

                var kafkaTopic = matcher.group(1);
                var partition = logSplit[1];
                var offset = logSplit[2];
                var key = logSplit[3];
                var headers = logSplit[4];
                var kafkaPayload = logSplit[6];
                var kafkaPayloadPrettify = JsonUtils.prettifyIgnoreException(kafkaPayload);
                var kafkaPayloadPrettifyWithAdditionalInfo = """
                    // %s
                    // %s
                    // %s
                    // %s
                    
                    %s
                    """.formatted(partition, offset, key, headers, kafkaPayloadPrettify);

                var contentHtmlTemp = EXPAND_PLUS_CODE_BLOCK_PUBLISH_KAFKA_TEMPLATE.formatted("Publish " + kafkaTopic, kafkaPayloadPrettifyWithAdditionalInfo);
                contentHtml.append(contentHtmlTemp);
            }
            else if(log.indexOf("CLIENT HTTP REQUEST START") != -1)
            {
                httpRequestFound = true;
                listOf1HttpRequestLogs = new ArrayList<>();
            }
            else if(log.indexOf("CLIENT HTTP REQUEST FINISH") != -1)
            {
                contentHtml.append(generateContentHtmlForHttpRequestLogs(listOf1HttpRequestLogs));
            }
            else if(httpRequestFound)
            {
                listOf1HttpRequestLogs.add(log);
            }
        }

        contentHtml.append(fullLogsHtml); // Add full logs at the bottom.

        return new ClipboardData(logs, contentHtml.toString());
    }

    private static final Map<Pattern, String> MAP_OF_PATTERN_OF_HTTP_REQUEST = new HashMap<>();

    static
    {
        MAP_OF_PATTERN_OF_HTTP_REQUEST.put(Pattern.compile("LoggingClientInterceptor\\s+:\\sRequest URL\\s+:\\s"), "requestUrl");
        MAP_OF_PATTERN_OF_HTTP_REQUEST.put(Pattern.compile("LoggingClientInterceptor\\s+:\\sRequest Header\\s+:\\s"), "requestHeaders");
        MAP_OF_PATTERN_OF_HTTP_REQUEST.put(Pattern.compile("LoggingClientInterceptor\\s+:\\sRequest body\\s+:\\s"), "requestBody");
        MAP_OF_PATTERN_OF_HTTP_REQUEST.put(Pattern.compile("LoggingClientInterceptor\\s+:\\sResponse status\\s+:\\s"), "responseStatus");
        MAP_OF_PATTERN_OF_HTTP_REQUEST.put(Pattern.compile("LoggingClientInterceptor\\s+:\\sResponse header\\s+:\\s"), "responseHeaders");
        MAP_OF_PATTERN_OF_HTTP_REQUEST.put(Pattern.compile("LoggingClientInterceptor\\s+:\\sResponse body\\s+:\\s"), "responseBody");

        MAP_OF_PATTERN_OF_HTTP_REQUEST.put(Pattern.compile("RestTemplateLoggingInterceptor\\s+:\\sRequest\\s+:\\s"), "requestUrl");
        MAP_OF_PATTERN_OF_HTTP_REQUEST.put(Pattern.compile("RestTemplateLoggingInterceptor\\s+:\\sRequest\\sHeader\\s+:\\s"), "requestHeaders");
        MAP_OF_PATTERN_OF_HTTP_REQUEST.put(Pattern.compile("RestTemplateLoggingInterceptor\\s+:\\sRequest\\sBody\\s+:\\s"), "requestBody");
        MAP_OF_PATTERN_OF_HTTP_REQUEST.put(Pattern.compile("RestTemplateLoggingInterceptor\\s+:\\sResponse\\sStatus\\s+:\\s"), "responseStatus");
        MAP_OF_PATTERN_OF_HTTP_REQUEST.put(Pattern.compile("RestTemplateLoggingInterceptor\\s+:\\sResponse\\sHeader\\s+:\\s"), "responseHeaders");
        MAP_OF_PATTERN_OF_HTTP_REQUEST.put(Pattern.compile("RestTemplateLoggingInterceptor\\s+:\\sResponse\\sBody\\s+:\\s"), "responseBody");
    }

    private static String generateContentHtmlForHttpRequestLogs(List<StringBuilder> listOf1HttpRequestLogs)
    {
        var mapOfValue = new HashMap<String, String>();

        for(var log : listOf1HttpRequestLogs)
        {
            for(var entry : MAP_OF_PATTERN_OF_HTTP_REQUEST.entrySet())
            {
                var pattern = entry.getKey();
                var valueAsKey = entry.getValue();
                var splitResult = pattern.split(log, 2);

                if(splitResult.length == 2)
                {
                    mapOfValue.put(valueAsKey, splitResult[1]);
                }
            }
        }

        var httpRequestLogsTemplateData = new StringBuilder();
        httpRequestLogsTemplateData.append("Request URL      : ").append(mapOfValue.get("requestUrl")).append("<br>");
        httpRequestLogsTemplateData.append("Request Headers  : ").append(mapOfValue.get("requestHeaders")).append("<br>");
        httpRequestLogsTemplateData.append("Request Body     : ").append(mapOfValue.get("requestBody")).append("<br>");
        httpRequestLogsTemplateData.append("<br>");
        httpRequestLogsTemplateData.append("Response Status  : ").append(mapOfValue.get("responseStatus")).append("<br>");
        httpRequestLogsTemplateData.append("Response Headers : ").append(mapOfValue.get("responseHeaders")).append("<br>");
        httpRequestLogsTemplateData.append("Response Body    : ").append(mapOfValue.get("responseBody"));

        var requestBody = JsonUtils.prettifyIgnoreException(mapOfValue.get("requestBody"));
        var responseBody = JsonUtils.prettifyIgnoreException(mapOfValue.get("responseBody"));

        return EXPAND_PLUS_CODE_BLOCK_HTTP_REQUEST_TEMPLATE.formatted
        (
            mapOfValue.get("requestUrl"),
            httpRequestLogsTemplateData,
            requestBody,
            responseBody
        );
    }

    public static void main(String[] args)
    {
        var logs = """
            2026-09-22 10:43:29.285 DEBUG [direct-debit-core-system,,] 7 --- [nio-9003-exec-4] c.d.a.s.module.http.ResponseAdvice : target path : /actuator/health
            2026-09-22 10:43:29.285 DEBUG [direct-debit-core-system,,] 7 --- [nio-9003-exec-3] c.d.a.s.module.http.ResponseAdvice : target path : /actuator/health
            2026-09-22 10:43:29.285 DEBUG [direct-debit-core-system,,] 7 --- [nio-9003-exec-4] c.d.a.s.module.http.ResponseAdvice : context-path :
            2026-09-22 10:43:29.285 DEBUG [direct-debit-core-system,,] 7 --- [nio-9003-exec-3] c.d.a.s.module.http.ResponseAdvice : context-path :
            2026-09-22 10:43:46.961 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.a.s.module.http.RequestHeaderFilter : servlet path : /direct-debit/core/v1/debit/payment-host-to-host
            2026-09-22 10:43:46.961 INFO [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] .d.d.c.m.m.c.MerchantInterfaceController : Receiving Request Payment, Payment Process is Started
            2026-09-22 10:43:46.962 INFO [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.d.c.m.m.s.MerchantInterfaceProcessor : Request Payment : {"partnerId":"BRN-0203-1728283759342","externalId":"RID_PH2H_1790048625387","bearerCustomer":"Bearer eyJhbGciOiJSUzI1NiJ9.eyJleHAiOjE3OTEzNDQzOTIsImlzcyI6IkRPS1UiLCJjbGllbnRJZCI6IkJSTi0wMjAzLTE3MjgyODM3NTkzNDIiLCJhY2NvdW50SWQiOiJjMGZjZDE0MTk3OWM3YTM3MTk4OWYxNDdhYmI3OWJjYyJ9.XHQqDRrJTa6V6rYGnOrsld3cINpjXbYHHFxhFEpEGIk4L_EqmPhqdSpqFtKib9waIpRTzYt443gtAnRDTVnweb_VDbsPQW20N-9PfJSI0WKGB9mE901P_6IyhfBl0yU3Rw2RrtYn48eQBA3kcWHz-0yX9QHMQzVQCqUstLrz--NM9Ffj5iF45FxrmF6uEt0wq5KeEnUTHAXZPTqAloErt_tfXKvtgcnqxMjoyYQd_z-XVXs3RJOCi9Say99tXDmm1rtgNiFp7SOthcUVkEuMJw0U5Zv9D6-kawMGLCFkS9Y0hUDpKWqwS8l4ofwE-sSrspyF39LOKgS-GIO6Xc3w6Q","serviceType":"H2H","partnerReferenceNo":"INVALLO1790048625387","journeyId":"09104345","amount":{"value":"12000.00","currency":"IDR"},"payOptionDetails":[{"payMethod":"BALANCE","transAmount":{"value":"72500.00","currency":"IDR"}}],"timestamp":"2026-09-22T10:43:45+07:00","signature":"NM1fVlXJIs523/tXWkCGX4+xvV+Yr0hGXy8R0Ms4vbGh8aa2z6mod6k/Abvz6WpXmA1TAaGVjbfHnbXpwS5wBg\\u003d\\u003d","deviceId":"912f12e223624d2e94828b168199asdf","ipAddress":"127.1.1.12","authorization":"Bearer eyJhbGciOiJSUzI1NiJ9.eyJleHAiOjE3OTAwNDkyODcsImlzcyI6IkRPS1UiLCJjbGllbnRJZCI6IkJSTi0wMjAzLTE3MjgyODM3NTkzNDIifQ.JdPd7tkyd_vnF6yBv9vz8FIephKSYVNB09AWZm62tyW4jqDzA8qVG7fA89v_2BKlQO2Y5dWN0SN0u6UlnMVy71373HGN828eXsZ0MdxhmKq8xzp7W6HUIF4wxXiv3-6sDwrmSMScgKOsvBZLkxpo-6BfU9wWKQb0NniRKdhEufXTFsYN4ZZhoDhLFUbQziLgQ9s3MHdjKqArMY7QmYM0YwmDL_MBvfIwihQPqhc8kmQ3BzdBZJL_fx5Yz3A7ceubRm9wVTqkq2CBqQc7h8G_4ShKz5hKHyslMz4cp9AON-7YXl28FPRq3DmFtsG3BOUGFQXfoQr-VUANhNEPY3YxTQ","additionalInfo":{"lineItems":[{"name":"Item One","price":"5000.00","quantity":1},{"name":"Item Two","price":"7000.00","quantity":1}],"account":{"id":"account-id-1790048625387","split_rule_id":"split-rule-id-1790048625387"},"channel":"DIRECT_DEBIT_ALLO_SNAP","successPaymentUrl":"https://www.google.com?q\\u003dsuccess-payment-url-1790048625387","failedPaymentUrl":"https://www.google.com?q\\u003dfailed-payment-url-1790048625387","origin":{"product":"CHECKOUT","source":"shopify","sourceVersion":"1.0.0","system":"devex-shopify-middle","apiFormat":"SNAP"},"paymentType":"SALE","any":{"addInfo1CamelCase":"Add Info 1 Camel Case.","add_info_2_snake_case":"Add Info 2 Snake Case."}}}
            2026-09-22 10:43:46.962 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.a.s.m.c.v.SafeStringValidator : value : DIRECT_DEBIT_ALLO_SNAP
            2026-09-22 10:43:46.962 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.a.s.m.c.v.SafeStringValidator : regex : ^[a-zA-Z0-9.\\-/+,=_:'@% ]*$
            2026-09-22 10:43:46.962 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.d.c.service.ServiceByDokuService : getById, id : DIRECT_DEBIT_ALLO_SNAP
            2026-09-22 10:43:46.965 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.a.s.m.c.v.SafeStringValidator : value : RID_PH2H_1790048625387
            2026-09-22 10:43:46.965 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.a.s.m.c.v.SafeStringValidator : regex : ^[a-zA-Z0-9.\\-/+,=_:'@% ]*$
            2026-09-22 10:43:46.965 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.a.s.m.c.v.SafeStringValidator : value : BRN-0203-1728283759342
            2026-09-22 10:43:46.965 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.a.s.m.c.v.SafeStringValidator : regex : ^[a-zA-Z0-9.\\-/+,=_:'@% ]*$
            2026-09-22 10:43:46.965 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.a.s.m.c.v.SafeStringValidator : value : Item One
            2026-09-22 10:43:46.965 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.a.s.m.c.v.SafeStringValidator : regex : ^[a-zA-Z0-9.\\-/+,=_:'@% ]*$
            2026-09-22 10:43:46.965 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.a.s.m.c.v.SafeStringValidator : value : Item Two
            2026-09-22 10:43:46.965 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.a.s.m.c.v.SafeStringValidator : regex : ^[a-zA-Z0-9.\\-/+,=_:'@% ]*$
            2026-09-22 10:43:46.966 INFO [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.d.c.m.c.service.PaymentCoreService : Processing Payment Request, invoice number: INVALLO1790048625387
            2026-09-22 10:43:46.966 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.d.c.service.MerchantAcquirerService : getByClientIdAndAcquirerIdAndApiVersion, clientId : BRN-0203-1728283759342, acquirerId : ALLO, apiVersion : SNAP
            2026-09-22 10:43:46.967 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.d.c.service.TransactionService : checkDuplicateRequestIdTransaction....
            2026-09-22 10:43:46.967 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.d.c.service.TransactionService : getTransactionByMerchantAcquirerAndRequestId, merchantAcquirer : 369, requestId : RID_PH2H_1790048625387
            2026-09-22 10:43:46.968 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.d.c.service.TransactionService : transactionService.validateDuplicateJourneyId, merchantAcquirer :com.doku.dd.coredirectdebit.entity.MerchantAcquirer@7e7b007c, journeyId : 09104345
            2026-09-22 10:43:46.972 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.d.c.common.AuthorizationUtils : encodedCustomerToken : Bearer eyJhbGciOiJSUzI1NiJ9.eyJleHAiOjE3OTEzNDQzOTIsImlzcyI6IkRPS1UiLCJjbGllbnRJZCI6IkJSTi0wMjAzLTE3MjgyODM3NTkzNDIiLCJhY2NvdW50SWQiOiJjMGZjZDE0MTk3OWM3YTM3MTk4OWYxNDdhYmI3OWJjYyJ9.XHQqDRrJTa6V6rYGnOrsld3cINpjXbYHHFxhFEpEGIk4L_EqmPhqdSpqFtKib9waIpRTzYt443gtAnRDTVnweb_VDbsPQW20N-9PfJSI0WKGB9mE901P_6IyhfBl0yU3Rw2RrtYn48eQBA3kcWHz-0yX9QHMQzVQCqUstLrz--NM9Ffj5iF45FxrmF6uEt0wq5KeEnUTHAXZPTqAloErt_tfXKvtgcnqxMjoyYQd_z-XVXs3RJOCi9Say99tXDmm1rtgNiFp7SOthcUVkEuMJw0U5Zv9D6-kawMGLCFkS9Y0hUDpKWqwS8l4ofwE-sSrspyF39LOKgS-GIO6Xc3w6Q
            2026-09-22 10:43:46.972 INFO [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.d.c.common.AuthorizationUtils : splitToken[1] : eyJleHAiOjE3OTEzNDQzOTIsImlzcyI6IkRPS1UiLCJjbGllbnRJZCI6IkJSTi0wMjAzLTE3MjgyODM3NTkzNDIiLCJhY2NvdW50SWQiOiJjMGZjZDE0MTk3OWM3YTM3MTk4OWYxNDdhYmI3OWJjYyJ9
            2026-09-22 10:43:46.972 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.d.c.common.AuthorizationUtils : decodedCustomerToken : {"exp":1791344392,"iss":"DOKU","clientId":"BRN-0203-1728283759342","accountId":"c0fcd141979c7a371989f147abb79bcc"}
            2026-09-22 10:43:46.973 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.d.c.service.TokenService : getActiveTokenByMerchantAcquirerAndToken, merchantAcquirer : 369, token : c0fcd141979c7a371989f147abb79bcc
            2026-09-22 10:43:46.980 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.d.c.service.AcquirerTokenService : updateDokuB2b2cToken....
            2026-09-22 10:43:46.982 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.d.c.service.AcquirerTokenService : save acquirerToken, id : 846
            2026-09-22 10:43:46.982 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.d.c.service.TransactionService : checkDuplicateInvoice, merchantAcquirer : 369, invoice : INVALLO1790048625387
            2026-09-22 10:43:46.986 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.d.c.service.TransactionService : getTransactionByMerchantAcquirerAndRequestId, merchantAcquirer : 369, requestId : RID_PH2H_1790048625387
            2026-09-22 10:43:46.986 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] .d.c.s.MerchantAcquirerIdentifierService : getMerchantAcquirerIdentifiers, merchantAcquirer : 369
            2026-09-22 10:43:46.987 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] .d.c.s.MerchantAcquirerIdentifierService : getMerchantAcquirerIdentifierByMerchantAcquirer, merchantAcquirer : 369
            2026-09-22 10:43:46.988 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.d.c.s.module.DirectDebitService : createTransactionPayment....
            2026-09-22 10:43:46.988 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.d.c.s.module.RiskEngineService : generateRiskEngineRequestTransaction, activityId : 1
            2026-09-22 10:43:46.989 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.d.c.s.module.RiskEngineService : riskEngineRequest RiskEngineRequest(partnerId=14, sharedKey=GTg96z9Lj0, activityId=1, merchantId=0, paymentChannelId=10, words=983AAC6D31222466858A6C2253136CB75A0BCF60, storeId=BRN-0203-1728283759342, customerId=KZR001_1, customerName=anomymous, customerEmail=john.doe@doku.com, customerMobilePhone=6281122588381, cardNumber=null, cardExpiryDate=null, transactionId=RID_PH2H_1790048625387, originalTransactionId=null, invoiceNumber=INVALLO1790048625387, currency=IDR, amount=12000.00, acquirerBank=ALLO)
            2026-09-22 10:43:46.989 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.d.c.s.module.RiskEngineService : riskEngineDecisionSelector....
            2026-09-22 10:43:46.989 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.d.c.common.ConnectionUtils : rest template for host : api-uat.doku.com is : restTemplateProxyShort
            2026-09-22 10:43:46.989 INFO [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,a96e46bebbea5741] 7 --- [nio-8080-exec-9] c.d.d.c.i.LoggingClientInterceptor : ================== CLIENT HTTP REQUEST START ==================
            2026-09-22 10:43:46.989 INFO [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,a96e46bebbea5741] 7 --- [nio-8080-exec-9] c.d.d.c.i.LoggingClientInterceptor : Request URL : POST https://api-uat.doku.com/DRE/RequestDecision
            2026-09-22 10:43:46.989 INFO [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,a96e46bebbea5741] 7 --- [nio-8080-exec-9] c.d.d.c.i.LoggingClientInterceptor : Request body : PARTNER_ID=14&SHARED_KEY=GTg96z9Lj0&ACTIVITY_ID=1&MERCHANT_ID=0&PAYMENT_CHANNEL_ID=10&WORDS=983AAC6D31222466858A6C2253136CB75A0BCF60&STOREID=BRN-0203-1728283759342&CUSTOMER_ID=KZR001_1&CUSTOMER_NAME=anomymous&CUSTOMER_EMAIL=john.doe%40doku.com&CUSTOMER_MOBILE_PHONE=6281122588381&TRANSACTION_ID=RID_PH2H_1790048625387&INVOICE_NUMBER=INVALLO1790048625387&CURRENCY=IDR&AMOUNT=12000.00&ACQUIRER_BANK=ALLO
            2026-09-22 10:43:46.989 INFO [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,a96e46bebbea5741] 7 --- [nio-8080-exec-9] c.d.d.c.i.LoggingClientInterceptor : Request Header : [Accept:"text/plain, application/json, application/*+json, */*", Content-Type:"application/x-www-form-urlencoded;charset=UTF-8", Content-Length:"401", X-B3-TraceId:"6ab1f972f1193ca5d9041d60cbdebd54", X-B3-SpanId:"a96e46bebbea5741", X-B3-ParentSpanId:"382c17b09330b2e9", X-B3-Sampled:"1"]
            2026-09-22 10:43:47.012 INFO [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,a96e46bebbea5741] 7 --- [nio-8080-exec-9] c.d.d.c.i.LoggingClientInterceptor : Response status : 500 - Internal Server Error
            2026-09-22 10:43:47.013 INFO [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,a96e46bebbea5741] 7 --- [nio-8080-exec-9] c.d.d.c.i.LoggingClientInterceptor : Response body : {
            "error": {
            "message": "Internal DOKU Server error, please contact our team",
            "type": "api_error"
            }
            }
            2026-09-22 10:43:47.013 INFO [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,a96e46bebbea5741] 7 --- [nio-8080-exec-9] c.d.d.c.i.LoggingClientInterceptor : Response header : [Date:"Tue, 22 Sep 2026 03:43:47 GMT", Content-Type:"application/json", Content-Length:"114", Connection:"keep-alive", Vary:"Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers"]
            2026-09-22 10:43:47.013 INFO [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,a96e46bebbea5741] 7 --- [nio-8080-exec-9] c.d.d.c.i.LoggingClientInterceptor : ================== CLIENT HTTP REQUEST FINISH =================
            2026-09-22 10:43:47.013 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.d.c.m.c.service.PaymentCoreService : Payment DTO V2: {"acquirer":{"id":"ALLO","apiVersion":"SNAP","credentials":{"consumerSecret":"1728968112742","merchantId":"1728968112742","consumerKey":"1728968112742"},"channelId":"DIRECT_DEBIT_ALLO_SNAP"},"client":{"id":"BRN-0203-1728283759342","originalExternalId":"RID_PH2H_1790048625387","name":"Merchant Khanza","businessAddress":{"data":"Senayan, Kebayoran Baru, Jakarta Selatan, DKI Jakarta","dataSource":"EXTERNAL","postalCode":"12190","fullAddress":"Jalan Dummy"},"origin":{"product":"CHECKOUT","source":"shopify","sourceVersion":"1.0.0","system":"devex-shopify-middle","apiFormat":"SNAP"}},"account":{"number":"6281122588381","token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb2RlaGFzaCI6Ik5HRTJZMkpqTnpVellqZzBORGRpWm1KbFpUTTVaRFkyWWpBd00yTTFaV0kiLCJyYW5kb20iOiJNVEV4T1RBMk1qZyIsInZlcnNpb24iOjF9.SBcV_kUwGEStGNMMKjcWLYqJ0ZR4YEzK3vOnuPX1Jw8","refreshToken":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb2RlaGFzaCI6Ik9HVmpZVGRrTXpVeE5UVm1OR1ExTUdGaU56TmxOakEwTm1ZeVpHWmtaRGciLCJyYW5kb20iOiJNVEV4T1RBMk1qYyIsInZlcnNpb24iOjF9.j-NuuQtvSdEX4G_VucJZRuopfIlHBBzX14nezP9arD4","tokenExpired":1879589029,"name":"","additionalInfo":{"lineItems":[{"name":"Item One","price":"5000.00","quantity":1},{"name":"Item Two","price":"7000.00","quantity":1}],"account":{"id":"account-id-1790048625387","split_rule_id":"split-rule-id-1790048625387"},"channel":"DIRECT_DEBIT_ALLO_SNAP","successPaymentUrl":"https://www.google.com?q\\u003dsuccess-payment-url-1790048625387","failedPaymentUrl":"https://www.google.com?q\\u003dfailed-payment-url-1790048625387","origin":{"product":"CHECKOUT","source":"shopify","sourceVersion":"1.0.0","system":"devex-shopify-middle","apiFormat":"SNAP"},"paymentType":"SALE","addInfo1CamelCase":"Add Info 1 Camel Case.","add_info_2_snake_case":"Add Info 2 Snake Case.","serviceType":"H2H"},"dokuB2b2cToken":"Bearer eyJhbGciOiJSUzI1NiJ9.eyJleHAiOjE3OTEzNDQzOTIsImlzcyI6IkRPS1UiLCJjbGllbnRJZCI6IkJSTi0wMjAzLTE3MjgyODM3NTkzNDIiLCJhY2NvdW50SWQiOiJjMGZjZDE0MTk3OWM3YTM3MTk4OWYxNDdhYmI3OWJjYyJ9.XHQqDRrJTa6V6rYGnOrsld3cINpjXbYHHFxhFEpEGIk4L_EqmPhqdSpqFtKib9waIpRTzYt443gtAnRDTVnweb_VDbsPQW20N-9PfJSI0WKGB9mE901P_6IyhfBl0yU3Rw2RrtYn48eQBA3kcWHz-0yX9QHMQzVQCqUstLrz--NM9Ffj5iF45FxrmF6uEt0wq5KeEnUTHAXZPTqAloErt_tfXKvtgcnqxMjoyYQd_z-XVXs3RJOCi9Say99tXDmm1rtgNiFp7SOthcUVkEuMJw0U5Zv9D6-kawMGLCFkS9Y0hUDpKWqwS8l4ofwE-sSrspyF39LOKgS-GIO6Xc3w6Q"},"customer":{"id":"KZR001_1","name":"","postalCode":"null","isBindAndPay":""},"transaction":{"amount":"12000.00","currency":"IDR","invoice":"INVALLO1790048625387","uuid":"2238260922104346988107170160480001712405","lineItems":[{"name":"Item One","price":"5000.00","quantity":1},{"name":"Item Two","price":"7000.00","quantity":1}],"transactionType":"SALE","sourceProduct":"shopify","journeyId":"09104345"},"additionalInfo":{"payOptionDetails":[{"payMethod":"BALANCE","transAmount":{"value":"72500.00","currency":"IDR"}}],"transactionType":"SALE","serviceType":"H2H","any":{"addInfo1CamelCase":"Add Info 1 Camel Case.","add_info_2_snake_case":"Add Info 2 Snake Case."},"requestBody":{"partnerId":"BRN-0203-1728283759342","externalId":"RID_PH2H_1790048625387","bearerCustomer":"Bearer eyJhbGciOiJSUzI1NiJ9.eyJleHAiOjE3OTEzNDQzOTIsImlzcyI6IkRPS1UiLCJjbGllbnRJZCI6IkJSTi0wMjAzLTE3MjgyODM3NTkzNDIiLCJhY2NvdW50SWQiOiJjMGZjZDE0MTk3OWM3YTM3MTk4OWYxNDdhYmI3OWJjYyJ9.XHQqDRrJTa6V6rYGnOrsld3cINpjXbYHHFxhFEpEGIk4L_EqmPhqdSpqFtKib9waIpRTzYt443gtAnRDTVnweb_VDbsPQW20N-9PfJSI0WKGB9mE901P_6IyhfBl0yU3Rw2RrtYn48eQBA3kcWHz-0yX9QHMQzVQCqUstLrz--NM9Ffj5iF45FxrmF6uEt0wq5KeEnUTHAXZPTqAloErt_tfXKvtgcnqxMjoyYQd_z-XVXs3RJOCi9Say99tXDmm1rtgNiFp7SOthcUVkEuMJw0U5Zv9D6-kawMGLCFkS9Y0hUDpKWqwS8l4ofwE-sSrspyF39LOKgS-GIO6Xc3w6Q","serviceType":"H2H","partnerReferenceNo":"INVALLO1790048625387","journeyId":"09104345","amount":{"value":"12000.00","currency":"IDR"},"payOptionDetails":[{"payMethod":"BALANCE","transAmount":{"value":"72500.00","currency":"IDR"}}],"timestamp":"2026-09-22T10:43:45+07:00","signature":"NM1fVlXJIs523/tXWkCGX4+xvV+Yr0hGXy8R0Ms4vbGh8aa2z6mod6k/Abvz6WpXmA1TAaGVjbfHnbXpwS5wBg\\u003d\\u003d","deviceId":"912f12e223624d2e94828b168199asdf","ipAddress":"127.1.1.12","authorization":"Bearer eyJhbGciOiJSUzI1NiJ9.eyJleHAiOjE3OTAwNDkyODcsImlzcyI6IkRPS1UiLCJjbGllbnRJZCI6IkJSTi0wMjAzLTE3MjgyODM3NTkzNDIifQ.JdPd7tkyd_vnF6yBv9vz8FIephKSYVNB09AWZm62tyW4jqDzA8qVG7fA89v_2BKlQO2Y5dWN0SN0u6UlnMVy71373HGN828eXsZ0MdxhmKq8xzp7W6HUIF4wxXiv3-6sDwrmSMScgKOsvBZLkxpo-6BfU9wWKQb0NniRKdhEufXTFsYN4ZZhoDhLFUbQziLgQ9s3MHdjKqArMY7QmYM0YwmDL_MBvfIwihQPqhc8kmQ3BzdBZJL_fx5Yz3A7ceubRm9wVTqkq2CBqQc7h8G_4ShKz5hKHyslMz4cp9AON-7YXl28FPRq3DmFtsG3BOUGFQXfoQr-VUANhNEPY3YxTQ","additionalInfo":{"lineItems":[{"name":"Item One","price":"5000.00","quantity":1},{"name":"Item Two","price":"7000.00","quantity":1}],"account":{"id":"account-id-1790048625387","split_rule_id":"split-rule-id-1790048625387"},"channel":"DIRECT_DEBIT_ALLO_SNAP","successPaymentUrl":"https://www.google.com?q\\u003dsuccess-payment-url-1790048625387","failedPaymentUrl":"https://www.google.com?q\\u003dfailed-payment-url-1790048625387","origin":{"product":"CHECKOUT","source":"shopify","sourceVersion":"1.0.0","system":"devex-shopify-middle","apiFormat":"SNAP"},"paymentType":"SALE","any":{"addInfo1CamelCase":"Add Info 1 Camel Case.","add_info_2_snake_case":"Add Info 2 Snake Case."}}}},"device":{"ipAddress":"127.1.1.12","deviceId":"912f12e223624d2e94828b168199asdf","osType":"ios"}}
            2026-09-22 10:43:47.013 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.d.c.m.b.s.snap.AlloSnapService : ALLO SNAP payment....
            2026-09-22 10:43:47.013 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.d.c.m.b.s.snap.AlloSnapService : ALLO SNAP getAccessTokenB2B2C....
            2026-09-22 10:43:47.013 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.a.s.m.encryption.EncryptionService : message : 0AS6MokF0PnVli11uC2y7dk9kOdoJUNSjioJpqT6Vcs=|DEV-AA|2|2|YoVjj71ArPzhnHFLg15z8A==
            2026-09-22 10:43:47.013 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.a.s.m.encryption.EncryptionService : message length : 5
            2026-09-22 10:43:47.013 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.a.s.m.encryption.EncryptionService : iv base64 : [YoVjj71ArPzhnHFLg15z8A==]
            2026-09-22 10:43:47.013 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.a.s.m.encryption.EncryptionService : using iv from chipertext: YoVjj71ArPzhnHFLg15z8A==
            2026-09-22 10:43:47.013 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.a.s.m.encryption.EncryptionService : decodedIV: YoVjj71ArPzhnHFLg15z8A==
            2026-09-22 10:43:47.013 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.a.s.m.encryption.EncryptionService : dataType : 2
            2026-09-22 10:43:47.014 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.a.s.m.encryption.EncryptionService : is usingHsm : false
            2026-09-22 10:43:47.014 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.d.c.m.b.s.snap.AlloSnapService : ALLO SNAP executeToAcquirer....
            2026-09-22 10:43:47.014 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.a.s.m.encryption.EncryptionService : message : 0AS6MokF0PnVli11uC2y7dk9kOdoJUNSjioJpqT6Vcs=|DEV-AA|2|2|YoVjj71ArPzhnHFLg15z8A==
            2026-09-22 10:43:47.014 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.a.s.m.encryption.EncryptionService : message length : 5
            2026-09-22 10:43:47.014 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.a.s.m.encryption.EncryptionService : iv base64 : [YoVjj71ArPzhnHFLg15z8A==]
            2026-09-22 10:43:47.014 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.a.s.m.encryption.EncryptionService : using iv from chipertext: YoVjj71ArPzhnHFLg15z8A==
            2026-09-22 10:43:47.014 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.a.s.m.encryption.EncryptionService : decodedIV: YoVjj71ArPzhnHFLg15z8A==
            2026-09-22 10:43:47.014 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.a.s.m.encryption.EncryptionService : dataType : 2
            2026-09-22 10:43:47.014 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.a.s.m.encryption.EncryptionService : is usingHsm : false
            2026-09-22 10:43:47.015 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] com.doku.au.security.common.HashTool : Expected component json body (payload component):
            {"partnerReferenceNo":"yWlcKkoeTB2lQ80HrfY5HM0l34LCkszz","merchantId":"1728968112742","amount":{"value":"12000.00","currency":"IDR"},"urlParam":[{"url":"https://api-uat.doku.com/direct-debit/ui/callback/allo/payment/2238260922104346988107170160480001712405","type":"PAY_NOTIFY","isDeeplink":"N"}],"payOptionDetails":[{"payMethod":"00","transAmount":{"value":"72500.00.00","currency":"IDR"}}],"additionalInfo":{"orderNo":"yWlcKkoeTB2lQ80HrfY5HM0l34LCkszz","msisdnGlobalRoaming":"+62","merchantUserId":"KZR001_1","msisdn":"81122588381","deviceInfo":{"isRoot":"false","isJbk":"false","ip":"127.1.1.12","isVpn":"false","osType":"ios","deviceId":"912f12e223624d2e94828b168199asdf"},"goodsInfo":[{"goodsId":"Item One","goodsPrice":"5000.00","goodsNum":"1"},{"goodsId":"Item Two","goodsPrice":"7000.00","goodsNum":"1"}]}}
            2026-09-22 10:43:47.015 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.d.c.common.ConnectionUtils : rest template for host : direct-debit-simulator.jokul-direct-debit-uat.svc is : restTemplateLongInternal
            2026-09-22 10:43:47.016 INFO [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,9b6abf6d5901543d] 7 --- [nio-8080-exec-9] c.d.d.c.i.LoggingClientInterceptor : ================== CLIENT HTTP REQUEST START ==================
            2026-09-22 10:43:47.016 INFO [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,9b6abf6d5901543d] 7 --- [nio-8080-exec-9] c.d.d.c.i.LoggingClientInterceptor : Request URL : POST http://direct-debit-simulator.jokul-direct-debit-uat.svc:8080/direct-debit/simulator/sapi/v3.0/debit/payment-host-to-host
            2026-09-22 10:43:47.016 INFO [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,9b6abf6d5901543d] 7 --- [nio-8080-exec-9] c.d.d.c.i.LoggingClientInterceptor : Request body : {"partnerReferenceNo":"yWlcKkoeTB2lQ80HrfY5HM0l34LCkszz","merchantId":"1728968112742","amount":{"value":"12000.00","currency":"IDR"},"urlParam":[{"url":"https://api-uat.doku.com/direct-debit/ui/callback/allo/payment/2238260922104346988107170160480001712405","type":"PAY_NOTIFY","isDeeplink":"N"}],"payOptionDetails":[{"payMethod":"00","transAmount":{"value":"72500.00.00","currency":"IDR"}}],"additionalInfo":{"orderNo":"yWlcKkoeTB2lQ80HrfY5HM0l34LCkszz","msisdnGlobalRoaming":"+62","merchantUserId":"KZR001_1","msisdn":"81122588381","deviceInfo":{"isRoot":"false","isJbk":"false","ip":"127.1.1.12","isVpn":"false","osType":"ios","deviceId":"912f12e223624d2e94828b168199asdf"},"goodsInfo":[{"goodsId":"Item One","goodsPrice":"5000.00","goodsNum":"1"},{"goodsId":"Item Two","goodsPrice":"7000.00","goodsNum":"1"}]}}
            2026-09-22 10:43:47.016 INFO [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,9b6abf6d5901543d] 7 --- [nio-8080-exec-9] c.d.d.c.i.LoggingClientInterceptor : Request Header : [Accept:"application/json", Content-Type:"application/json", Authorization:"Bearer AAIgMDZmMzdmZmQyZmVjMjEzZWQzMzNhM2IzYmMyMTY3Mzlog_MDnu2Gk_vtz7PpHDvgfORvD7H0cd5qaF_qU46qkmhXi66Az7rXWhkotuXxwniy", Authorization-Customer:"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb2RlaGFzaCI6Ik5HRTJZMkpqTnpVellqZzBORGRpWm1KbFpUTTVaRFkyWWpBd00yTTFaV0kiLCJyYW5kb20iOiJNVEV4T1RBMk1qZyIsInZlcnNpb24iOjF9.SBcV_kUwGEStGNMMKjcWLYqJ0ZR4YEzK3vOnuPX1Jw8", X-PARTNER-ID:"1728968112742", X-EXTERNAL-ID:"1790048627015703599", X-TIMESTAMP:"2026-09-22T10:43:47+07:00", X-SIGNATURE:"915832d040f3bee6adc3179fdafcc9b80bd6c8832c60b94fab320919b3d030c8d3c9d94d7edde7d00c07adf1691af81e6f52de9a205983d758364f974bdd37fd", CHANNEL-ID:"95221", ORIGIN:"https://uatopenapi.ctcorpmpc.com/", X-IP-ADDRESS:"127.1.1.12", X-DEVICE-ID:"912f12e223624d2e94828b168199asdf", Content-Length:"814", X-B3-TraceId:"6ab1f972f1193ca5d9041d60cbdebd54", X-B3-SpanId:"9b6abf6d5901543d", X-B3-ParentSpanId:"382c17b09330b2e9", X-B3-Sampled:"1"]
            2026-09-22 10:43:47.031 INFO [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,9b6abf6d5901543d] 7 --- [nio-8080-exec-9] c.d.d.c.i.LoggingClientInterceptor : Response status : 200 - OK
            2026-09-22 10:43:47.031 INFO [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,9b6abf6d5901543d] 7 --- [nio-8080-exec-9] c.d.d.c.i.LoggingClientInterceptor : Response body : {"responseCode":"2005400","responseMessage":"Successful","partnerReferenceNo":"yWlcKkoeTB2lQ80HrfY5HM0l34LCkszz","referenceNo":"jHNBVyrAqXkB6llj","appRedirectUrl":"https://api-uat.doku.com/direct-debit-simulator-ui/allo/payment?&amount=12000.00&destination=https://api-uat.doku.com/direct-debit/ui/callback/allo/payment/2238260922104346988107170160480001712405","additionalInfo":{}}
            2026-09-22 10:43:47.031 INFO [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,9b6abf6d5901543d] 7 --- [nio-8080-exec-9] c.d.d.c.i.LoggingClientInterceptor : Response header : [Content-Type:"application/json", Transfer-Encoding:"chunked", Date:"Tue, 22 Sep 2026 03:43:47 GMT", Keep-Alive:"timeout=60", Connection:"keep-alive"]
            2026-09-22 10:43:47.031 INFO [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,9b6abf6d5901543d] 7 --- [nio-8080-exec-9] c.d.d.c.i.LoggingClientInterceptor : ================== CLIENT HTTP REQUEST FINISH =================
            2026-09-22 10:43:47.032 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.d.c.service.AcquirerTokenService : updateAcquirerAccessToken....
            2026-09-22 10:43:47.038 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.d.c.service.AcquirerTokenService : save acquirerToken, id : 846
            2026-09-22 10:43:47.041 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.d.c.service.TransactionService : save transaction, transaction id : 16547
            2026-09-22 10:43:47.041 INFO [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.d.c.service.kafka.KafkaService : publishCardTransaction....
            2026-09-22 10:43:47.042 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.a.s.module.http.ResponseAdvice : target path : /direct-debit/core/v1/debit/payment-host-to-host
            2026-09-22 10:43:47.042 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,382c17b09330b2e9] 7 --- [nio-8080-exec-9] c.d.a.s.module.http.ResponseAdvice : context-path :
            2026-09-22 10:43:47.042 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,af8192e03acd900d] 7 --- [ async-19] .d.d.c.s.k.PublishCardTransactionService : publishCardTransaction, action : ACTION_PAYMENT
            2026-09-22 10:43:47.042 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,af8192e03acd900d] 7 --- [ async-19] c.d.d.c.service.kafka.KafkaService : createJdm....
            2026-09-22 10:43:47.042 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,af8192e03acd900d] 7 --- [ async-19] c.d.d.c.service.kafka.KafkaService : init time : 2024-10-15T12:02:31.453970+07:00[Asia/Jakarta]
            2026-09-22 10:43:47.042 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,af8192e03acd900d] 7 --- [ async-19] c.d.d.c.service.kafka.KafkaService : client time :2026-09-22T10:43:47.042415510+07:00[Asia/Jakarta]
            2026-09-22 10:43:47.042 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,af8192e03acd900d] 7 --- [ async-19] c.d.d.c.service.MerchantService : getMerchantByClientId, clientId : MCH-0001-2768422848440
            2026-09-22 10:43:47.043 INFO [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,af8192e03acd900d] 7 --- [ async-19] c.d.d.c.common.kafka.KafkaPublisher : Sending Kafka to topic : uat-streaming-card-transaction, message : {"businessService":{"service":{"id":"DIRECT_DEBIT","name":"Direct Debit"},"partner":{"id":"MCH-0001-2768422848440","name":"OVO tkn V2"},"client":{"id":"BRN-0203-1728283759342","name":"Merchant Khanza","status":"ACTIVE","apiVersion":"3.0"}},"acquirer":{"id":"ALLO","name":"AlloBank SNAP Direct Debit","apiVersion":"3.0"},"channel":{"id":"DIRECT_DEBIT_ALLO","name":"DIRECT DEBIT ALLO"},"order":{"invoiceNumber":"INVALLO1790048625387","lineItems":[{"name":"Item One","price":"5000.00","quantity":1.0},{"name":"Item Two","price":"7000.00","quantity":1.0}],"currency":"IDR","amount":12000.00,"createdDate":"2026-09-22T03:43:46Z"},"directDebitConfiguration":{"status":"ACTIVE"},"customer":{"id":"KZR001_1","name":"anomymous","email":"john.doe@doku.com","phone":"6281122588381","idCard":"12345","country":"Indonesia","address":"Bali","dateOfBirth":"01-01-1999"},"cardTransaction":{"type":"SALE","requestId":"RID_PH2H_1790048625387","cardMasked":"****8381","responseCode":"2005400","responseMessage":"Successful","paymentId":"jHNBVyrAqXkB6llj","transactionStatus":"PENDING","date":"2026-09-22T03:43:46Z","token":"c0fcd141979c7a371989f147abb79bcc"},"transaction":{"date":"2026-09-22T03:43:46Z","status":"PENDING","serviceCode":"54","originalRequestId":"RID_PH2H_1790048625387","originalPartnerReferenceNo":"INVALLO1790048625387","originalReferenceNo":"jHNBVyrAqXkB6llj","originalExternalId":"RID_PH2H_1790048625387"},"jdm":{"uuid":"2238260922104346988107170160480001712405","divisionName":"PAYMENT_PLATFORM","departmentName":"WALLET_TOKEN_DEBIT","databaseName":"direct_debit_core","clientCreationTime":"2026-09-22T10:43:47.042415510+07:00[Asia/Jakarta]","journeyId":"pbl-direct-debit-payment","journeyCode":"PBL_CARD_0001","journeyDesc":"Direct Debit create payment process"},"additionalInfo":{"lineItems":[{"name":"Item One","price":"5000.00","quantity":1},{"name":"Item Two","price":"7000.00","quantity":1}],"serviceType":"H2H","addInfo1CamelCase":"Add Info 1 Camel Case.","failedPaymentUrl":"https://www.google.com?q\\u003dfailed-payment-url-1790048625387","origin":{"product":"CHECKOUT","source":"shopify","sourceVersion":"1.0.0","system":"devex-shopify-middle","apiFormat":"SNAP"},"channel":"DIRECT_DEBIT_ALLO_SNAP","successPaymentUrl":"https://www.google.com?q\\u003dsuccess-payment-url-1790048625387","addInfo2SnakeCase":"Add Info 2 Snake Case.","account":{"id":"account-id-1790048625387","split_rule_id":"split-rule-id-1790048625387"},"paymentType":"SALE"},"screening":{"status":"","type":"DECISION","reason":""}}
            2026-09-22 10:43:47.045 DEBUG [direct-debit-core-system,6ab1f972f1193ca5d9041d60cbdebd54,af8192e03acd900d] 7 --- [ async-19] c.d.d.c.common.kafka.KafkaPublisher : Send Result : SendResult [producerRecord=ProducerRecord(topic=uat-streaming-card-transaction, partition=null, headers=RecordHeaders(headers = [RecordHeader(key = __TypeId__, value = [99, 111, 109, 46, 100, 111, 107, 117, 46, 100, 100, 46, 99, 111, 114, 101, 100, 105, 114, 101, 99, 116, 100, 101, 98, 105, 116, 46, 100, 116, 111, 46, 107, 97, 102, 107, 97, 46, 83, 116, 114, 101, 97, 109, 105, 110, 103, 67, 97, 114, 100, 84, 114, 97, 110, 115, 97, 99, 116, 105, 111, 110, 68, 116, 111])], isReadOnly = true), key=null, value=StreamingCardTransactionDto(businessService=StreamingCardTransactionDto.BusinessServiceDto(service=StreamingCardTransactionDto.ServiceDto(id=DIRECT_DEBIT, name=Direct Debit), partner=StreamingCardTransactionDto.PartnerDto(id=MCH-0001-2768422848440, name=OVO tkn V2), client=StreamingCardTransactionDto.ClientDto(id=BRN-0203-1728283759342, name=Merchant Khanza, sharedKey=null, sharedKeyEnc=null, status=ACTIVE, apiVersion=3.0)), acquirer=StreamingCardTransactionDto.AcquirerDto(id=ALLO, name=AlloBank SNAP Direct Debit, apiVersion=3.0, nns=null), channel=StreamingCardTransactionDto.ChannelDto(id=DIRECT_DEBIT_ALLO, name=DIRECT DEBIT ALLO), order=StreamingCardTransactionDto.OrderDto(invoiceNumber=INVALLO1790048625387, lineItems=[{name=Item One, price=5000.00, quantity=1.0}, {name=Item Two, price=7000.00, quantity=1.0}], currency=IDR, amount=12000.00, createdDate=2026-09-22T03:43:46Z), refund=null, directDebitConfiguration=StreamingCardTransactionDto.DirectDebitConfigurationDto(status=ACTIVE), customer=StreamingCardTransactionDto.CustomerDto(id=KZR001_1, name=anomymous, email=john.doe@doku.com, phone=6281122588381, idCard=12345, country=Indonesia, address=Bali, dateOfBirth=01-01-1999, additionalInfo=null), cardTransaction=StreamingCardTransactionDto.CardTransactionDto(type=SALE, requestId=RID_PH2H_1790048625387, cardMasked=****8381, responseCode=2005400, responseMessage=Successful, paymentId=jHNBVyrAqXkB6llj, transactionStatus=PENDING, date=2026-09-22T03:43:46Z, token=c0fcd141979c7a371989f147abb79bcc, acquiringOffUsStatus=null, installmentTenor=null, planId=null, issuer=null, approvalCode=null, brand=null, cardType=null, transactionErrorMessage=null, threeDSecureStatus=null, authCode=null, settlementDate=null, settlementStatus=null, authorizeId=null, cardNumber=null, cardExpired=null, tokenId=null, batchNumber=null, authenticationId=null, encryptedCardNumber=null, expiryDate=null, identifier=null, referenceNo=null), transaction=StreamingCardTransactionDto.TransactionDto(date=2026-09-22T03:43:46Z, status=PENDING, serviceCode=54, originalRequestId=RID_PH2H_1790048625387, originalPartnerReferenceNo=INVALLO1790048625387, originalReferenceNo=jHNBVyrAqXkB6llj, originalExternalId=RID_PH2H_1790048625387, accountMasked=null, type=null, amount=null, issuerId=null, category=null, issuerNns=null, acquirerNns=null, acquirerId=null, acquirerName=null, gatewayRefNum=null, amountDetails=null, partnerRefNum=null, responseCode=null, responseMessage=null, approvalCode=null, gatewayToken=null, mpan=null, issuerName=null), jdm=JdmDto(uuid=2238260922104346988107170160480001712405, parentUuid=null, batchUuid=null, inquiryUuid=null, registerUuid=null, activities=null, divisionName=PAYMENT_PLATFORM, departmentName=WALLET_TOKEN_DEBIT, databaseName=direct_debit_core, clientCreationTime=2026-09-22T10:43:47.042415510+07:00[Asia/Jakarta], journeyId=pbl-direct-debit-payment, journeyCode=PBL_CARD_0001, journeyDesc=Direct Debit create payment process, originalUuid=null), dispute=null, additionalInfo={lineItems=[{name=Item One, price=5000.00, quantity=1}, {name=Item Two, price=7000.00, quantity=1}], serviceType=H2H, addInfo1CamelCase=Add Info 1 Camel Case., failedPaymentUrl=https://www.google.com?q=failed-payment-url-1790048625387, origin=OriginDto(product=CHECKOUT, source=shopify, sourceVersion=1.0.0, system=devex-shopify-middle, apiFormat=SNAP, standardApiVersion=null, format=null, apiVersion=null), channel=DIRECT_DEBIT_ALLO_SNAP, successPaymentUrl=https://www.google.com?q=success-payment-url-1790048625387, addInfo2SnakeCase=Add Info 2 Snake Case., account={id=account-id-1790048625387, split_rule_id=split-rule-id-1790048625387}, paymentType=SALE}, verification=null, origin=null, screening=StreamingCardTransactionDto.ScreeningDto(status=, type=DECISION, reason=)), timestamp=null), recordMetadata=uat-streaming-card-transaction-0@321955]
            2026-09-22 10:43:49.450 DEBUG [direct-debit-core-system,6ab1f975c4fd133eadb36e8a216fc13a,5a6fff373e1d6d8a] 7 --- [io-8080-exec-10] c.d.a.s.module.http.RequestHeaderFilter : servlet path : /direct-debit/core/session/payment/2238260922104346988107170160480001712405
            2026-09-22 10:43:49.450 DEBUG [direct-debit-core-system,6ab1f975c4fd133eadb36e8a216fc13a,5a6fff373e1d6d8a] 7 --- [io-8080-exec-10] c.d.d.c.service.TransactionService : getTransactionByUuid, uuid : 2238260922104346988107170160480001712405
            2026-09-22 10:43:49.464 DEBUG [direct-debit-core-system,6ab1f975c4fd133eadb36e8a216fc13a,5a6fff373e1d6d8a] 7 --- [io-8080-exec-10] c.d.d.c.m.c.service.CallbackCoreService : Session Response GetSessionResponseDto(acquirer=GetSessionResponseDto.AcquirerDto(id=ALLO), channel=GetSessionResponseDto.ChannelDto(id=DIRECT_DEBIT_ALLO_SNAP), client=GetSessionResponseDto.ClientDto(id=BRN-0203-1728283759342), account=GetSessionResponseDto.AccountDto(type=DIRECT_DEBIT, issuer=ALLO, phoneNumber=6281122588381), info=GetSessionResponseDto.InfoDto(sessionId=null, journeyId=09104345, redirectUrl=https://api-uat.doku.com/direct-debit-simulator-ui/allo/payment?&amount=12000.00&destination=https://api-uat.doku.com/direct-debit/ui/callback/allo/payment/2238260922104346988107170160480001712405, successRedirectUrl=https://www.google.com?q=success-payment-url-1790048625387, failedRedirectUrl=https://www.google.com?q=failed-payment-url-1790048625387, validUpTo=2026-09-22T03:48:46.988Z, isDeepLink=null, webRedirectUrl=null, stringQr=null, urlQr=null, deepLink=null, timeoutQr=null, origin=null, status=null))
            2026-09-22 10:43:49.464 DEBUG [direct-debit-core-system,6ab1f975c4fd133eadb36e8a216fc13a,5a6fff373e1d6d8a] 7 --- [io-8080-exec-10] c.d.a.s.module.http.ResponseAdvice : target path : /direct-debit/core/session/payment/2238260922104346988107170160480001712405
            2026-09-22 10:43:49.464 DEBUG [direct-debit-core-system,6ab1f975c4fd133eadb36e8a216fc13a,5a6fff373e1d6d8a] 7 --- [io-8080-exec-10] c.d.a.s.module.http.ResponseAdvice : context-path :
            2026-09-22 10:43:51.482 DEBUG [direct-debit-core-system,6ab1f9774d0e8f6527f537d00e0c4321,91c87b412a4d16bf] 7 --- [nio-8080-exec-2] c.d.a.s.module.http.RequestHeaderFilter : servlet path : /direct-debit/core/auth-code
            2026-09-22 10:43:51.492 INFO [direct-debit-core-system,6ab1f9774d0e8f6527f537d00e0c4321,91c87b412a4d16bf] 7 --- [nio-8080-exec-2] c.d.d.c.m.c.service.CallbackCoreService : callbackCoreService.getAuthCode....
            2026-09-22 10:43:51.492 DEBUG [direct-debit-core-system,6ab1f9774d0e8f6527f537d00e0c4321,91c87b412a4d16bf] 7 --- [nio-8080-exec-2] c.d.d.c.service.TransactionService : getTransactionByUuidAndStatus, uuid : 2238260922104346988107170160480001712405, transactionStatus : PENDING
            2026-09-22 10:43:51.503 DEBUG [direct-debit-core-system,6ab1f9774d0e8f6527f537d00e0c4321,91c87b412a4d16bf] 7 --- [nio-8080-exec-2] c.d.d.c.service.TransactionService : save transaction, transaction id : 16547
            2026-09-22 10:43:51.503 DEBUG [direct-debit-core-system,6ab1f9774d0e8f6527f537d00e0c4321,91c87b412a4d16bf] 7 --- [nio-8080-exec-2] .d.c.s.MerchantAcquirerIdentifierService : getMerchantAcquirerIdentifiers, merchantAcquirer : 369
            2026-09-22 10:43:51.503 DEBUG [direct-debit-core-system,6ab1f9774d0e8f6527f537d00e0c4321,91c87b412a4d16bf] 7 --- [nio-8080-exec-2] .d.c.s.MerchantAcquirerIdentifierService : getMerchantAcquirerIdentifierByMerchantAcquirer, merchantAcquirer : 369
            2026-09-22 10:43:51.513 DEBUG [direct-debit-core-system,6ab1f9774d0e8f6527f537d00e0c4321,91c87b412a4d16bf] 7 --- [nio-8080-exec-2] c.d.d.c.service.TransactionService : save transaction, transaction id : 16547
            2026-09-22 10:43:51.513 INFO [direct-debit-core-system,6ab1f9774d0e8f6527f537d00e0c4321,91c87b412a4d16bf] 7 --- [nio-8080-exec-2] c.d.d.c.service.kafka.KafkaService : publishNotificationTransactionSingleApi....
            2026-09-22 10:43:51.513 INFO [direct-debit-core-system,6ab1f9774d0e8f6527f537d00e0c4321,91c87b412a4d16bf] 7 --- [nio-8080-exec-2] c.d.d.c.service.kafka.KafkaService : publishCardTransaction....
            2026-09-22 10:43:51.514 DEBUG [direct-debit-core-system,6ab1f9774d0e8f6527f537d00e0c4321,91c87b412a4d16bf] 7 --- [nio-8080-exec-2] c.d.a.s.module.http.ResponseAdvice : target path : /direct-debit/core/auth-code
            2026-09-22 10:43:51.514 DEBUG [direct-debit-core-system,6ab1f9774d0e8f6527f537d00e0c4321,91c87b412a4d16bf] 7 --- [nio-8080-exec-2] c.d.a.s.module.http.ResponseAdvice : context-path :
            2026-09-22 10:43:51.514 DEBUG [direct-debit-core-system,6ab1f9774d0e8f6527f537d00e0c4321,32f26b5926ed82bf] 7 --- [ async-19] ublishNotificationHttpTransactionService : createMessageToPublish....
            2026-09-22 10:43:51.514 DEBUG [direct-debit-core-system,6ab1f9774d0e8f6527f537d00e0c4321,32f26b5926ed82bf] 7 --- [ async-19] ublishNotificationHttpTransactionService : createMessage....
            2026-09-22 10:43:51.514 DEBUG [direct-debit-core-system,6ab1f9774d0e8f6527f537d00e0c4321,32f26b5926ed82bf] 7 --- [ async-19] c.d.d.c.service.AcquirerService : getAcquirerById, id ALLO
            2026-09-22 10:43:51.514 DEBUG [direct-debit-core-system,6ab1f9774d0e8f6527f537d00e0c4321,32f26b5926ed82bf] 7 --- [ async-19] c.d.d.c.s.module.DirectDebitService : createMessage....
            2026-09-22 10:43:51.515 DEBUG [direct-debit-core-system,6ab1f9774d0e8f6527f537d00e0c4321,32f26b5926ed82bf] 7 --- [ async-19] c.d.d.c.service.kafka.KafkaService : createJdm....
            2026-09-22 10:43:51.515 DEBUG [direct-debit-core-system,6ab1f9774d0e8f6527f537d00e0c4321,32f26b5926ed82bf] 7 --- [ async-19] c.d.d.c.service.kafka.KafkaService : init time : 2024-10-15T12:02:31.453970+07:00[Asia/Jakarta]
            2026-09-22 10:43:51.515 DEBUG [direct-debit-core-system,6ab1f9774d0e8f6527f537d00e0c4321,32f26b5926ed82bf] 7 --- [ async-19] c.d.d.c.service.kafka.KafkaService : client time :2026-09-22T10:43:51.515073120+07:00[Asia/Jakarta]
            2026-09-22 10:43:51.515 INFO [direct-debit-core-system,6ab1f9774d0e8f6527f537d00e0c4321,32f26b5926ed82bf] 7 --- [ async-19] c.d.d.c.common.kafka.KafkaPublisher : Sending Kafka to topic : uat-streaming-notification-http, message : {"message":{"to":"https://app.beeceptor.com/console/notification-allo","request":{"method":"POST","parameter":{"originalPartnerReferenceNo":"INVALLO1790048625387","originalReferenceNo":"aQNRS3Z8JgzQc0VE","originalExternalId":"RID_PH2H_1790048625387","latestTransactionStatus":"00","transactionStatusDesc":"Success","amount":{"value":"12000.00","currency":"IDR"},"additionalInfo":{"addInfo1CamelCase":"Add Info 1 Camel Case.","failedPaymentUrl":"https://www.google.com?q\\u003dfailed-payment-url-1790048625387","origin":{"source":"shopify","system":"devex-shopify-middle","product":"CHECKOUT","apiFormat":"SNAP","sourceVersion":"1.0.0"},"accountType":"DIRECT_DEBIT","channel":"DIRECT_DEBIT_ALLO_SNAP","custIdMerchant":"KZR001_1","successPaymentUrl":"https://www.google.com?q\\u003dsuccess-payment-url-1790048625387","paymentType":"SALE","lineItems":[{"name":"Item One","price":"5000.00","quantity":1},{"name":"Item Two","price":"7000.00","quantity":1}],"addInfo2SnakeCase":"Add Info 2 Snake Case.","account":{"id":"account-id-1790048625387","split_rule_id":"split-rule-id-1790048625387"},"channelId":"DIRECT_DEBIT_ALLO_SNAP"}},"header":{"X-PARTNER-ID":"BRN-0203-1728283759342","X-EXTERNAL-ID":"1790048631514472866","CHANNEL-ID":"H2H","Authorization-Customer":"Bearer eyJhbGciOiJSUzI1NiJ9.eyJleHAiOjE3OTEzNDQzOTIsImlzcyI6IkRPS1UiLCJjbGllbnRJZCI6IkJSTi0wMjAzLTE3MjgyODM3NTkzNDIiLCJhY2NvdW50SWQiOiJjMGZjZDE0MTk3OWM3YTM3MTk4OWYxNDdhYmI3OWJjYyJ9.XHQqDRrJTa6V6rYGnOrsld3cINpjXbYHHFxhFEpEGIk4L_EqmPhqdSpqFtKib9waIpRTzYt443gtAnRDTVnweb_VDbsPQW20N-9PfJSI0WKGB9mE901P_6IyhfBl0yU3Rw2RrtYn48eQBA3kcWHz-0yX9QHMQzVQCqUstLrz--NM9Ffj5iF45FxrmF6uEt0wq5KeEnUTHAXZPTqAloErt_tfXKvtgcnqxMjoyYQd_z-XVXs3RJOCi9Say99tXDmm1rtgNiFp7SOthcUVkEuMJw0U5Zv9D6-kawMGLCFkS9Y0hUDpKWqwS8l4ofwE-sSrspyF39LOKgS-GIO6Xc3w6Q"}}},"identifier":{"applicationCode":"APP0029","clientId":"BRN-0203-1728283759342","isRetryNotify":true,"apiVersion":"3.0"},"jdm":{"uuid":"2238260922104346988107170160480001712405","divisionName":"PAYMENT_PLATFORM","departmentName":"WALLET_TOKEN_DEBIT","databaseName":"direct_debit_core","clientCreationTime":"2026-09-22T10:43:51.515073120+07:00[Asia/Jakarta]","journeyId":"pbl-direct-debit-notification-payment","journeyCode":"PBL_CARD_0008","journeyDesc":"Direct Debit notify payment merchant"}}
            2026-09-22 10:43:51.516 DEBUG [direct-debit-core-system,6ab1f9774d0e8f6527f537d00e0c4321,32f26b5926ed82bf] 7 --- [ async-19] c.d.d.c.common.kafka.KafkaPublisher : Send Result : SendResult [producerRecord=ProducerRecord(topic=uat-streaming-notification-http, partition=null, headers=RecordHeaders(headers = [RecordHeader(key = identifier, value = [123, 34, 97, 112, 112, 108, 105, 99, 97, 116, 105, 111, 110, 67, 111, 100, 101, 34, 58, 34, 65, 80, 80, 48, 48, 50, 57, 34, 44, 34, 99, 108, 105, 101, 110, 116, 73, 100, 34, 58, 34, 66, 82, 78, 45, 48, 50, 48, 51, 45, 49, 55, 50, 56, 50, 56, 51, 55, 53, 57, 51, 52, 50, 34, 44, 34, 105, 115, 82, 101, 116, 114, 121, 78, 111, 116, 105, 102, 121, 34, 58, 116, 114, 117, 101, 44, 34, 97, 112, 105, 86, 101, 114, 115, 105, 111, 110, 34, 58, 34, 51, 46, 48, 34, 125]), RecordHeader(key = __TypeId__, value = [99, 111, 109, 46, 100, 111, 107, 117, 46, 100, 100, 46, 99, 111, 114, 101, 100, 105, 114, 101, 99, 116, 100, 101, 98, 105, 116, 46, 100, 116, 111, 46, 107, 97, 102, 107, 97, 46, 83, 116, 114, 101, 97, 109, 105, 110, 103, 78, 111, 116, 105, 102, 105, 99, 97, 116, 105, 111, 110, 72, 116, 116, 112, 68, 116, 111])], isReadOnly = true), key=null, value=StreamingNotificationHttpDto(message=StreamingNotificationHttpDto.MessageDto(to=https://app.beeceptor.com/console/notification-allo, request=StreamingNotificationHttpDto.MessageDto.RequestDto(method=POST, parameter=NotificationParameterDto(originalPartnerReferenceNo=INVALLO1790048625387, originalReferenceNo=aQNRS3Z8JgzQc0VE, originalExternalId=RID_PH2H_1790048625387, latestTransactionStatus=00, transactionStatusDesc=Success, amount=NotificationParameterDto.Amount(value=12000.00, currency=IDR), additionalInfo={addInfo1CamelCase=Add Info 1 Camel Case., failedPaymentUrl=https://www.google.com?q=failed-payment-url-1790048625387, origin={source=shopify, system=devex-shopify-middle, product=CHECKOUT, apiFormat=SNAP, sourceVersion=1.0.0}, accountType=DIRECT_DEBIT, channel=DIRECT_DEBIT_ALLO_SNAP, custIdMerchant=KZR001_1, successPaymentUrl=https://www.google.com?q=success-payment-url-1790048625387, paymentType=SALE, lineItems=[{name=Item One, price=5000.00, quantity=1}, {name=Item Two, price=7000.00, quantity=1}], refundNo=null, addInfo2SnakeCase=Add Info 2 Snake Case., account={id=account-id-1790048625387, split_rule_id=split-rule-id-1790048625387}, channelId=DIRECT_DEBIT_ALLO_SNAP, refundAmount=null}, order=null, customer=null, transaction=null, service=null, acquirer=null, channel=null, additional_info=null, card_payment=null, wallet=null, account=null), header=NotificationHeaderDto(partnerId=BRN-0203-1728283759342, externalId=1790048631514472866, timestamp=null, signature=null, signatureV2=null, requestTimestamp=null, channelId=H2H, authorizationCustomer=Bearer eyJhbGciOiJSUzI1NiJ9.eyJleHAiOjE3OTEzNDQzOTIsImlzcyI6IkRPS1UiLCJjbGllbnRJZCI6IkJSTi0wMjAzLTE3MjgyODM3NTkzNDIiLCJhY2NvdW50SWQiOiJjMGZjZDE0MTk3OWM3YTM3MTk4OWYxNDdhYmI3OWJjYyJ9.XHQqDRrJTa6V6rYGnOrsld3cINpjXbYHHFxhFEpEGIk4L_EqmPhqdSpqFtKib9waIpRTzYt443gtAnRDTVnweb_VDbsPQW20N-9PfJSI0WKGB9mE901P_6IyhfBl0yU3Rw2RrtYn48eQBA3kcWHz-0yX9QHMQzVQCqUstLrz--NM9Ffj5iF45FxrmF6uEt0wq5KeEnUTHAXZPTqAloErt_tfXKvtgcnqxMjoyYQd_z-XVXs3RJOCi9Say99tXDmm1rtgNiFp7SOthcUVkEuMJw0U5Zv9D6-kawMGLCFkS9Y0hUDpKWqwS8l4ofwE-sSrspyF39LOKgS-GIO6Xc3w6Q, clientId=null, requestId=null))), identifier=StreamingNotificationHttpDto.IdentifierDto(applicationCode=APP0029, clientId=BRN-0203-1728283759342, isRetryNotify=true, apiVersion=3.0, apiFormat=null), jdm=JdmDto(uuid=2238260922104346988107170160480001712405, parentUuid=null, batchUuid=null, inquiryUuid=null, registerUuid=null, activities=null, divisionName=PAYMENT_PLATFORM, departmentName=WALLET_TOKEN_DEBIT, databaseName=direct_debit_core, clientCreationTime=2026-09-22T10:43:51.515073120+07:00[Asia/Jakarta], journeyId=pbl-direct-debit-notification-payment, journeyCode=PBL_CARD_0008, journeyDesc=Direct Debit notify payment merchant, originalUuid=null), dispute=null), timestamp=null), recordMetadata=uat-streaming-notification-http-2@1465199]
            2026-09-22 10:43:51.517 DEBUG [direct-debit-core-system,6ab1f9774d0e8f6527f537d00e0c4321,767579495445ec76] 7 --- [ async-19] .d.d.c.s.k.PublishCardTransactionService : publishCardTransaction, action : ACTION_PAYMENT_VALIDATE
            2026-09-22 10:43:51.517 DEBUG [direct-debit-core-system,6ab1f9774d0e8f6527f537d00e0c4321,767579495445ec76] 7 --- [ async-19] c.d.d.c.service.kafka.KafkaService : createJdm....
            2026-09-22 10:43:51.517 DEBUG [direct-debit-core-system,6ab1f9774d0e8f6527f537d00e0c4321,767579495445ec76] 7 --- [ async-19] c.d.d.c.service.kafka.KafkaService : init time : 2024-10-15T12:02:31.453970+07:00[Asia/Jakarta]
            2026-09-22 10:43:51.517 DEBUG [direct-debit-core-system,6ab1f9774d0e8f6527f537d00e0c4321,767579495445ec76] 7 --- [ async-19] c.d.d.c.service.kafka.KafkaService : client time :2026-09-22T10:43:51.517163589+07:00[Asia/Jakarta]
            2026-09-22 10:43:51.517 DEBUG [direct-debit-core-system,6ab1f9774d0e8f6527f537d00e0c4321,767579495445ec76] 7 --- [ async-19] c.d.d.c.service.MerchantService : getMerchantByClientId, clientId : MCH-0001-2768422848440
            2026-09-22 10:43:51.518 INFO [direct-debit-core-system,6ab1f9774d0e8f6527f537d00e0c4321,767579495445ec76] 7 --- [ async-19] c.d.d.c.common.kafka.KafkaPublisher : Sending Kafka to topic : uat-streaming-card-transaction, message : {"businessService":{"service":{"id":"DIRECT_DEBIT","name":"Direct Debit"},"partner":{"id":"MCH-0001-2768422848440","name":"OVO tkn V2"},"client":{"id":"BRN-0203-1728283759342","name":"Merchant Khanza","status":"ACTIVE","apiVersion":"3.0"}},"acquirer":{"id":"ALLO","name":"AlloBank SNAP Direct Debit","apiVersion":"3.0"},"channel":{"id":"DIRECT_DEBIT_ALLO","name":"DIRECT DEBIT ALLO"},"order":{"invoiceNumber":"INVALLO1790048625387","lineItems":[{"name":"Item One","price":"5000.00","quantity":1.0},{"name":"Item Two","price":"7000.00","quantity":1.0}],"currency":"IDR","amount":12000.00,"createdDate":"2026-09-22T03:43:46Z"},"directDebitConfiguration":{"status":"ACTIVE"},"customer":{"id":"KZR001_1","name":"anomymous","email":"john.doe@doku.com","phone":"6281122588381","idCard":"12345","country":"Indonesia","address":"Bali","dateOfBirth":"01-01-1999"},"cardTransaction":{"type":"SALE","requestId":"RID_PH2H_1790048625387","cardMasked":"****8381","responseCode":"2005400","responseMessage":"Successful","paymentId":"aQNRS3Z8JgzQc0VE","transactionStatus":"SUCCESS","date":"2026-09-22T03:43:46Z","token":"c0fcd141979c7a371989f147abb79bcc"},"transaction":{"date":"2026-09-22T03:43:46Z","status":"SUCCESS","serviceCode":"54","originalRequestId":"RID_PH2H_1790048625387","originalPartnerReferenceNo":"INVALLO1790048625387","originalReferenceNo":"aQNRS3Z8JgzQc0VE","originalExternalId":"RID_PH2H_1790048625387"},"jdm":{"uuid":"2238260922104346988107170160480001712405","divisionName":"PAYMENT_PLATFORM","departmentName":"WALLET_TOKEN_DEBIT","databaseName":"direct_debit_core","clientCreationTime":"2026-09-22T10:43:51.517163589+07:00[Asia/Jakarta]","journeyId":"pbl-direct-debit-validate-payment","journeyCode":"PBL_CARD_0002","journeyDesc":"Direct Debit validate payment process"},"additionalInfo":{"lineItems":[{"name":"Item One","price":"5000.00","quantity":1},{"name":"Item Two","price":"7000.00","quantity":1}],"serviceType":"H2H","addInfo1CamelCase":"Add Info 1 Camel Case.","failedPaymentUrl":"https://www.google.com?q\\u003dfailed-payment-url-1790048625387","origin":{"product":"CHECKOUT","source":"shopify","sourceVersion":"1.0.0","system":"devex-shopify-middle","apiFormat":"SNAP"},"channel":"DIRECT_DEBIT_ALLO_SNAP","successPaymentUrl":"https://www.google.com?q\\u003dsuccess-payment-url-1790048625387","addInfo2SnakeCase":"Add Info 2 Snake Case.","account":{"id":"account-id-1790048625387","split_rule_id":"split-rule-id-1790048625387"},"paymentType":"SALE"},"screening":{"status":"","type":"DECISION","reason":""}}
            2026-09-22 10:43:51.519 DEBUG [direct-debit-core-system,6ab1f9774d0e8f6527f537d00e0c4321,767579495445ec76] 7 --- [ async-19] c.d.d.c.common.kafka.KafkaPublisher : Send Result : SendResult [producerRecord=ProducerRecord(topic=uat-streaming-card-transaction, partition=null, headers=RecordHeaders(headers = [RecordHeader(key = __TypeId__, value = [99, 111, 109, 46, 100, 111, 107, 117, 46, 100, 100, 46, 99, 111, 114, 101, 100, 105, 114, 101, 99, 116, 100, 101, 98, 105, 116, 46, 100, 116, 111, 46, 107, 97, 102, 107, 97, 46, 83, 116, 114, 101, 97, 109, 105, 110, 103, 67, 97, 114, 100, 84, 114, 97, 110, 115, 97, 99, 116, 105, 111, 110, 68, 116, 111])], isReadOnly = true), key=null, value=StreamingCardTransactionDto(businessService=StreamingCardTransactionDto.BusinessServiceDto(service=StreamingCardTransactionDto.ServiceDto(id=DIRECT_DEBIT, name=Direct Debit), partner=StreamingCardTransactionDto.PartnerDto(id=MCH-0001-2768422848440, name=OVO tkn V2), client=StreamingCardTransactionDto.ClientDto(id=BRN-0203-1728283759342, name=Merchant Khanza, sharedKey=null, sharedKeyEnc=null, status=ACTIVE, apiVersion=3.0)), acquirer=StreamingCardTransactionDto.AcquirerDto(id=ALLO, name=AlloBank SNAP Direct Debit, apiVersion=3.0, nns=null), channel=StreamingCardTransactionDto.ChannelDto(id=DIRECT_DEBIT_ALLO, name=DIRECT DEBIT ALLO), order=StreamingCardTransactionDto.OrderDto(invoiceNumber=INVALLO1790048625387, lineItems=[{name=Item One, price=5000.00, quantity=1.0}, {name=Item Two, price=7000.00, quantity=1.0}], currency=IDR, amount=12000.00, createdDate=2026-09-22T03:43:46Z), refund=null, directDebitConfiguration=StreamingCardTransactionDto.DirectDebitConfigurationDto(status=ACTIVE), customer=StreamingCardTransactionDto.CustomerDto(id=KZR001_1, name=anomymous, email=john.doe@doku.com, phone=6281122588381, idCard=12345, country=Indonesia, address=Bali, dateOfBirth=01-01-1999, additionalInfo=null), cardTransaction=StreamingCardTransactionDto.CardTransactionDto(type=SALE, requestId=RID_PH2H_1790048625387, cardMasked=****8381, responseCode=2005400, responseMessage=Successful, paymentId=aQNRS3Z8JgzQc0VE, transactionStatus=SUCCESS, date=2026-09-22T03:43:46Z, token=c0fcd141979c7a371989f147abb79bcc, acquiringOffUsStatus=null, installmentTenor=null, planId=null, issuer=null, approvalCode=null, brand=null, cardType=null, transactionErrorMessage=null, threeDSecureStatus=null, authCode=null, settlementDate=null, settlementStatus=null, authorizeId=null, cardNumber=null, cardExpired=null, tokenId=null, batchNumber=null, authenticationId=null, encryptedCardNumber=null, expiryDate=null, identifier=null, referenceNo=null), transaction=StreamingCardTransactionDto.TransactionDto(date=2026-09-22T03:43:46Z, status=SUCCESS, serviceCode=54, originalRequestId=RID_PH2H_1790048625387, originalPartnerReferenceNo=INVALLO1790048625387, originalReferenceNo=aQNRS3Z8JgzQc0VE, originalExternalId=RID_PH2H_1790048625387, accountMasked=null, type=null, amount=null, issuerId=null, category=null, issuerNns=null, acquirerNns=null, acquirerId=null, acquirerName=null, gatewayRefNum=null, amountDetails=null, partnerRefNum=null, responseCode=null, responseMessage=null, approvalCode=null, gatewayToken=null, mpan=null, issuerName=null), jdm=JdmDto(uuid=2238260922104346988107170160480001712405, parentUuid=null, batchUuid=null, inquiryUuid=null, registerUuid=null, activities=null, divisionName=PAYMENT_PLATFORM, departmentName=WALLET_TOKEN_DEBIT, databaseName=direct_debit_core, clientCreationTime=2026-09-22T10:43:51.517163589+07:00[Asia/Jakarta], journeyId=pbl-direct-debit-validate-payment, journeyCode=PBL_CARD_0002, journeyDesc=Direct Debit validate payment process, originalUuid=null), dispute=null, additionalInfo={lineItems=[{name=Item One, price=5000.00, quantity=1}, {name=Item Two, price=7000.00, quantity=1}], serviceType=H2H, addInfo1CamelCase=Add Info 1 Camel Case., failedPaymentUrl=https://www.google.com?q=failed-payment-url-1790048625387, origin=OriginDto(product=CHECKOUT, source=shopify, sourceVersion=1.0.0, system=devex-shopify-middle, apiFormat=SNAP, standardApiVersion=null, format=null, apiVersion=null), channel=DIRECT_DEBIT_ALLO_SNAP, successPaymentUrl=https://www.google.com?q=success-payment-url-1790048625387, addInfo2SnakeCase=Add Info 2 Snake Case., account={id=account-id-1790048625387, split_rule_id=split-rule-id-1790048625387}, paymentType=SALE}, verification=null, origin=null, screening=StreamingCardTransactionDto.ScreeningDto(status=, type=DECISION, reason=)), timestamp=null), recordMetadata=uat-streaming-card-transaction-1@322034]
            """;

        ClipboardUtils.copyToClipboard(parseLogsToExpandPlusCodeBlock(logs));
    }
}
