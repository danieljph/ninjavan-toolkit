package com.karyasarma.ninjavan_toolkit.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.karyasarma.ninjavan_toolkit.database.model.Order;
import com.karyasarma.ninjavan_toolkit.util.IntegerCounter;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

/**
 *
 * @author Daniel Joi Partogi Hutapea
 */
public class OperatorClient
{
    private static String API_BASE_URL = "https://api-qa.ninjavan.co/";
    private String accessToken;

    public OperatorClient(String countryCode)
    {
        RestAssured.useRelaxedHTTPSValidation();
        API_BASE_URL = API_BASE_URL + countryCode.toLowerCase();
    }

    public OperatorClient(String accessToken, String countryCode)
    {
        this.accessToken = accessToken;
        API_BASE_URL = API_BASE_URL + countryCode.toLowerCase();
    }

    public String login(String clientId, String clientSecret)
    {
        return getAccessToken(clientId, clientSecret);
    }

    public String getAccessToken(String clientId, String clientSecret)
    {
        String apiPath = "/auth/login?grant_type=client_credentials";

        ObjectNode requestBody = new ObjectMapper().createObjectNode();
        requestBody.put("clientId", clientId);
        requestBody.put("clientSecret", clientSecret);

        RequestSpecification spec = given()
                .contentType(ContentType.JSON)
                .body(requestBody);

        //spec.log().all();

        Response response = spec.when().post(API_BASE_URL+apiPath);
        //response.then().log().all();
        //response.then().assertThat().statusCode(200);

        JsonPath jsonPath = response.jsonPath();
        accessToken = jsonPath.get("accessToken");
        System.out.println("Access Token: "+accessToken);
        return accessToken;
    }

    public void forceSuccess(List<Order> listOfOrder)
    {
        String apiPath = "/core/orders/forcesuccess";

        for(Order order : listOfOrder)
        {
            long l1 = System.currentTimeMillis();

            RequestSpecification spec = given()
                    .header("Authorization", "Bearer "+accessToken)
                    .contentType(ContentType.JSON)
                    .body(String.format("[{\"orderId\":%d, \"withDriver\":true, \"codCollected\":true}]", order.getId()));

            //spec.log().all();

            Response response = spec.when().post(API_BASE_URL+apiPath);

            long l2 = System.currentTimeMillis();
            long duration = l2-l1;
            System.out.println(String.format("Force successing order with Tracking ID = '%s' in %dms", order.getTrackingId(), duration));

            //response.then().log().all();
            //response.then().assertThat().statusCode(200);
        }
    }

    public void forceSuccessFast(List<Order> listOfOrder)
    {
        String apiPath = "/core/orders/forcesuccess";
        int size = listOfOrder.size();
        IntegerCounter integerCounter = new IntegerCounter();

        ExecutorService executor = Executors.newFixedThreadPool(10);

        for(Order order : listOfOrder)
        {
            executor.execute(() ->
            {
                integerCounter.inc();
                int responseCode = -1;
                long l1 = System.currentTimeMillis();

                try
                {
                    URL url = new URL(API_BASE_URL+apiPath);
                    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                    connection.setConnectTimeout(3_000);
                    connection.setReadTimeout(3_000);
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setRequestProperty("Authorization", "Bearer "+accessToken);

                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                    wr.writeBytes(String.format("[{\"orderId\":%d, \"withDriver\":true, \"codCollected\":false}]", order.getId()));
                    wr.flush();
                    wr.close();
                    responseCode = connection.getResponseCode();

//                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                    String input;
//
//                    while((input=br.readLine())!=null)
//                    {
//                        System.out.println(input);
//                    }
                }
                catch(MalformedURLException ex)
                {
                    ex.printStackTrace();
                }
                catch(IOException ex)
                {
                    ex.printStackTrace();
                }

                long l2 = System.currentTimeMillis();
                long duration = l2-l1;
                int remainingOrder = size-integerCounter.getValue();

                //synchronized(integerCounter)
                {
                    //if(integerCounter.getValue()%20==0)
                    {
                        System.out.println(String.format("Force succeeding order (%d) with Tracking ID = '%s' in %,dms with response code %d. Remaining order(s): %,d", order.getId(), order.getTrackingId(), duration, responseCode, remainingOrder));
                    }
                }

            });
        }

        try
        {
            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.HOURS);
        }
        catch(Exception ex)
        {
            ex.printStackTrace(System.err);
        }
    }

    public void archiveRoute(List<Integer> listOfRouteIds)
    {
        int size = listOfRouteIds.size();
        int counter = 0;
        String apiPath = "/core/routes/details";

        for(Integer routeId : listOfRouteIds)
        {
            counter++;
            RequestSpecification spec = given()
                    .header("Authorization", "Bearer " + accessToken)
                    .contentType(ContentType.JSON)
                    .body(String.format("[{\"id\": %d,\"archived\":true}]", routeId));

            //spec.log().all();

            Response response = spec.when().put(API_BASE_URL + apiPath);
            response.then().log().all();
            int remainingOrder = size-counter;
            System.out.println(String.format("Archive route with ID = '%d' with response code %d. Remaining route(s): %d", routeId, response.getStatusCode(), remainingOrder));
        }
    }

    public void newArchiveRoute(List<Integer> listOfRouteIds)
    {
        int size = listOfRouteIds.size();
        int counter = 0;
        String apiPath = "/core/routes/archive";

        for(Integer routeId : listOfRouteIds)
        {
            counter++;
            RequestSpecification spec = given()
                    .header("Authorization", "Bearer " + accessToken)
                    .contentType(ContentType.JSON)
                    .body(String.format("[{\"routeIds\":[%d]}]", routeId));

            Response response = spec.when().put(API_BASE_URL + apiPath);
            //response.then().log().all();
            int remainingOrder = size-counter;
            System.out.println(String.format("Archive route with ID = '%d' with response code %d. Remaining route(s): %d", routeId, response.getStatusCode(), remainingOrder));
        }
    }
}
