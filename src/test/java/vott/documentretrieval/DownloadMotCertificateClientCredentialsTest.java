package vott.documentretrieval;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import net.thucydides.core.annotations.Title;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import vott.auth.GrantType;
import vott.auth.OAuthVersion;
import vott.auth.TokenService;
import vott.config.VottConfiguration;
import vott.database.*;
import vott.database.connection.ConnectionFactory;
import vott.e2e.FieldGenerator;
import vott.json.GsonInstance;
import vott.models.dao.Vehicle;
import vott.models.dto.techrecords.TechRecordPOST;
import vott.models.dto.testresults.CompleteTestResults;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static vott.e2e.RestAssuredAuthenticated.givenAuth;

public class DownloadMotCertificateClientCredentialsTest {

    // Variable + Constant Test Data Setup
    private VottConfiguration configuration = VottConfiguration.local();
    private String token;
    private final String xApiKey = configuration.getApiKeys().getEnquiryServiceApiKey();

    private String validVINNumber = "";
    private String validTestNumber = "";
    private String invalidVINNumber = "T123456789";
    private String invalidTestNumber = "W00A00000";

    private Gson gson;
    private FieldGenerator fieldGenerator;
    private TokenService v1ImplicitTokens = new TokenService(OAuthVersion.V1, GrantType.IMPLICIT);

    private VehicleRepository vehicleRepository;
    private TestResultRepository testResultRepository;

    //Test Data
    private Integer vehiclePK;
    private Integer testResultPK;
    private Integer fuelEmissionPK;
    private Integer testStationPK;
    private Integer testerPK;
    private Integer vehicleClassPK;
    private Integer testTypePK;
    private Integer preparerPK;
    private Integer identityPK;

    private TestResultRepository testResultRepository;
    private VehicleRepository vehicleRepository;
    private FuelEmissionRepository fuelEmissionRepository;
    private TestStationRepository testStationRepository;
    private TesterRepository testerRepository;
    private VehicleClassRepository vehicleClassRepository;
    private TestTypeRepository testTypeRepository;
    private PreparerRepository preparerRepository;
    private IdentityRepository identityRepository;

    @Before
    public void setUp() throws Exception {

        this.token = new TokenService(OAuthVersion.V2, GrantType.CLIENT_CREDENTIALS).getBearerToken();
        RestAssured.baseURI = configuration.getApiProperties().getBranchSpecificUrl() + "/v1/document-retrieval";

        gson = GsonInstance.get();
        fieldGenerator = new FieldGenerator();

        TechRecordPOST techRecord = techRecord();
        CompleteTestResults testResult = testResult(techRecord);
        System.out.println("VIN: "+ testResult.getVin());

        postTechRecord(techRecord);
        postTestResult(testResult);

        ConnectionFactory connectionFactory = new ConnectionFactory(VottConfiguration.local());
        vehicleRepository = new VehicleRepository(connectionFactory);
        testResultRepository = new TestResultRepository(connectionFactory);

        validVINNumber = testResult.getVin();

        with().timeout(Duration.ofSeconds(30)).await().until(vehicleIsPresentInDatabase(validVINNumber));
        with().timeout(Duration.ofSeconds(30)).await().until(testResultIsPresentInDatabase(validVINNumber));

        validTestNumber = getTestNumber(validVINNumber);
    }

