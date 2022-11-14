package vott.api;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import vott.config.VottConfiguration;
import vott.json.GsonInstance;
import vott.models.dto.enquiry.Vehicle;

import static io.restassured.RestAssured.given;

public class VehicleDataAPI {

    private static VottConfiguration configuration = VottConfiguration.local();
    private static String apiKey = configuration.getApiKeys().getEnquiryServiceApiKey();

    public static Response getVehicleDataUsingVIN(String vin, String token){
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

    public static Vehicle getVehicleObjectUsingVIN(String vin, String token){
        Response response = getVehicleDataUsingVIN(vin, token);

        Gson gson = GsonInstance.get();
        return gson.fromJson(response.asString(), Vehicle.class);
    }

    public static Response getVehicleDataUsingVRM(String vrm, String token){
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

    public static Response getVehicleDataNoParams(String token){
        RESTAssuredBaseURI();

        Response response = givenAuth(token, apiKey)
                .header("content-type", "application/json").
                //send request
                when().get().
                //verification
                then().extract().response();

        return response;
    }

    public static Response getVehicleDataUsingVIN_VRM(String vin, String vrm, String token){
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

    public static Response getVehicleDataNoAPIKey(String vin, String token){
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

    public static Response getVehicleDataInvalidAPIKey(String vin, String token){
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

    private static void RESTAssuredBaseURI(){
        RestAssured.baseURI = configuration.getApiProperties().getBranchSpecificUrl() + "/v1/enquiry/vehicle";
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
