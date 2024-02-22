package vott.api;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import vott.config.VottConfiguration;
import vott.json.GsonInstance;
import vott.models.dto.testresults.CompleteTestResults;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

public class TestResultAPI {
    private static final VottConfiguration configuration = VottConfiguration.local();
    private static final String apiKey = configuration.getApiKeys().getEnquiryServiceApiKey();
    private static final Gson gson = GsonInstance.get();

    public static void postTestResult(CompleteTestResults testResult, String token) {
        RESTAssuredBaseURI();

        String testResultJson = gson.toJson(testResult);

        Response response;
        int statusCode;

        int tries = 0;
        int maxRetries = 3;
        do {
            response = givenAuth(token, apiKey)
                    .body(testResultJson)
                    .post().thenReturn();
            statusCode = response.statusCode();
            System.out.println(response.getBody().asString());
            tries++;
        } while (statusCode >= 500 && tries < maxRetries);
    }

    public static Map<String,String> postTestResultReturnResult(CompleteTestResults testResult, String token) {
        RESTAssuredBaseURI();

        String testResultJson = gson.toJson(testResult);

        Response response;
        int statusCode;

        int tries = 0;
        int maxRetries = 3;
        do {
            response = givenAuth(token, apiKey)
                    .body(testResultJson)
                    .post().thenReturn();
            statusCode = response.statusCode();
          
            tries++;
        } while (statusCode >= 500 && tries < maxRetries);

        Map<String,String> outcome = new HashMap<>();
        outcome.put(TechnicalRecordsV3.STATUS_CODE_KEY, Integer.toString(statusCode));
        outcome.put(TechnicalRecordsV3.RESPONSE_BODY_KEY, response != null ? response.getBody().asString() : null);
        return outcome;


    }

    private static void RESTAssuredBaseURI(){
        RestAssured.baseURI = configuration.getApiProperties().getBranchSpecificUrl() + "/test-results";
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
