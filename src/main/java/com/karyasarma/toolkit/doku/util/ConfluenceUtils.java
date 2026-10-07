package com.karyasarma.toolkit.doku.util;

import com.karyasarma.toolkit.doku.model.ClipboardData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
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

    private static final String EXPAND_PLUS_CODE_BLOCK_API_HTTP_LOGS_TEMPLATE = """
        <table class="wysiwyg-macro" data-macro-name="expand" data-macro-parameters="title=API HTTP Logs" data-macro-schema-version="1" data-macro-body-type="RICH_TEXT" style="background-color: rgb(240, 240, 240); background-position: 0px 0px; background-repeat: no-repeat; border: 1px solid rgb(221, 221, 221); margin-top: 10px; padding: 24px 2px 2px; width: 1432px; border-collapse: separate; cursor: move; background-image: url(&quot;/plugins/servlet/confluence/placeholder/macro-heading?definition=e2V4cGFuZH0&amp;locale=en_GB&amp;version=2&quot;);">
          <tbody>
            <tr>
              <td class="wysiwyg-macro-body" style="white-space: pre-wrap; background-color: rgb(255, 255, 255); border: 1px solid rgb(221, 221, 221); margin: 0px; padding: 10px; cursor: text;">
                <p class="auto-cursor-target" style="margin: 0px;"><br></p>
                <table class="wysiwyg-macro" data-macro-name="code" data-macro-parameters="language=text|title=API HTTP Logs" data-macro-schema-version="1" data-macro-body-type="PLAIN_TEXT" style="background-color: rgb(240, 240, 240); background-position: 0px 0px; background-repeat: no-repeat; border: 1px solid rgb(221, 221, 221); margin-top: 10px; padding: 24px 2px 2px; width: 1400px; border-collapse: separate; cursor: move; background-image: url(&quot;/plugins/servlet/confluence/placeholder/macro-heading?definition=e2NvZGU6bGFuZ3VhZ2U9dGV4dH0&amp;locale=en_GB&amp;version=2&quot;);">
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

    private static final String EXPAND_PLUS_CODE_BLOCK_ISO_8583_REQUEST_TEMPLATE = """
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
              </td>
            </tr>
          </tbody>
        </table>
        """;

    private static final Pattern DD_CORE_SYSTEM_LOG_PREFIX_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}.\\d{3}\\s+(ERROR|INFO|WARN|DEBUG)\\s+\\[.*");

    private static final Pattern DD_CORE_SYSTEM_LOG_API_HTTP_LOGS_PATTERN = Pattern.compile(".*CommonAppRequestFilter\\s+:\\sHTTP Logs:.*", Pattern.DOTALL);
    private static final Pattern DD_CORE_SYSTEM_LOG_API_HTTP_LOGS_REQ_AND_RESP_PATTERN = Pattern.compile(".*Req\\sBody\\s+=\\s(.*)Resp\\sBody\\s+=\\s(.*)Execute-In\\s+=.*", Pattern.DOTALL);

    private static final Pattern DD_CORE_SYSTEM_LOG_PUBLISH_KAFKA_VARIANT_1_PATTERN = Pattern.compile(".*\\[(.*)]\\sKafka\\sevent\\shas been sent\\..*", Pattern.DOTALL);

    private static final Pattern DD_CORE_SYSTEM_LOG_PUBLISH_KAFKA_VARIANT_2_PATTERN = Pattern.compile(".*Sending\\sKafka\\sto\\stopic\\s:\\s(.*),\\smessage\\s:\\s.*");
    private static final Pattern DD_CORE_SYSTEM_LOG_PUBLISH_KAFKA_VARIANT_2_SPLIT_PATTERN = Pattern.compile("Sending\\sKafka\\sto\\stopic\\s:\\s(.*),\\smessage\\s:\\s");

    private static final Pattern WT_API_TRANSACTION_SYSTEM_LOG_PUBLISH_KAFKA_VARIANT_1_PATTERN = Pattern.compile(".*Producing\\smessage\\s([a-zA-Z-]*)\\s(?:with\\sheaders:\\s)?(.*)\\s->\\s(.*).*");
    private static final Pattern WT_API_TRANSACTION_SYSTEM_LOG_ISO_8583_REQ_AND_RESP_PATTERN = Pattern.compile(".*] (BANK_MANDIRI|BNI) (.*) (REQUEST|RESPONSE) : (\\{.*)", Pattern.DOTALL);

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

        var listOfWtApiTransactionCybersourceHttpRequestLogs = new ArrayList<StringBuilder>();
        var listOfIso8583RequestLogs = new ArrayList<StringBuilder>();

        Matcher matcher;

        for(var log : listOfGroupedLog)
        {
            if(DD_CORE_SYSTEM_LOG_API_HTTP_LOGS_PATTERN.matcher(log).matches())
            {
                var httpLogs = log.toString().split("\n", 2)[1];

                var reqAndRespBodyMatcher = DD_CORE_SYSTEM_LOG_API_HTTP_LOGS_REQ_AND_RESP_PATTERN.matcher(httpLogs);

                var reqBody = "";
                var respBody = "";

                if(reqAndRespBodyMatcher.matches())
                {
                    reqBody = JsonUtils.prettifyIgnoreException(reqAndRespBodyMatcher.group(1));
                    respBody = JsonUtils.prettifyIgnoreException(reqAndRespBodyMatcher.group(2));
                }

                var contentHtmlTemp = EXPAND_PLUS_CODE_BLOCK_API_HTTP_LOGS_TEMPLATE.formatted
                (
                    httpLogs,
                    reqBody,
                    respBody
                );

                contentHtml.insert(0, contentHtmlTemp);
            }
            else if((matcher = DD_CORE_SYSTEM_LOG_PUBLISH_KAFKA_VARIANT_1_PATTERN.matcher(log)).matches())
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
            else if((matcher = DD_CORE_SYSTEM_LOG_PUBLISH_KAFKA_VARIANT_2_PATTERN.matcher(log)).matches())
            {
                var kafkaTopic = matcher.group(1);
                var kafkaPayload = DD_CORE_SYSTEM_LOG_PUBLISH_KAFKA_VARIANT_2_SPLIT_PATTERN.split(log, 2)[1];
                var kafkaPayloadPrettify = JsonUtils.prettifyIgnoreException(kafkaPayload);

                var contentHtmlTemp = EXPAND_PLUS_CODE_BLOCK_PUBLISH_KAFKA_TEMPLATE.formatted("Publish " + kafkaTopic, kafkaPayloadPrettify);
                contentHtml.append(contentHtmlTemp);
            }
            else if((matcher = WT_API_TRANSACTION_SYSTEM_LOG_PUBLISH_KAFKA_VARIANT_1_PATTERN.matcher(log)).matches())
            {
                var kafkaTopic = matcher.group(1);
                var headerInfo = matcher.group(2);
                var kafkaPayload = matcher.group(3);
                var kafkaPayloadPrettify = JsonUtils.prettifyIgnoreException(kafkaPayload);
                var kafkaPayloadPrettifyWithAdditionalInfo = """
                    // Header Info: %s
                    
                    %s
                    """.formatted(headerInfo, kafkaPayloadPrettify);

                var contentHtmlTemp = EXPAND_PLUS_CODE_BLOCK_PUBLISH_KAFKA_TEMPLATE.formatted("Publish " + kafkaTopic, kafkaPayloadPrettifyWithAdditionalInfo);
                contentHtml.append(contentHtmlTemp);
            }
            else if(log.indexOf("CLIENT HTTP REQUEST START") != -1)
            {
                insertContentHtmlForHttpRequestLogs(contentHtml, listOf1HttpRequestLogs);
                httpRequestFound = true;
            }
            else if(log.indexOf("CLIENT HTTP REQUEST FINISH") != -1)
            {
                insertContentHtmlForHttpRequestLogs(contentHtml, listOf1HttpRequestLogs);
                httpRequestFound = false;
            }
            else if(httpRequestFound)
            {
                listOf1HttpRequestLogs.add(log);
            }
            else if(log.indexOf("] Cybersource") != -1)
            {
                if(log.indexOf("Cybersource URL :") != -1)
                {
                    insertContentHtmlForWtApiTransactionCybersourceHttpRequestLogs(contentHtml, listOfWtApiTransactionCybersourceHttpRequestLogs);
                    listOfWtApiTransactionCybersourceHttpRequestLogs.add(log);
                }
                else if
                (
                    log.indexOf("Cybersource Response :") != -1 ||
                    log.indexOf("Cybersource Failed Response :") != -1
                )
                {
                    listOfWtApiTransactionCybersourceHttpRequestLogs.add(log);
                    insertContentHtmlForWtApiTransactionCybersourceHttpRequestLogs(contentHtml, listOfWtApiTransactionCybersourceHttpRequestLogs);
                }
            }
            else if(log.indexOf("] Payment URL: ") != -1)
            {
                insertContentHtmlForWtApiTransactionIso8583RequestLogs(contentHtml, listOfIso8583RequestLogs);

                try
                {
                    var paymentUrl = log.toString().split(" Payment URL: ")[1];
                    listOfIso8583RequestLogs.add(new StringBuilder("Payment URL: ").append(paymentUrl));
                }
                catch(Exception ex)
                {
                    ex.printStackTrace(System.err);
                }
            }
            else if((matcher = WT_API_TRANSACTION_SYSTEM_LOG_ISO_8583_REQ_AND_RESP_PATTERN.matcher(log)).matches())
            {
                try
                {
                    var acquirer = matcher.group(1);
                    var transactionType = matcher.group(2);
                    var requestOrResponse = matcher.group(3);
                    var payload = matcher.group(4);

                    var logNormalized = "%s %s %s:\n%s".formatted(acquirer, transactionType, requestOrResponse, payload);
                    listOfIso8583RequestLogs.add(new StringBuilder(logNormalized));

                    if("RESPONSE".equals(requestOrResponse))
                    {
                        insertContentHtmlForWtApiTransactionIso8583RequestLogs(contentHtml, listOfIso8583RequestLogs);
                    }
                }
                catch(Exception ex)
                {
                    ex.printStackTrace(System.err);
                }
            }
        }

        // If Cybersource Response never found, then will keep added the premature HTTP Request logs
        insertContentHtmlForWtApiTransactionCybersourceHttpRequestLogs(contentHtml, listOfWtApiTransactionCybersourceHttpRequestLogs);

        contentHtml.append(fullLogsHtml); // Add full logs at the bottom.

        return new ClipboardData(logs, contentHtml.toString());
    }

    private static final Map<String, List<Pattern>> MAP_OF_PATTERN_OF_HTTP_REQUEST = new LinkedHashMap<>();

    static
    {
        var addHttpRequestPattern = (BiConsumer<String, String>) (key, valuePattern) ->
            MAP_OF_PATTERN_OF_HTTP_REQUEST.computeIfAbsent(key, k -> new ArrayList<>()).add(Pattern.compile(valuePattern));

        addHttpRequestPattern.accept("requestUrl",      "LoggingClientInterceptor\\s+: Request URL\\s+: ");
        addHttpRequestPattern.accept("requestHeaders",  "LoggingClientInterceptor\\s+: Request Header\\s+: ");
        addHttpRequestPattern.accept("requestBody",     "LoggingClientInterceptor\\s+: Request body\\s+: ");
        addHttpRequestPattern.accept("responseStatus",  "LoggingClientInterceptor\\s+: Response status\\s+: ");
        addHttpRequestPattern.accept("responseHeaders", "LoggingClientInterceptor\\s+: Response header\\s+: ");
        addHttpRequestPattern.accept("responseBody",    "LoggingClientInterceptor\\s+: Response body\\s+: ");

        addHttpRequestPattern.accept("requestUrl",      "Request\\s+: ");
        addHttpRequestPattern.accept("requestHeaders",  "Request Header\\s+: ");
        addHttpRequestPattern.accept("requestBody",     "Request Body\\s+: ");
        addHttpRequestPattern.accept("responseStatus",  "Response Status\\s+: ");
        addHttpRequestPattern.accept("responseHeaders", "Response Header\\s+: ");
        addHttpRequestPattern.accept("responseBody",    "Response Body\\s+: ");
    }

    @SuppressWarnings("DuplicatedCode")
    private static void insertContentHtmlForHttpRequestLogs(StringBuilder contentHtml, List<StringBuilder> listOf1HttpRequestLogs)
    {
        if(listOf1HttpRequestLogs.isEmpty())
        {
            return;
        }

        var mapOfPatternOfHttpRequestTemp = new LinkedHashMap<>(MAP_OF_PATTERN_OF_HTTP_REQUEST);
        var mapOfValue = new HashMap<String, String>();

        for(var log : listOf1HttpRequestLogs)
        {
            for(var entry : mapOfPatternOfHttpRequestTemp.entrySet())
            {
                var key = entry.getKey();
                var listOfPattern = entry.getValue();

                var found = false;

                for(var pattern : listOfPattern)
                {
                    var splitResult = pattern.split(log, 2);

                    if(splitResult.length == 2)
                    {
                        found = true;
                        mapOfValue.put(key, splitResult[1]);
                        break;
                    }
                }

                if(found)
                {
                    mapOfPatternOfHttpRequestTemp.remove(key);
                    break;
                }
            }
        }

        var templateData = new StringBuilder();
        templateData.append("Request URL      : ").append(mapOfValue.getOrDefault("requestUrl", "")).append("<br>");
        templateData.append("Request Headers  : ").append(mapOfValue.getOrDefault("requestHeaders", "")).append("<br>");
        templateData.append("Request Body     : ").append(mapOfValue.getOrDefault("requestBody", "")).append("<br>");
        templateData.append("<br>");
        templateData.append("Response Status  : ").append(mapOfValue.getOrDefault("responseStatus", "")).append("<br>");
        templateData.append("Response Headers : ").append(mapOfValue.getOrDefault("responseHeaders", "")).append("<br>");
        templateData.append("Response Body    : ").append(mapOfValue.getOrDefault("responseBody", ""));

        var requestBody = JsonUtils.prettifyIgnoreException(mapOfValue.getOrDefault("requestBody", ""));
        var responseBody = JsonUtils.prettifyIgnoreException(mapOfValue.getOrDefault("responseBody", ""));

        var newContentHtml = EXPAND_PLUS_CODE_BLOCK_HTTP_REQUEST_TEMPLATE.formatted
        (
            mapOfValue.get("requestUrl"),
            templateData,
            requestBody,
            responseBody
        );

        listOf1HttpRequestLogs.clear();
        contentHtml.append(newContentHtml);
    }

    private static final Map<String, List<Pattern>> MAP_OF_PATTERN_OF_WT_API_TRANSACTION_CYBERSOURCE_HTTP_REQUEST = new LinkedHashMap<>();

    static
    {
        var addHttpRequestPattern = (BiConsumer<String, String>) (key, valuePattern) ->
            MAP_OF_PATTERN_OF_WT_API_TRANSACTION_CYBERSOURCE_HTTP_REQUEST.computeIfAbsent(key, k -> new ArrayList<>()).add(Pattern.compile(valuePattern));

        addHttpRequestPattern.accept("requestUrl",      "Cybersource URL :");
        addHttpRequestPattern.accept("requestHeaders",  "Cybersource Request Header :");
        addHttpRequestPattern.accept("requestBody",     "Cybersource Request Body : ");
        addHttpRequestPattern.accept("responseBody",    "Cybersource Response :");
        addHttpRequestPattern.accept("responseBody",    "Cybersource Failed Response :");
    }

    @SuppressWarnings("DuplicatedCode")
    private static void insertContentHtmlForWtApiTransactionCybersourceHttpRequestLogs(StringBuilder contentHtml, List<StringBuilder> listOf1HttpRequestLogs)
    {
        if(listOf1HttpRequestLogs.isEmpty())
        {
            return;
        }

        var mapOfPatternOfHttpRequestTemp = new LinkedHashMap<>(MAP_OF_PATTERN_OF_WT_API_TRANSACTION_CYBERSOURCE_HTTP_REQUEST);
        var mapOfValue = new HashMap<String, String>();

        for(var log : listOf1HttpRequestLogs)
        {
            for(var entry : mapOfPatternOfHttpRequestTemp.entrySet())
            {
                var key = entry.getKey();
                var listOfPattern = entry.getValue();

                var found = false;

                for(var pattern : listOfPattern)
                {
                    var splitResult = pattern.split(log, 2);

                    if(splitResult.length == 2)
                    {
                        found = true;
                        mapOfValue.put(key, splitResult[1]);
                        break;
                    }
                }

                if(found)
                {
                    mapOfPatternOfHttpRequestTemp.remove(key);
                    break;
                }
            }
        }

        var templateData = new StringBuilder();
        templateData.append("Request URL      : ").append(mapOfValue.getOrDefault("requestUrl", "")).append("<br>");
        templateData.append("Request Headers  : ").append(mapOfValue.getOrDefault("requestHeaders", "")).append("<br>");
        templateData.append("Request Body     : ").append(mapOfValue.getOrDefault("requestBody", "")).append("<br>");
        templateData.append("<br>");
        templateData.append("Response Body    : ").append(mapOfValue.getOrDefault("responseBody", ""));

        var requestBody = JsonUtils.prettifyIgnoreException(mapOfValue.getOrDefault("requestBody", ""));
        var responseBody = JsonUtils.prettifyIgnoreException(mapOfValue.getOrDefault("responseBody", ""));

        var newContentHtml = EXPAND_PLUS_CODE_BLOCK_HTTP_REQUEST_TEMPLATE.formatted
        (
            "Call " + mapOfValue.get("requestUrl"),
            templateData,
            requestBody,
            responseBody
        );

        listOf1HttpRequestLogs.clear();
        contentHtml.append(newContentHtml);
    }

    @SuppressWarnings("DuplicatedCode")
    private static void insertContentHtmlForWtApiTransactionIso8583RequestLogs(StringBuilder contentHtml, List<StringBuilder> listOfLogs)
    {
        if(listOfLogs.isEmpty())
        {
            return;
        }

        var expandTitle = new StringBuilder("Request ISO-8583");
        var templateData = new StringBuilder();
        var firstData = true;

        for(var log : listOfLogs)
        {
            if(log.toString().contains("Payment URL: "))
            {
                var paymentUrl = log.toString().split("Payment URL: ")[1];
                expandTitle.append(" - ").append(paymentUrl);
            }

            if(firstData)
            {
                firstData = false;
                templateData.append(log);
            }
            else
            {
                templateData.append("<br>").append(log);
            }
        }

        var newContentHtml = EXPAND_PLUS_CODE_BLOCK_ISO_8583_REQUEST_TEMPLATE.formatted
        (
            expandTitle.toString(),
            templateData
        );

        listOfLogs.clear();
        contentHtml.append(newContentHtml);
    }
}
