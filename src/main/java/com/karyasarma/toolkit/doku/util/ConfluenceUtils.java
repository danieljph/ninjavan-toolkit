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

    private static final Pattern DD_CORE_SYSTEM_LOG_PREFIX_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}.\\d{3}\\s+(ERROR|INFO|WARN|DEBUG)\\s+\\[.*");

    private static final Pattern DD_CORE_SYSTEM_LOG_API_HTTP_LOGS_PATTERN = Pattern.compile(".*CommonAppRequestFilter\\s+:\\sHTTP Logs:.*", Pattern.DOTALL);
    private static final Pattern DD_CORE_SYSTEM_LOG_API_HTTP_LOGS_REQ_AND_RESP_PATTERN = Pattern.compile(".*Req\\sBody\\s+=\\s(.*)Resp\\sBody\\s+=\\s(.*)Execute-In\\s+=.*", Pattern.DOTALL);

    private static final Pattern DD_CORE_SYSTEM_LOG_PUBLISH_KAFKA_VARIANT_1_PATTERN = Pattern.compile(".*\\[(.*)]\\sKafka\\sevent\\shas been sent\\..*", Pattern.DOTALL);

    private static final Pattern DD_CORE_SYSTEM_LOG_PUBLISH_KAFKA_VARIANT_2_PATTERN = Pattern.compile(".*Sending\\sKafka\\sto\\stopic\\s:\\s(.*),\\smessage\\s:\\s.*");
    private static final Pattern DD_CORE_SYSTEM_LOG_PUBLISH_KAFKA_VARIANT_2_SPLIT_PATTERN = Pattern.compile("Sending\\sKafka\\sto\\stopic\\s:\\s(.*),\\smessage\\s:\\s");

    private static final Pattern WT_API_TRANSACTION_SYSTEM_LOG_PUBLISH_KAFKA_VARIANT_1_PATTERN = Pattern.compile(".*Producing\\smessage\\s([a-zA-Z-]*)\\s(?:with\\sheaders:\\s)?(.*)\\s->\\s(.*).*");

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

    private static final Map<String, List<Pattern>> MAP_OF_PATTERN_OF_HTTP_REQUEST = new LinkedHashMap<>();

    static
    {
        var addHttpRequestPattern = (BiConsumer<String, String>) (key, valuePattern) ->
            MAP_OF_PATTERN_OF_HTTP_REQUEST.computeIfAbsent(key, k -> new ArrayList<>()).add(Pattern.compile(valuePattern));

        addHttpRequestPattern.accept("requestUrl",      "LoggingClientInterceptor\\s+:\\sRequest\\sURL\\s+:\\s");
        addHttpRequestPattern.accept("requestHeaders",  "LoggingClientInterceptor\\s+:\\sRequest\\sHeader\\s+:\\s");
        addHttpRequestPattern.accept("requestBody",     "LoggingClientInterceptor\\s+:\\sRequest\\sbody\\s+:\\s");
        addHttpRequestPattern.accept("responseStatus",  "LoggingClientInterceptor\\s+:\\sResponse\\sstatus\\s+:\\s");
        addHttpRequestPattern.accept("responseHeaders", "LoggingClientInterceptor\\s+:\\sResponse\\sheader\\s+:\\s");
        addHttpRequestPattern.accept("responseBody",    "LoggingClientInterceptor\\s+:\\sResponse\\sbody\\s+:\\s");

        addHttpRequestPattern.accept("requestUrl",      "Request\\s+:\\s");
        addHttpRequestPattern.accept("requestHeaders",  "Request\\sHeader\\s+:\\s");
        addHttpRequestPattern.accept("requestBody",     "Request\\sBody\\s+:\\s");
        addHttpRequestPattern.accept("responseStatus",  "Response\\sStatus\\s+:\\s");
        addHttpRequestPattern.accept("responseHeaders", "Response\\sHeader\\s+:\\s");
        addHttpRequestPattern.accept("responseBody",    "Response\\sBody\\s+:\\s");
    }

    private static String generateContentHtmlForHttpRequestLogs(List<StringBuilder> listOf1HttpRequestLogs)
    {
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

        var httpRequestLogsTemplateData = new StringBuilder();
        httpRequestLogsTemplateData.append("Request URL      : ").append(mapOfValue.getOrDefault("requestUrl", "")).append("<br>");
        httpRequestLogsTemplateData.append("Request Headers  : ").append(mapOfValue.getOrDefault("requestHeaders", "")).append("<br>");
        httpRequestLogsTemplateData.append("Request Body     : ").append(mapOfValue.getOrDefault("requestBody", "")).append("<br>");
        httpRequestLogsTemplateData.append("<br>");
        httpRequestLogsTemplateData.append("Response Status  : ").append(mapOfValue.getOrDefault("responseStatus", "")).append("<br>");
        httpRequestLogsTemplateData.append("Response Headers : ").append(mapOfValue.getOrDefault("responseHeaders", "")).append("<br>");
        httpRequestLogsTemplateData.append("Response Body    : ").append(mapOfValue.getOrDefault("responseBody", ""));

        var requestBody = JsonUtils.prettifyIgnoreException(mapOfValue.getOrDefault("requestBody", ""));
        var responseBody = JsonUtils.prettifyIgnoreException(mapOfValue.getOrDefault("responseBody", ""));

        return EXPAND_PLUS_CODE_BLOCK_HTTP_REQUEST_TEMPLATE.formatted
        (
            mapOfValue.get("requestUrl"),
            httpRequestLogsTemplateData,
            requestBody,
            responseBody
        );
    }
}