    @Title("CVSB-19156 - AC2 - TC1 - Happy Path - DownloadTestCertificateTest")
    @Test
    public void DownloadTestCertificateTest() throws InterruptedException {

        System.out.println("Test Certificate Client Creds Happy Path");
        System.out.println("Valid access token: " + token);

        int tries = 0;
        int maxRetries = 20;
        int statusCode;
        byte[] pdf;

        //Retrieve and save test certificate (pdf) as byteArray
        do {
            Response response =
                    givenAuth(token, xApiKey)
                            .header("content-type", "application/pdf")
                            .queryParam("vinNumber", validVINNumber)
                            .queryParam("testNumber", validTestNumber).

                            //send request
                                    when().//log().all().
                            get().
                            //verification
                                    then().//log().all().
                            extract().response();
            statusCode = response.statusCode();
            pdf = response.asByteArray();
            tries++;
            Thread.sleep(1000);
        } while (statusCode >= 400 && tries < maxRetries);

        assertEquals(statusCode, 200);

        //Save file in resources folder
        File file = new File("src/test/resources/DownloadedMotTestCertificates/TestCert.pdf");

        //Decode downloaded pdf
        try (FileOutputStream fos = new FileOutputStream(file)) {
            byte[] decoder = Base64.getDecoder().decode(pdf);
            fos.write(decoder);
            System.out.println("PDF File Saved");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Open downloaded pdf file in system default pdf viewer
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File("src/test/resources/DownloadedMotTestCertificates/TestCert.pdf");
                Desktop.getDesktop().open(myFile);
            } catch (FileNotFoundException ex) {
                System.out.println("File not found" + ex.getMessage());
            } catch (IOException ex) {
                System.out.println("No apps installed to open PDFs" + ex.getMessage());
            }
        }
    }

    @Title("CVSB-19156 - AC2 - TC2 - DownloadTestCertificateBadJwtTokenTest")
    @Test
    public void DownloadTestCertificateBadJwtTokenTest() {

        System.out.println("Test Certificate Client Creds Invalid Token");
        System.out.println("Using invalid token: " + token);

        //prep request
        givenAuth(token + 1, xApiKey)
                .header("content-type", "application/pdf")
                .queryParam("vinNumber", validVINNumber)
                .queryParam("testNumber", validTestNumber).

                //send request
                        when().//log().all().
                get().

                //verification
                        then().//log().all().
                statusCode(403).
                body("message", equalTo("User is not authorized to access this resource with an explicit deny"));
    }

    @Title("CVSB-19156 - AC2 - TC3 - DownloadTestCertificateNoJwtTokenTest")
    @Test
    public void DownloadTestCertificateNoJwtTokenTest() {

        //prep request
        given()//.log().all()
                .header("X-Api-Key", xApiKey)
                .header("content-type", "application/pdf")
                .queryParam("vinNumber", validVINNumber)
                .queryParam("testNumber", validTestNumber).

                //send request
                        when().//log().all().
                get().

                //verification
                        then().//log().all().
                statusCode(401).
                body("message", equalTo("Unauthorized"));
    }

    @Title("CVSB-19156 - AC2 - TC4 - DownloadTestCertificateNoVinNumberTest")
    @Test
    public void DownloadTestCertificateNoVinNumberTest() {

        System.out.println("Valid access token: " + token);

        //prep request
        givenAuth(token, xApiKey)
                .header("content-type", "application/pdf")
                .queryParam("testNumber", validTestNumber).

                //send request
                        when().//log().all().
                get().

                //verification
                        then().//log().all().
                statusCode(400);
    }

    @Title("CVSB-19156 - AC2 - TC5 - DownloadTestCertificateNoTestNumberTest")
    @Test
    public void DownloadTestCertificateNoTestNumberTest() {

        System.out.println("Valid access token: " + token);

        //prep request
        givenAuth(token, xApiKey)
                .header("content-type", "application/pdf")
                .queryParam("vinNumber", validVINNumber).

                //send request
                        when().//log().all().
                get().

                //verification
                        then().//log().all().
                statusCode(400);
    }

    @Title("CVSB-19156 - AC2 - TC6 - DownloadTestCertificateNoAPIKeyTest")
    @Test
    public void DownloadTestCertificateNoAPIKeyTest() {

        System.out.println("Valid access token " + token);

        //prep request
        givenAuth(token)
                .header("content-type", "application/pdf")
                .queryParam("testNumber", validTestNumber)
                .queryParam("vinNumber", validVINNumber).

                //send request
                        when().//log().all().
                get().

                //verification
                        then().//log().all().
                statusCode(403).
                body("message", equalTo("Forbidden"));
    }

    @Title("CVSB-19156 - AC2 - TC7 - DownloadTestCertificateInvalidAPIKeyTest")
    @Test
    public void DownloadTestCertificateInvalidAPIKeyTest() {

        System.out.println("Valid access token " + token);

        //prep request
        givenAuth(token, xApiKey + "badkey")
                .header("content-type", "application/pdf")
                .queryParam("testNumber", validTestNumber)
                .queryParam("vinNumber", validVINNumber).

                //send request
                        when().//log().all().
                get().

                //verification
                        then().//log().all().
                statusCode(403).
                body("message", equalTo("Forbidden"));
    }

    @Title("CVSB-19156 - AC2 - TC8 - DownloadTestCertificateTestNumberDoesntExistTest")
    @Test
    public void DownloadTestCertificateTestNumberDoesntExistTest() {

        System.out.println("Valid access token: " + token);

        //prep request
        givenAuth(token, xApiKey)
                .header("content-type", "application/pdf")
                .queryParam("vinNumber", validVINNumber)
                .queryParam("testNumber", invalidTestNumber).

                //send request
                        when().//log().all().
                get().

                //verification
                        then().//log().all().
                statusCode(404).
                body(equalTo("NoSuchKey"));
    }

    @Title("CVSB-19156 - AC2 - TC9 - DownloadTestCertificateNumericTestNumberTest")
    @Test
    public void DownloadTestCertificateNumericTestNumberTest() {

        System.out.println("Using valid token: " + token);

        //prep request
        givenAuth(token, xApiKey)
                .header("content-type", "application/pdf")
                .queryParam("vinNumber", validVINNumber)
                .queryParam("testNumber", "123456789").

                //send request
                        when().//log().all().
                get().

                //verification
                        then().//log().all().
                statusCode(400).
                body(equalTo("Test number is in incorrect format"));
    }

    @Title("CVSB-19156 - AC2 - TC10 - DownloadTestCertificateVinNumberDoesntExistTest")
    @Test
    public void DownloadTestCertificateVinNumberDoesntExistTest() {

        System.out.println("Valid access token: " + token);

        //prep request
        givenAuth(token, xApiKey)
                .header("content-type", "application/pdf")
                .queryParam("vinNumber", invalidVINNumber)
                .queryParam("testNumber", validTestNumber).

                //send request
                        when().//log().all().
                get().

                //verification
                        then().//log().all().
                statusCode(404).
                body(equalTo("NoSuchKey"));
    }

    @Title("CVSB-19156 - AC2 - TC11 - DownloadTestCertificateNumericVINNumberTest")
    @Test
    public void DownloadTestCertificateNumericVINNumberTest() {

        System.out.println("Using valid token: " + token);

        //prep request
        givenAuth(token, xApiKey)
                .header("content-type", "application/pdf")
                .queryParam("vinNumber", "123456789")
                .queryParam("testNumber", validTestNumber).

                //send request
                        when().//log().all().
                get().

                //verification
                        then().//log().all().
                statusCode(404).
                body(equalTo("NoSuchKey"));
    }

    @Title("CVSB-19156 - AC2 - TC12 - DownloadTestCertificateVinNumberSpecialCharsTest")
    @Test
    public void DownloadTestCertificateVinNumberSpecialCharsTest() {

        System.out.println("Valid access token: " + token);

        //prep request
        givenAuth(token, xApiKey)
                .header("content-type", "application/pdf")
                .queryParam("vinNumber", "T12765@!'") //https://www.oreilly.com/library/view/java-cookbook/0596001703/ch03s12.html
                .queryParam("testNumber", validTestNumber).

                //send request
                        when().//log().all().
                get().

                //verification
                        then().//log().all().
                statusCode(400).
                body(equalTo("VIN is in incorrect format"));
    }

    @Title("CVSB-19156 - AC2 - TC13 - DownloadTestCertificateTestNumberSpecialCharsTest")
    @Test
    public void DownloadTestCertificateTestNumberSpecialCharsTest() {

        System.out.println("Valid access token: " + token);

        //prep request
        givenAuth(token, xApiKey)
                .header("content-type", "application/pdf")
                .queryParam("vinNumber", validVINNumber) //https://www.oreilly.com/library/view/java-cookbook/0596001703/ch03s12.html
                .queryParam("testNumber", "123Abc@!/").

                //send request
                        when().//log().all().
                get().

                //verification
                        then().//log().all().
                statusCode(400).
                body(equalTo("Test number is in incorrect format"));
    }

    @Title("CVSB-19156 - AC2 - TC14 - DownloadTestCertificatePostRequestTest")
    @Test
    public void DownloadTestCertificatePostRequestTest() {

        System.out.println("Valid access token " + token);

        //prep request
        givenAuth(token, xApiKey)
                .header("content-type", "application/pdf")
                .queryParam("vinNumber", validVINNumber)
                .queryParam("testNumber", validTestNumber).

                //send request
                        when().//log().all().
                post().
                //verification
                        then().//log().all().
                statusCode(405);
    }

    @Title("CVSB-19156 - AC2 - TC15 - DownloadTestCertificatePutRequestTest")
    @Test
    public void DownloadTestCertificatePutRequestTest() {

        System.out.println("Valid access token " + token);

        //prep request
        givenAuth(token, xApiKey)
                .header("content-type", "application/pdf")
                .queryParam("vinNumber", validVINNumber)
                .queryParam("testNumber", validTestNumber).

                //send request
                        when().//log().all().
                put().
                //verification
                        then().//log().all().
                statusCode(405);
    }

    @Title("CVSB-19156 - AC2 - TC16 - DownloadTestCertificatePatchRequestTest")
    @Test
    public void DownloadTestCertificatePatchRequestTest() {

        System.out.println("Valid access token: " + token);

        //prep request
        givenAuth(token, xApiKey)
                .header("content-type", "application/pdf")
                .queryParam("vinNumber", validVINNumber)
                .queryParam("testNumber", validTestNumber).

                //send request
                        when().//log().all().
                patch().
                //verification
                        then().//log().all().
                statusCode(405);
    }

    @Title("CVSB-19156 - AC2 - TC17 - DownloadTestCertificateDeleteRequestTest")
    @Test
    public void DownloadTestCertificateDeleteRequestTest() {

        System.out.println("Valid access token " + token);

        //prep request
        givenAuth(token, xApiKey)
                .header("content-type", "application/pdf")
                .queryParam("vinNumber", validVINNumber)
                .queryParam("testNumber", validTestNumber).

                //send request
                        when().//log().all().
                delete().
                //verification
                        then().//log().all().
                statusCode(405);
    }

    private void postTestResult(CompleteTestResults testResult) {
        String testResultJson = gson.toJson(testResult);

        Response response;
        int statusCode;

        int tries = 0;
        int maxRetries = 3;
        do {
            response = givenAuth(v1ImplicitTokens.getBearerToken())
                    .baseUri(configuration.getApiProperties().getBranchSpecificUrl())
                    .body(testResultJson)
                    .post("/test-results")
                    .thenReturn();
            statusCode = response.statusCode();
            tries++;
        } while (statusCode >= 500 && tries < maxRetries);

        assertThat(response.statusCode()).isBetween(200, 300);
    }

    private void postTechRecord(TechRecordPOST techRecord) {
        String techRecordJson = gson.toJson(techRecord);

        Response response;
        int statusCode;

        int tries = 0;
        int maxRetries = 3;
        do {
            response = givenAuth(v1ImplicitTokens.getBearerToken())
                    .baseUri(configuration.getApiProperties().getBranchSpecificUrl())
                    .body(techRecordJson)
                    .post("/vehicles")
                    .thenReturn();
            System.out.println(response);
            statusCode = response.statusCode();
            tries++;
        } while (statusCode >= 500 && tries < maxRetries);

        assertThat(response.statusCode()).isBetween(200, 300);
    }

    private CompleteTestResults testResult(TechRecordPOST techRecord) {
        return matchKeys(techRecord, readTestResult("src/test/resources/test-results-user-auth-doc-retrieval.json"));
    }

    private TechRecordPOST techRecord() {
        return randomizeKeys(readTechRecord("src/test/resources/technical-record-user-auth-doc-retrieval.json"));
    }

    private TechRecordPOST randomizeKeys(TechRecordPOST techRecord) {
        String vin = fieldGenerator.randomVin();

        techRecord.setVin(vin);

        return techRecord;
    }

    private CompleteTestResults matchKeys(TechRecordPOST techRecord, CompleteTestResults testResult) {
        testResult.setTestResultId(UUID.randomUUID().toString());
        System.out.println("Test Result ID: "+ testResult.getTestResultId());
        testResult.setTesterName(UUID.randomUUID().toString());
        testResult.setVin(techRecord.getVin());

        return testResult;
    }

    @SneakyThrows(IOException.class)
    private CompleteTestResults readTestResult(String path) {
        return gson.fromJson(
                Files.newBufferedReader(Paths.get(path)),
                CompleteTestResults.class
        );
    }

    @SneakyThrows(IOException.class)
    private TechRecordPOST readTechRecord(String path) {
        return gson.fromJson(
                Files.newBufferedReader(Paths.get(path)),
                TechRecordPOST.class
        );
    }

    private Callable<Boolean> vehicleIsPresentInDatabase(String vin) {
        return () -> {
            List<Vehicle> vehicles = vehicleRepository.select(String.format("SELECT * FROM `vehicle` WHERE `vin` = '%s'", vin));
            return !vehicles.isEmpty();
        };
    }

    private Callable<Boolean> testResultIsPresentInDatabase(String vin) {
        return () -> {
            List<vott.models.dao.TestResult> testResults = testResultRepository.select(String.format(
                    "SELECT `test_result`.*\n"
                            + "FROM `vehicle`\n"
                            + "JOIN `test_result`\n"
                            + "ON `test_result`.`vehicle_id` = `vehicle`.`id`\n"
                            + "WHERE `vehicle`.`vin` = '%s'", vin
            ));
            return !testResults.isEmpty();
        };
    }

    private String getTestNumber(String vin) {
        List<vott.models.dao.TestResult> testResults = testResultRepository.select(String.format(
                "SELECT `test_result`.*\n"
                        + "FROM `vehicle`\n"
                        + "JOIN `test_result`\n"
                        + "ON `test_result`.`vehicle_id` = `vehicle`.`id`\n"
                        + "WHERE `vehicle`.`vin` = '%s'", vin
        ));

        return testResults.get(0).getTestNumber();
    }
}
