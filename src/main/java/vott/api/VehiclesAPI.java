package vott.api;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import vott.config.VottConfiguration;
import vott.json.GsonInstance;
import vott.models.dto.techrecords.TechRecordPOST;

import static io.restassured.RestAssured.given;

public class VehiclesAPI {

    private static final VottConfiguration configuration = VottConfiguration.local();
    private static final String apiKey = configuration.getApiKeys().getEnquiryServiceApiKey();
    private static final Gson gson = GsonInstance.get();

    public static void postVehicleTechnicalRecord(TechRecordPOST techRecord, String token){
        RESTAssuredBaseURI();

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
    }

    private static void RESTAssuredBaseURI(){
        RestAssured.baseURI = configuration.getApiProperties().getBranchSpecificUrl() + "/vehicles";
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
