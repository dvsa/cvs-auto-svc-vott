package vott.e2e;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Title;
import net.thucydides.core.annotations.WithTag;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testcontainers.shaded.com.google.common.reflect.TypeToken;
import vott.api.ActivitiesAPI;
import vott.api.TestResultAPI;
import vott.api.VehiclesAPI;
import vott.auth.GrantType;
import vott.auth.OAuthVersion;
import vott.auth.TokenService;
import vott.config.VottConfiguration;
import vott.database.*;
import vott.database.connection.ConnectionFactory;
import vott.database.seeddata.SeedData;
import vott.database.sqlgeneration.SqlGenerator;
import vott.json.GsonInstance;
import vott.models.dto.enquiry.TestResult;
import vott.models.dto.techrecords.TechRecordPOST;
import vott.models.dto.testresults.CompleteTestResults;
import vott.models.dto.activities.Activity;


import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.Random;
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
    private ActivityRepository activityRepository;
    private TesterRepository testerRepository;
    private TestStationRepository testStationRepository;
    private Integer testerId;
    private Integer staffId;
    private String parentId = "1";
    private Integer testStationId;
    @Before
    public void setUp() throws Exception {
        configuration = VottConfiguration.local();

        gson = GsonInstance.get();

        fieldGenerator = new FieldGenerator();

        v1ImplicitTokens = new TokenService(OAuthVersion.V1, GrantType.IMPLICIT);

        ConnectionFactory connectionFactory = new ConnectionFactory(VottConfiguration.local());

        vehicleRepository = new VehicleRepository(connectionFactory);

        testResultRepository = new TestResultRepository(connectionFactory);

        activityRepository = new ActivityRepository(connectionFactory);

        testerRepository = new TesterRepository(connectionFactory);

        testStationRepository = new TestStationRepository(connectionFactory);

        Random rand = new Random();
        staffId = rand.nextInt(99999);
        testerId = testerRepository.fullUpsert(SeedData.newTestTester(staffId.toString()));

        testStationId = testStationRepository.fullUpsert(SeedData.newTestTestStation());

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

    @WithTag("Vott")
    @Title("VOTT-999 - AC1 - TC3 - End to End Test for Trailers ")
    @Test
    public void e2eActivities() {
        Activity activity = siteVistActivity();
        activity.setTesterStaffId(staffId.toString());
        e2eTest(activity);

        activity = waitTimeActivity();
        activity.setTesterStaffId(staffId.toString());
        activity.setParentId(parentId);
        e2eTest(activity);

        activity = unallocatedTimeActivity();
        activity.setTesterStaffId(staffId.toString());
        activity.setParentId(parentId);
        e2eTest(activity);
    }

    private void e2eTest(TechRecordPOST techRecord, CompleteTestResults testResult) {
        VehiclesAPI.postVehicleTechnicalRecord(techRecord, v1ImplicitTokens.getBearerToken());
        TestResultAPI.postTestResult(testResult, v1ImplicitTokens.getBearerToken());

        String vin = testResult.getVin();

        with().timeout(Duration.ofSeconds(30)).await().until(SqlGenerator.vehicleIsPresentInDatabase(vin, vehicleRepository));
        with().timeout(Duration.ofSeconds(30)).await().until(SqlGenerator.testResultIsPresentInDatabase(vin, testResultRepository));

        vott.models.dto.enquiry.Vehicle actualVehicle = retrieveVehicle(vin);
        List<TestResult> actualTestResults = retrieveTestResults(vin);

        assertNotNull(actualVehicle);
        assertEquals(vin, actualVehicle.getVin());

        assertNotNull(actualTestResults);
        assertThat(actualTestResults).hasSize(1);

        // match on arbitrary field tester name, which we have previously set to a UUID in matchKeys
        assertEquals(testResult.getTesterName(), actualTestResults.get(0).getTester().getName());
    }

    private void e2eTest(Activity activity) {

        ActivitiesAPI.postActivity(activity, v1ImplicitTokens.getBearerToken());
        with().timeout(Duration.ofSeconds(30)).await().until(
                SqlGenerator.activityIsPresentInDatabase(testerId.toString(), activityRepository));
        vott.models.dao.Activity dbData =
                activityRepository.select("tester_id", testerId.toString(), "activityType", activity.getActivityType().toString());

        assertEquals(dbData.getTesterID(), testerId.toString());
        assertEquals(dbData.getTestStationID(), testStationId.toString());
        assertEquals(dbData.getActivityType(), activity.getActivityType().toString());
        assertEquals(dbData.getNotes(), activity.getNotes());
        assertNotNull(dbData.getActivityID());

        if (activity.getActivityType() == Activity.ActivityTypeEnum.VISIT){
            parentId = dbData.getActivityID();
        }
        else
        {
            assertEquals(dbData.getParentID(), parentId);
        }
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

    private Activity siteVistActivity() {
        return randomizeKeys(readActivityRecord("src/main/resources/payloads/activity-site-visit.json"));
    }

    private Activity unallocatedTimeActivity () {
        return randomizeKeys(readActivityRecord("src/main/resources/payloads/activity-unallocated.json"));
    }

    private Activity waitTimeActivity () {
        return randomizeKeys(readActivityRecord("src/main/resources/payloads/activity-wait-time.json"));
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

    @SneakyThrows(IOException.class)
    private Activity readActivityRecord(String path) {
        return gson.fromJson(
                Files.newBufferedReader(Paths.get(path)),
                Activity.class
        );
    }

    private TechRecordPOST randomizeKeys(TechRecordPOST techRecord) {
        String vin = fieldGenerator.randomVin();

        techRecord.setVin(vin);

        return techRecord;
    }

    private Activity randomizeKeys(Activity activity) {
        String activityID = fieldGenerator.randomActivityID();

        //activity.setActivityID(activityID);

        return activity;
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

        return gson.fromJson(response.asString(), new TypeToken<List<TestResult>>(){}.getType());
    }
}
