package vott.api;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import vott.config.VottConfiguration;
import vott.json.GsonInstance;
import vott.models.dto.testresults.CompleteTestResults;

import static io.restassured.RestAssured.given;

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
            //System.out.print(response.getBody().toString());
            tries++;
        } while (statusCode >= 500 && tries < maxRetries);
    }

    private static void RESTAssuredBaseURI() {
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
