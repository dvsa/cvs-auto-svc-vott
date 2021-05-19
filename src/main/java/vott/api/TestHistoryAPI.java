package vott.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import vott.config.VottConfiguration;

import static io.restassured.RestAssured.*;

public class TestHistoryAPI {

    private static VottConfiguration configuration = VottConfiguration.local();
    private static String apiKey = configuration.getApiKeys().getEnquiryServiceApiKey();

    public static Response getTestHistoryUsingVIN(String vin, String token){
        RESTAssuredBaseURI();

        Response response = givenAuth(token, apiKey)
                                .header("content-type", "application/json")
                                .queryParam("vinNumber", vin).
                                //send request
                                when().get().
                                //verification
                                then().extract().response();
        
        return response;
    }

    public static Response getTestHistoryUsingVRM(String vrm, String token){
        RESTAssuredBaseURI();

        Response response = givenAuth(token, apiKey)
                .header("content-type", "application/json")
                .queryParam("VehicleRegMark", vrm).
                //send request
                        when().get().
                //verification
                        then().extract().response();

        return response;
    }

    // todo add request to retrieve single test history using testnumber
    public static Response getSpecificTestHistoryUsingTestNumber(String testnumber, String token){
        RESTAssuredBaseURI();

        Response response = givenAuth(token, apiKey)
                .header("content-type", "application/json")
                .queryParam("testnumber", testnumber).
                //send request
                        when().get().
                //verification
                        then().extract().response();

        return response;
    }

    public static Response getTestHistoryUsingVIN_VRM(String vin, String vrm, String token){
        RESTAssuredBaseURI();

        Response response = givenAuth(token, apiKey)
                .header("content-type", "application/json")
                .queryParam("vinNumber", vin)
                .queryParam("VehicleRegMark", vrm).
                //send request
                        when().get().
                //verification
                        then().extract().response();

        return response;
    }

    public static Response getTestHistoryNoAPIKey(String vin, String token){
        RESTAssuredBaseURI();

        Response response = givenAuth(token)
                .header("content-type", "application/json")
                .queryParam("vinNumber", vin).
                //send request
                        when().get().
                //verification
                        then().extract().response();

        return response;
    }

    public static Response getTestHistoryInvalidAPIKey(String vin, String token){
        RESTAssuredBaseURI();

        Response response = givenAuth(token, apiKey+"bad")
                .header("content-type", "application/json")
                .queryParam("vinNumber", vin).
                //send request
                        when().get().
                //verification
                        then().extract().response();

        return response;
    }

    public static Response getTestHistoryNoParams(String token){
        RESTAssuredBaseURI();

        Response response = givenAuth(token, apiKey)
                .header("content-type", "application/json").
                //send request
                        when().get().
                //verification
                        then().extract().response();

        return response;
    }

    private static void RESTAssuredBaseURI(){
        RestAssured.baseURI = configuration.getApiProperties().getBranchSpecificUrl() + "/v1/enquiry/testResults";
    }

    private static RequestSpecification givenAuth(String bearerToken) {
        return given()
                .header("Authorization", "Bearer " + bearerToken);
    }

    private static RequestSpecification givenAuth(String bearerToken, String apiKey) {
        return given()
                .header("Authorization", "Bearer " + bearerToken)
                .header("X-Api-Key", apiKey);
    }
}
