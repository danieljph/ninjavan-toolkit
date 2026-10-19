package com.karyasarma.toolkit.doku.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Daniel Joi Partogi Hutapea
 */
public class SpringCloudConfigUtils
{
    private SpringCloudConfigUtils()
    {
    }

    public static String pathsToPredicatesPathSingleLine(String paths)
    {
        var listOfPattern = Arrays.stream(paths.trim().split("\n"))
            .map(String::trim)
            .distinct()
            .sorted()
            .collect(Collectors.joining(", "));

        return """
                    predicates:
                      - Path=%s\
            """.formatted(listOfPattern);
    }

    public static String pathsToPredicatesPathMultiLine(String paths)
    {
        var listOfPattern = Arrays.stream(paths.trim().split("\n"))
            .map(String::trim)
            .distinct()
            .sorted()
            .map(it -> "                - " + it)
            .collect(Collectors.joining("\n"));

        return """
                    predicates:
                      - name: Path
                        args:
                          pattern:
            %s""".formatted(listOfPattern);
    }

    public static String fromPredicatesPathSingleLineToMultiLine(String predicatesPathSingleLine)
    {
        var lines = predicatesPathSingleLine.trim().split("\n");
        var listOfPath = new ArrayList<String>();

        for(var line : lines)
        {
            var lineTrimmed = line.trim();

            if(lineTrimmed.contains("Path="))
            {
                var temp = lineTrimmed
                    .split("=")[1]
                    .split(",");

                Arrays.stream(temp)
                    .map(String::trim)
                    .forEach(listOfPath::add);
            }
        }

        return pathsToPredicatesPathMultiLine(String.join("\n", listOfPath));
    }

    public static String fromPredicatesPathMultiLineToSingleLine(String predicatesPathMultiLine)
    {
        var lines = predicatesPathMultiLine.trim().split("\n");
        var listOfPath = new ArrayList<String>();
        var startGetData = false;

        for(var line : lines)
        {
            var lineTrimmed = line.trim();

            if(lineTrimmed.startsWith("pattern:"))
            {
                startGetData = true;
                continue;
            }

            if(startGetData && lineTrimmed.startsWith("-"))
            {
                listOfPath.add(lineTrimmed.substring(1).trim());
            }
        }

        return pathsToPredicatesPathSingleLine(String.join("\n", listOfPath));
    }

    public static void main(String[] args)
    {
        String paths = """
            /wtd-direct-debit-snap-bri/publish/unreconciled-transaction
            /wtd-direct-debit-snap-bri/snap/v1.0/debit/notify
            /wtd-direct-debit-snap-bri/snap/v1.0/debit/notify/refund
            /wtd-direct-debit-snap-bri/snap/v1.0/debit/payment-host-to-host
            /wtd-direct-debit-snap-bri/snap/v1.0/debit/refund
            /wtd-direct-debit-snap-bri/snap/v1.0/debit/status
            /wtd-direct-debit-snap-bri/snap/v1.0/otp-verification
            /wtd-direct-debit-snap-bri/snap/v1.0/registration-card-bind
            /wtd-direct-debit-snap-bri/snap/v1.0/registration-card-unbind
            /wtd-direct-debit-snap-bri/v1/identify-dispute
            """;

        System.out.println("==============");
        var singleLine = pathsToPredicatesPathSingleLine(paths);
        System.out.println(singleLine);
        System.out.println("--------------");
        var multiLine = pathsToPredicatesPathMultiLine(paths);
        System.out.println(multiLine);
        System.out.println("--------------");
        System.out.println(fromPredicatesPathSingleLineToMultiLine(singleLine));
        System.out.println("--------------");
        System.out.println(fromPredicatesPathMultiLineToSingleLine(multiLine));
        System.out.println("==============");
    }
}
