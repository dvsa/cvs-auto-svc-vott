package vott.e2e;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.annotations.WithTag;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testcontainers.shaded.com.google.common.reflect.TypeToken;
import vott.api.TestResultAPI;
import vott.api.VehiclesAPI;
import vott.auth.GrantType;
import vott.auth.OAuthVersion;
import vott.auth.TokenService;
import vott.config.VottConfiguration;
import vott.database.TestResultRepository;
import vott.database.VehicleRepository;
import vott.database.connection.ConnectionFactory;
import vott.database.sqlgeneration.SqlGenerator;
import vott.json.GsonInstance;
import vott.models.dto.enquiry.TestResult;
import vott.models.dto.techrecords.TechRecordPOST;
import vott.models.dto.testresults.CompleteTestResults;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.with;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static vott.e2e.RestAssuredAuthenticated.givenAuth;

@RunWith(SerenityRunner.class)
public class E2eTest {

    private VottConfiguration configuration;

    private Gson gson;

    private FieldGenerator fieldGenerator;

    private TokenService v1ImplicitTokens;

    private VehicleRepository vehicleRepository;
    private TestResultRepository testResultRepository;

    private static final int WAIT_IN_SECONDS = 60;

    @Before
    public void setUp() throws Exception {
        configuration = VottConfiguration.local();

        gson = GsonInstance.get();

        fieldGenerator = new FieldGenerator();

        v1ImplicitTokens = new TokenService(OAuthVersion.V1, GrantType.IMPLICIT);

        ConnectionFactory connectionFactory = new ConnectionFactory(VottConfiguration.local());

        vehicleRepository = new VehicleRepository(connectionFactory);

        testResultRepository = new TestResultRepository(connectionFactory);
    }

    @WithTag("Vott")
    @Title("VOTT-10 - AC1 - TC1 - End to End for HGV")
    @Test
    public void e2eTestHgv() {
        TechRecordPOST hgvTechRecord = hgvTechRecord();
        CompleteTestResults hgvTestResult = hgvTestResult(hgvTechRecord);

        e2eTest(hgvTechRecord, hgvTestResult);
    }

    @WithTag("Vott")
    @Title("VOTT-10 - AC1 - TC2 - End to End Test for PSV")
    @Test
    public void e2eTestPsv() {
        TechRecordPOST psvTechRecord = psvTechRecord();
        CompleteTestResults psvTestResult = psvTestResult(psvTechRecord);

        e2eTest(psvTechRecord, psvTestResult);
    }

    @WithTag("Vott")
    @Title("VOTT-10 - AC1 - TC3 - End to End Test for Trailers ")
    @Test
    public void e2eTestTrl() {
        TechRecordPOST trlTechRecord = trlTechRecord();
        CompleteTestResults trlTestResult = trlTestResult(trlTechRecord);

        e2eTest(trlTechRecord, trlTestResult);
    }

    private void e2eTest(TechRecordPOST techRecord, CompleteTestResults testResult) {
        VehiclesAPI.postVehicleTechnicalRecord(techRecord, v1ImplicitTokens.getBearerToken());
        TestResultAPI.postTestResult(testResult, v1ImplicitTokens.getBearerToken());

        String vin = testResult.getVin();

        with().timeout(Duration.ofSeconds(WAIT_IN_SECONDS)).await().until(SqlGenerator.vehicleIsPresentInDatabase(vin, vehicleRepository));
        with().timeout(Duration.ofSeconds(WAIT_IN_SECONDS)).await().until(SqlGenerator.testResultIsPresentInDatabase(vin, testResultRepository));

        vott.models.dto.enquiry.Vehicle actualVehicle = retrieveVehicle(vin);
        List<TestResult> actualTestResults = retrieveTestResults(vin);

        assertNotNull(actualVehicle);
        assertEquals(vin, actualVehicle.getVin());

        assertNotNull(actualTestResults);
        assertThat(actualTestResults).hasSize(1);

        // match on arbitrary field tester name, which we have previously set to a UUID in matchKeys
        assertEquals(testResult.getTesterName(), actualTestResults.get(0).getTester().getName());
    }

    private TechRecordPOST hgvTechRecord() {
        return randomizeKeys(readTechRecord("src/main/resources/payloads/technical-records_hgv.json"));
    }

    private TechRecordPOST psvTechRecord() {
        return randomizeKeys(readTechRecord("src/main/resources/payloads/technical-records_psv.json"));
    }

    private TechRecordPOST trlTechRecord() {
        return randomizeKeys(readTechRecord("src/main/resources/payloads/technical-records_trl.json"));
    }

    private CompleteTestResults hgvTestResult(TechRecordPOST techRecord) {
        return matchKeys(techRecord, readTestResult("src/main/resources/payloads/test-results_hgv.json"));
    }

    private CompleteTestResults psvTestResult(TechRecordPOST techRecord) {
        return matchKeys(techRecord, readTestResult("src/main/resources/payloads/test-results_psv.json"));
    }

    private CompleteTestResults trlTestResult(TechRecordPOST techRecord) {
        return matchKeys(techRecord, readTestResult("src/main/resources/payloads/test-results_trl.json"));
    }

    @SneakyThrows(IOException.class)
    private TechRecordPOST readTechRecord(String path) {
        return gson.fromJson(
                Files.newBufferedReader(Paths.get(path)),
                TechRecordPOST.class
        );
    }

    @SneakyThrows(IOException.class)
    private CompleteTestResults readTestResult(String path) {
        return gson.fromJson(
                Files.newBufferedReader(Paths.get(path)),
                CompleteTestResults.class
        );
    }

    private TechRecordPOST randomizeKeys(TechRecordPOST techRecord) {
        String vin = fieldGenerator.randomVin();

        techRecord.setVin(vin);

        return techRecord;
    }

    private CompleteTestResults matchKeys(TechRecordPOST techRecord, CompleteTestResults testResult) {
        testResult.setTestResultId(UUID.randomUUID().toString());

        // test result ID is not kept on enquiry-service retrievals: need a way to uniquely identify within test suite
        testResult.setTesterName(UUID.randomUUID().toString());

        testResult.setVin(techRecord.getVin());

        return testResult;
    }

    private vott.models.dto.enquiry.Vehicle retrieveVehicle(String vinNumber) {
        String bearerToken = v1ImplicitTokens.getBearerToken();

        Response response = givenAuth(bearerToken, configuration.getApiKeys().getEnquiryServiceApiKey())
                .baseUri(configuration.getApiProperties().getBranchSpecificUrl())
                .accept(ContentType.JSON)
                .queryParam("vinNumber", vinNumber)
                .get("v1/enquiry/vehicle")
                .thenReturn();

        assertThat(response.statusCode()).isBetween(200, 300);

        return gson.fromJson(response.asString(), vott.models.dto.enquiry.Vehicle.class);
    }

    private List<TestResult> retrieveTestResults(String vinNumber) {
        String bearerToken = v1ImplicitTokens.getBearerToken();

        Response response = givenAuth(bearerToken, configuration.getApiKeys().getEnquiryServiceApiKey())
                .baseUri(configuration.getApiProperties().getBranchSpecificUrl())
                .accept(ContentType.JSON)
                .queryParam("vinNumber", vinNumber)
                .get("v1/enquiry/testResults")
                .thenReturn();

        assertThat(response.statusCode()).isBetween(200, 300);

        return gson.fromJson(response.asString(), new TypeToken<List<TestResult>>() {
        }.getType());
    }
}
