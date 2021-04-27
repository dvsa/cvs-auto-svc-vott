package vott.documentretrieval;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Title;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

@RunWith(SerenityRunner.class)
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

    private TestResultRepository testResultRepository;
    private VehicleRepository vehicleRepository;

    @Before
    public void setUp() throws Exception {

        this.token = new TokenService(OAuthVersion.V2, GrantType.CLIENT_CREDENTIALS).getBearerToken();
        RestAssured.baseURI = configuration.getApiProperties().getBranchSpecificUrl() + "/v1/document-retrieval";

        gson = GsonInstance.get();
        fieldGenerator = new FieldGenerator();

        TechRecordPOST techRecord = techRecord();
        CompleteTestResults testResult = testResult(techRecord);

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

    @Title("VOTT-5 - AC1 - TC18 - Happy Path - Download Test Certificate Using Client Credentials generated JWT Token")
    @Test
    public void DownloadTestCertificateTest() throws InterruptedException {

        int tries = 0;
        int maxRetries = 30;
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

        assertEquals(200, statusCode);

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

    @Title("VOTT-5 - AC1 - TC19 - DownloadTestCertificateBadJwtTokenTest")
    @Test
    public void DownloadTestCertificateBadJwtTokenTest() {

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

    @Title("VOTT-5 - AC1 - TC20 - DownloadTestCertificateNoJwtTokenTest")
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

    @Title("VOTT-5 - AC1 - TC21 - DownloadTestCertificateNoVinNumberTest")
    @Test
    public void DownloadTestCertificateNoVinNumberTest() {


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

    @Title("VOTT-5 - AC1 - TC22 - DownloadTestCertificateNoTestNumberTest")
    @Test
    public void DownloadTestCertificateNoTestNumberTest() {


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

    @Title("VOTT-5 - AC1 - TC23 - DownloadTestCertificateNoAPIKeyTest")
    @Test
    public void DownloadTestCertificateNoAPIKeyTest() {


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

    @Title("VOTT-5 - AC1 - TC24 - DownloadTestCertificateInvalidAPIKeyTest")
    @Test
    public void DownloadTestCertificateInvalidAPIKeyTest() {

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

    @Title("VOTT-5 - AC1 - TC25 - DownloadTestCertificateTestNumberDoesntExistTest")
    @Test
    public void DownloadTestCertificateTestNumberDoesntExistTest() {

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

    @Title("VOTT-5 - AC1 - TC26 - DownloadTestCertificateNumericTestNumberTest")
    @Test
    public void DownloadTestCertificateNumericTestNumberTest() {


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

    @Title("VOTT-5 - AC1 - TC27 - DownloadTestCertificateVinNumberDoesntExistTest")
    @Test
    public void DownloadTestCertificateVinNumberDoesntExistTest() {

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

    @Title("VOTT-5 - AC1 - TC28 - DownloadTestCertificateNumericVINNumberTest")
    @Test
    public void DownloadTestCertificateNumericVINNumberTest() {

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

    @Title("VOTT-5 - AC1 - TC29 - DownloadTestCertificateVinNumberSpecialCharsTest")
    @Test
    public void DownloadTestCertificateVinNumberSpecialCharsTest() {


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

    @Title("VOTT-5 - AC1 - TC30 - DownloadTestCertificateTestNumberSpecialCharsTest")
    @Test
    public void DownloadTestCertificateTestNumberSpecialCharsTest() {


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

    @Title("VOTT-5 - AC1 - TC31 - DownloadTestCertificatePostRequestTest")
    @Test
    public void DownloadTestCertificatePostRequestTest() {


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

    @Title("VOTT-5 - AC1 - TC32 - DownloadTestCertificatePutRequestTest")
    @Test
    public void DownloadTestCertificatePutRequestTest() {

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

    @Title("VOTT-5 - AC1 - TC33 - DownloadTestCertificatePatchRequestTest")
    @Test
    public void DownloadTestCertificatePatchRequestTest() {

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

    @Title("VOTT-5 - AC1 - TC34 - DownloadTestCertificateDeleteRequestTest")
    @Test
    public void DownloadTestCertificateDeleteRequestTest() {

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
