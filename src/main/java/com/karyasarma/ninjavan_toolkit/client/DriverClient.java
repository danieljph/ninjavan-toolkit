package com.karyasarma.ninjavan_toolkit.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

/**
 *
 * @author Daniel Joi Partogi Hutapea
 */
public class DriverClient
{
    private static final String API_BASE_URL = "https://api-qa.ninjavan.co/sg";
    private String accessToken;

    public DriverClient()
    {
        RestAssured.useRelaxedHTTPSValidation();
    }

    public DriverClient(String accessToken)
    {
        this.accessToken = accessToken;
    }

    public String login(String driverUsername, String driverPassword)
    {
        return getAccessToken(driverUsername, driverPassword);
    }

    public String getAccessToken(String driverUsername, String driverPassword)
    {
        String apiPath = "/driver/1.0/login";

        ObjectNode requestBody = new ObjectMapper().createObjectNode();
        requestBody.put("username", driverUsername);
        requestBody.put("password", driverPassword);

        RequestSpecification spec = given()
                .contentType(ContentType.JSON)
                .body(requestBody);

        Response response = spec.when().post(API_BASE_URL+apiPath);

        JsonPath jsonPath = response.jsonPath();
        accessToken = jsonPath.get("accessToken");
        System.out.println("Access Token: "+accessToken);
        return accessToken;
    }
}
