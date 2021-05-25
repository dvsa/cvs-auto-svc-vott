package vott.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import vott.config.VottConfiguration;

import static io.restassured.RestAssured.given;

public class DocRetrievalAPI{

    private static VottConfiguration configuration = VottConfiguration.local();
    private static String apiKey = configuration.getApiKeys().getDocumentRetrievalApiKey();

    public static Response getMOTCertUsingVINTestNumber(String vin, String testNumber, String token){
        RESTAssuredBaseURI();

        Response response = givenAuth(token, apiKey)
                        .header("content-type", "application/pdf")
                        .queryParam("vinNumber", vin)
                        .queryParam("testNumber", testNumber).
                        //send request
                                when().get().
                        //verification
                                then().extract().response();

        return response;
    }

    public static Response getMOTCertUsingTestNumber(String testNumber, String token){
        RESTAssuredBaseURI();

        Response response = givenAuth(token, apiKey)
                .header("content-type", "application/pdf")
                .queryParam("testNumber", testNumber).
                //send request
                        when().get().
                //verification
                        then().extract().response();

        return response;
    }

    public static Response getMOTCertUsingVIN(String vin, String token){
        RESTAssuredBaseURI();

        Response response = givenAuth(token, apiKey)
                .header("content-type", "application/pdf")
                .queryParam("vinNumber", vin).
                //send request
                        when().get().
                //verification
                        then().extract().response();

        return response;
    }

    public static Response getMOTCertUsingVINTestNumberNoJWT(String vin, String testNumber){
        RESTAssuredBaseURI();

        Response response = givenAuth(apiKey)
                .header("content-type", "application/pdf")
                .queryParam("vinNumber", vin)
                .queryParam("testNumber", testNumber).
                //send request
                        when().get().
                //verification
                        then().extract().response();

        return response;
    }

    public static Response getMOTCertNoAPIKey(String vin, String testNumber, String token){
        RESTAssuredBaseURI();

        Response response = givenAuth(token)
                .header("content-type", "application/pdf")
                .queryParam("vinNumber", vin)
                .queryParam("testNumber", testNumber).
                //send request
                        when().get().
                //verification
                        then().extract().response();

        return response;
    }

    public static Response getMOTCertInvalidAPIKey(String vin, String testNumber, String token){
        RESTAssuredBaseURI();

        Response response = givenAuth(token, apiKey+"bad")
                .header("content-type", "application/pdf")
                .queryParam("vinNumber", vin)
                .queryParam("testNumber", testNumber).
                //send request
                        when().get().
                //verification
                        then().extract().response();

        return response;
    }

    public static Response postMOTCertUsingVINTestNumber(String vin, String testNumber, String token){
        RESTAssuredBaseURI();

        Response response = givenAuth(token, apiKey)
                .header("content-type", "application/pdf")
                .queryParam("vinNumber", vin)
                .queryParam("testNumber", testNumber).
                //send request
                        when().post().
                //verification
                        then().extract().response();

        return response;
    }

    public static Response putMOTCertUsingVINTestNumber(String vin, String testNumber, String token){
        RESTAssuredBaseURI();

        Response response = givenAuth(token, apiKey)
                .header("content-type", "application/pdf")
                .queryParam("vinNumber", vin)
                .queryParam("testNumber", testNumber).
                //send request
                        when().put().
                //verification
                        then().extract().response();

        return response;
    }

    public static Response patchMOTCertUsingVINTestNumber(String vin, String testNumber, String token){
        RESTAssuredBaseURI();

        Response response = givenAuth(token, apiKey)
                .header("content-type", "application/pdf")
                .queryParam("vinNumber", vin)
                .queryParam("testNumber", testNumber).
                //send request
                        when().patch().
                //verification
                        then().extract().response();

        return response;
    }

    public static Response deleteMOTCertUsingVINTestNumber(String vin, String testNumber, String token){
        RESTAssuredBaseURI();

        Response response = givenAuth(token, apiKey)
                .header("content-type", "application/pdf")
                .queryParam("vinNumber", vin)
                .queryParam("testNumber", testNumber).
                //send request
                        when().delete().
                //verification
                        then().extract().response();

        return response;
    }

    private static void RESTAssuredBaseURI(){
        RestAssured.baseURI = configuration.getApiProperties().getBranchSpecificUrl() + "/v1/document-retrieval";
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
