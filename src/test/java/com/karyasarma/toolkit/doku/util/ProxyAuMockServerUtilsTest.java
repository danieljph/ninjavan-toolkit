package com.karyasarma.toolkit.doku.util;

import org.junit.jupiter.api.Test;

import static com.karyasarma.toolkit.doku.util.ProxyAuMockServerUtils.convertUrlToProxyAuMockServerOrViceVersa;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Daniel Joi Partogi Hutapea
 */
class ProxyAuMockServerUtilsTest
{
    @Test
    void testFromAuMockServerApiGatewayWithEmptyUrlParams()
    {
        String input = "https://api-uat.doku.com/au-mockserver/merchant-api/bni-non-snap/inquiry/full-paycode";
        String output = convertUrlToProxyAuMockServerOrViceVersa(input);
        String expected = "https://api-uat.doku.com/gtw-api-mock-server/proxy-au-mockserver/au-mockserver/merchant-api/bni-non-snap/inquiry/full-paycode?validate-request-signature=false&generate-response-signature=true&secret-key=SK-ChangeThis";
        assertEquals(expected, output);
    }

    @Test
    void testFromAuMockServerApiGatewayWithNonEmptyUrlParams()
    {
        String input = "https://api-uat.doku.com/au-mockserver/merchant-api/bni-non-snap/inquiry/full-paycode?param-1=value-contains-%24";
        String output = convertUrlToProxyAuMockServerOrViceVersa(input);
        String expected = "https://api-uat.doku.com/gtw-api-mock-server/proxy-au-mockserver/au-mockserver/merchant-api/bni-non-snap/inquiry/full-paycode?param-1=value-contains-%24&validate-request-signature=false&generate-response-signature=true&secret-key=SK-ChangeThis";
        assertEquals(expected, output);
    }

    @Test
    void testFromGtwApiMockServerApiGatewayWithEmptyUrlParams()
    {
        String input = "https://api-sit.doku.com/gtw-api-mock-server/proxy-au-mockserver/au-mockserver/merchant-api/bni-non-snap/inquiry/full-paycode";
        String output = convertUrlToProxyAuMockServerOrViceVersa(input);
        String expected = "https://api-sit.doku.com/au-mockserver/merchant-api/bni-non-snap/inquiry/full-paycode";
        assertEquals(expected, output);
    }

    @Test
    void testFromGtwApiMockServerApiGatewayWithNonEmptyUrlParams()
    {
        {
            String input = "https://api-sit.doku.com/gtw-api-mock-server/proxy-au-mockserver/au-mockserver/merchant-api/bni-non-snap/inquiry/full-paycode?param-1=value-contains-%24";
            String output = convertUrlToProxyAuMockServerOrViceVersa(input);
            String expected = "https://api-sit.doku.com/au-mockserver/merchant-api/bni-non-snap/inquiry/full-paycode?param-1=value-contains-%24";
            assertEquals(expected, output);
        }

        {
            String input = "https://api-sit.doku.com/gtw-api-mock-server/proxy-au-mockserver/au-mockserver/merchant-api/bni-non-snap/inquiry/full-paycode?param-1=value-contains-%24&validate-request-signature=false&generate-response-signature=true&secret-key=SK-ChangeThis";
            String output = convertUrlToProxyAuMockServerOrViceVersa(input);
            String expected = "https://api-sit.doku.com/au-mockserver/merchant-api/bni-non-snap/inquiry/full-paycode?param-1=value-contains-%24";
            assertEquals(expected, output);
        }
    }

    @Test
    void testFromAuMockServerSvcWithEmptyUrlParams()
    {
        String input = "http://au-mockserver.techno-sit.svc:1080/merchant-api/bni-non-snap/inquiry/full-paycode";
        String output = convertUrlToProxyAuMockServerOrViceVersa(input);
        String expected = "http://gtw-api-mock-server.jokul-gateway-sit.svc:8080/gtw-api-mock-server/proxy-au-mockserver/merchant-api/bni-non-snap/inquiry/full-paycode?validate-request-signature=false&generate-response-signature=true&secret-key=SK-ChangeThis";
        assertEquals(expected, output);
    }

    @Test
    void testFromAuMockServerSvcWithNonEmptyUrlParams()
    {
        String input = "http://au-mockserver.techno-sit.svc:1080/merchant-api/bni-non-snap/inquiry/full-paycode?param-1=value-contains-%24";
        String output = convertUrlToProxyAuMockServerOrViceVersa(input);
        String expected = "http://gtw-api-mock-server.jokul-gateway-sit.svc:8080/gtw-api-mock-server/proxy-au-mockserver/merchant-api/bni-non-snap/inquiry/full-paycode?param-1=value-contains-%24&validate-request-signature=false&generate-response-signature=true&secret-key=SK-ChangeThis";
        assertEquals(expected, output);
    }

    @Test
    void testFromGtwApiMockServerSvcWithEmptyUrlParams()
    {
        String input = "http://gtw-api-mock-server.jokul-gateway-uat.svc:8080/gtw-api-mock-server/proxy-au-mockserver/merchant-api/bni-non-snap/inquiry/full-paycode";
        String output = convertUrlToProxyAuMockServerOrViceVersa(input);
        String expected = "http://au-mockserver.techno-uat.svc:1080/merchant-api/bni-non-snap/inquiry/full-paycode";
        assertEquals(expected, output);
    }

    @Test
    void testFromGtwApiMockServerSvcWithNonEmptyUrlParams()
    {
        {
            String input = "http://gtw-api-mock-server.jokul-gateway-uat.svc:8080/gtw-api-mock-server/proxy-au-mockserver/merchant-api/bni-non-snap/inquiry/full-paycode?param-1=value-contains-%24";
            String output = convertUrlToProxyAuMockServerOrViceVersa(input);
            String expected = "http://au-mockserver.techno-uat.svc:1080/merchant-api/bni-non-snap/inquiry/full-paycode?param-1=value-contains-%24";
            assertEquals(expected, output);
        }

        {
            String input = "http://gtw-api-mock-server.jokul-gateway-uat.svc:8080/gtw-api-mock-server/proxy-au-mockserver/merchant-api/bni-non-snap/inquiry/full-paycode?param-1=value-contains-%24&validate-request-signature=false&generate-response-signature=true&secret-key=SK-ChangeThis";
            String output = convertUrlToProxyAuMockServerOrViceVersa(input);
            String expected = "http://au-mockserver.techno-uat.svc:1080/merchant-api/bni-non-snap/inquiry/full-paycode?param-1=value-contains-%24";
            assertEquals(expected, output);
        }
    }
}
