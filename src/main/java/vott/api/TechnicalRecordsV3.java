package vott.api;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import vott.config.VottConfiguration;
import vott.json.GsonInstance;
import vott.models.dto.techrecordsv3.TechRecordV3;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

public class TechnicalRecordsV3 {

    private static final VottConfiguration configuration = VottConfiguration.local();
    private static final String apiKey = configuration.getApiKeys().getEnquiryServiceApiKey();
    private static final Gson gson = GsonInstance.get();
    public static final String STATUS_CODE_KEY = "statusCode";
    public static final String RESPONSE_BODY_KEY = "responseBody";

    public static Map<String,String> postTechnicalRecordV3ObjectResponse(TechRecordV3 techRecord, String token){
        RESTAssuredBasePostURI();

        String techRecordJson = gson.toJson(techRecord);

        Response response;
        int statusCode;

        int tries = 0;
        int maxRetries = 3;
        do {
            response = givenAuth(token, apiKey)
                    .body(techRecordJson)
                    .post().thenReturn();
            statusCode = response.statusCode();
            tries++;
        } while (statusCode >= 500 && tries < maxRetries);
        
        Map<String,String> outcome = new HashMap<>();
        outcome.put(STATUS_CODE_KEY, Integer.toString(statusCode));
        outcome.put(RESPONSE_BODY_KEY, response != null ? response.getBody().asString() : null);
        return outcome;
    }


    public static Map<String,String> updateTechnicalRecord(TechRecordV3 techRecord, String token, String systemNumber, String createdTimestamp){
        
        RESTAssuredBasePutURI(systemNumber,createdTimestamp);
        String techRecordJson = gson.toJson(techRecord);

        Response response;
        int statusCode;

        int tries = 0;
        int maxRetries = 3;
        do {
            response = givenAuth(token, apiKey)
                    .body(techRecordJson)
                    .patch().thenReturn();
            statusCode = response.statusCode();
            tries++;
        } while (statusCode >= 500 && tries < maxRetries);
        
        Map<String,String> outcome = new HashMap<>();
        outcome.put("statusCode", Integer.toString(statusCode));
        outcome.put("responseBody", response != null ? response.getBody().asString() : null);
        return outcome;
    }

    public static Map<String,String> getTechnicalRecord(String token, String systemNumber, String createdTimestamp){
        
        RESTAssuredBasePutURI(systemNumber,createdTimestamp);
        Response response;
        int statusCode;

        int tries = 0;
        int maxRetries = 3;
        do {
            response = givenAuth(token, apiKey)
                    .get().thenReturn();
            statusCode = response.statusCode();
            tries++;
        } while (statusCode >= 500 && tries < maxRetries);
        
        Map<String,String> outcome = new HashMap<>();
        outcome.put("statusCode", Integer.toString(statusCode));
        outcome.put("responseBody", response != null ? response.getBody().asString() : null);
        return outcome;
    }


    public static int postTechnicalRecordV3Object(TechRecordV3 techRecord, String token){
        RESTAssuredBasePostURI();

        String techRecordJson = gson.toJson(techRecord);

        Response response;
        int statusCode;

        int tries = 0;
        int maxRetries = 3;
        do {
            response = givenAuth(token, apiKey)
                    .body(techRecordJson)
                    .post().thenReturn();
            statusCode = response.statusCode();
            tries++;
        } while (statusCode >= 500 && tries < maxRetries);
        return statusCode;
    }

    private static void RESTAssuredBasePostURI(){
        RestAssured.baseURI = configuration.getApiProperties().getBranchSpecificUrl() + "/v3/technical-records";
    }

    private static void RESTAssuredBasePutURI(String systemNumber, String createdTimestamp){
        RestAssured.baseURI = configuration.getApiProperties().getBranchSpecificUrl() + "/v3/technical-records/" + systemNumber + "/" + createdTimestamp ;
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
