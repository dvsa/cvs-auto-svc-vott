package vott.documentretrieval;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Title;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import vott.api.DocRetrievalAPI;
import vott.api.TestResultAPI;
import vott.api.VehiclesAPI;
import vott.auth.GrantType;
import vott.auth.OAuthVersion;
import vott.auth.TokenService;
import vott.config.VottConfiguration;
import vott.database.*;
import vott.database.connection.ConnectionFactory;
import vott.database.sqlgeneration.SqlGenerator;
import vott.e2e.FieldGenerator;
import vott.json.GsonInstance;
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

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static vott.e2e.RestAssuredAuthenticated.givenAuth;

@RunWith(SerenityRunner.class)
public class DownloadMotCertificateClientCredentialsTest {

    // Variable + Constant Test Data Setup
    private final VottConfiguration configuration = VottConfiguration.local();
    private String token;

    private String validVIN = "";
    private String validTestNumber = "";

    private Gson gson;
    private FieldGenerator fieldGenerator;
    private final TokenService v1ImplicitTokens = new TokenService(OAuthVersion.V1, GrantType.IMPLICIT);

    private TestResultRepository testResultRepository;
    private VehicleRepository vehicleRepository;

    @Before
    public void setUp() throws Exception {

        this.token = new TokenService(OAuthVersion.V2, GrantType.CLIENT_CREDENTIALS).getBearerToken();

        gson = GsonInstance.get();
        fieldGenerator = new FieldGenerator();

        TechRecordPOST techRecord = techRecord();
        CompleteTestResults testResult = testResult(techRecord);

        VehiclesAPI.postVehicleTechnicalRecord(techRecord, v1ImplicitTokens.getBearerToken());
        TestResultAPI.postTestResult(testResult, v1ImplicitTokens.getBearerToken());

        ConnectionFactory connectionFactory = new ConnectionFactory(VottConfiguration.local());
        vehicleRepository = new VehicleRepository(connectionFactory);
        testResultRepository = new TestResultRepository(connectionFactory);

        validVIN = testResult.getVin();

        with().timeout(Duration.ofSeconds(30)).await().until(SqlGenerator.vehicleIsPresentInDatabase(validVIN, vehicleRepository));
        with().timeout(Duration.ofSeconds(30)).await().until(SqlGenerator.testResultIsPresentInDatabase(validVIN, testResultRepository));
        validTestNumber = getTestNumber(validVIN);
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
            Response response = DocRetrievalAPI.getMOTCertUsingVINTestNumber(validVIN, validTestNumber, token);
            statusCode = response.statusCode();
            pdf = response.asByteArray();
            tries++;
            Thread.sleep(2000);
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
        Response response = DocRetrievalAPI.getMOTCertUsingVINTestNumber(validVIN, validTestNumber, token+1);
        assertEquals(403, response.statusCode());
    }

    @Title("VOTT-5 - AC1 - TC20 - DownloadTestCertificateNoJwtTokenTest")
    @Test
    public void DownloadTestCertificateNoJwtTokenTest() {
        Response response = DocRetrievalAPI.getMOTCertUsingVINTestNumberNoJWT(validVIN, validTestNumber);
        assertEquals(401, response.statusCode());
    }

    @Title("VOTT-5 - AC1 - TC21 - DownloadTestCertificateNoVinNumberTest")
    @Test
    public void DownloadTestCertificateNoVinNumberTest() {
        Response response = DocRetrievalAPI.getMOTCertUsingTestNumber(validTestNumber, token);
        assertEquals(400, response.statusCode());
    }

    @Title("VOTT-5 - AC1 - TC22 - DownloadTestCertificateNoTestNumberTest")
    @Test
    public void DownloadTestCertificateNoTestNumberTest() {
        Response response = DocRetrievalAPI.getMOTCertUsingVIN(validVIN, token);
        assertEquals(400, response.statusCode());
    }

    @Title("VOTT-5 - AC1 - TC23 - DownloadTestCertificateNoAPIKeyTest")
    @Test
    public void DownloadTestCertificateNoAPIKeyTest() {
        Response response = DocRetrievalAPI.getMOTCertNoAPIKey(validVIN, validTestNumber, token);
        assertEquals(403, response.statusCode());
    }

    @Title("VOTT-5 - AC1 - TC24 - DownloadTestCertificateInvalidAPIKeyTest")
    @Test
    public void DownloadTestCertificateInvalidAPIKeyTest() {
        Response response = DocRetrievalAPI.getMOTCertInvalidAPIKey(validVIN, validTestNumber, token);
        assertEquals(403, response.statusCode());
    }

    @Title("VOTT-5 - AC1 - TC25 - DownloadTestCertificateTestNumberDoesntExistTest")
    @Test
    public void DownloadTestCertificateTestNumberDoesntExistTest() {
        String invalidTestNumber = "W00A00000";
        Response response = DocRetrievalAPI.getMOTCertUsingVINTestNumber(validVIN, invalidTestNumber, token);
        assertEquals(404, response.statusCode());
        assertEquals("NoSuchKey", response.asString());
    }

    @Title("VOTT-5 - AC1 - TC26 - DownloadTestCertificateNumericTestNumberTest")
    @Test
    public void DownloadTestCertificateNumericTestNumberTest() {
        String numericTestNumber = "123456789";
        Response response = DocRetrievalAPI.getMOTCertUsingVINTestNumber(validVIN, numericTestNumber, token);
        assertEquals(400, response.statusCode());
        assertEquals("Test number is in incorrect format", response.asString());
    }

    @Title("VOTT-5 - AC1 - TC27 - DownloadTestCertificateVinNumberDoesntExistTest")
    @Test
    public void DownloadTestCertificateVinNumberDoesntExistTest() {
        String invalidVIN = "T123456789";
        Response response = DocRetrievalAPI.getMOTCertUsingVINTestNumber(invalidVIN, validTestNumber, token);
        assertEquals(404, response.statusCode());
        assertEquals("NoSuchKey", response.asString());
    }

    @Title("VOTT-5 - AC1 - TC28 - DownloadTestCertificateNumericVINNumberTest")
    @Test
    public void DownloadTestCertificateNumericVINNumberTest() {
        String numericVIN = "123456789";
        Response response = DocRetrievalAPI.getMOTCertUsingVINTestNumber(numericVIN, validTestNumber, token);
        assertEquals(404, response.statusCode());
        assertEquals("NoSuchKey", response.asString());
    }

    @Title("VOTT-5 - AC1 - TC29 - DownloadTestCertificateVinNumberSpecialCharsTest")
    @Test
    public void DownloadTestCertificateVinNumberSpecialCharsTest() {
        String specialCharVIN = "T12765@!'";
        Response response = DocRetrievalAPI.getMOTCertUsingVINTestNumber(specialCharVIN, validTestNumber, token);
        assertEquals(400, response.statusCode());
        assertEquals("VIN is in incorrect format", response.asString());
    }

    @Title("VOTT-5 - AC1 - TC30 - DownloadTestCertificateTestNumberSpecialCharsTest")
    @Test
    public void DownloadTestCertificateTestNumberSpecialCharsTest() {
        String specialCharTestNumber = "T12765@!'";
        Response response = DocRetrievalAPI.getMOTCertUsingVINTestNumber(validVIN, specialCharTestNumber, token);
        assertEquals(400, response.statusCode());
        assertEquals("Test number is in incorrect format", response.asString());
    }

    @Title("VOTT-5 - AC1 - TC31 - DownloadTestCertificatePostRequestTest")
    @Test
    public void DownloadTestCertificatePostRequestTest() {
        Response response = DocRetrievalAPI.postMOTCertUsingVINTestNumber(validVIN, validTestNumber, token);
        assertEquals(405, response.statusCode());
    }

    @Title("VOTT-5 - AC1 - TC32 - DownloadTestCertificatePutRequestTest")
    @Test
    public void DownloadTestCertificatePutRequestTest() {
        Response response = DocRetrievalAPI.putMOTCertUsingVINTestNumber(validVIN, validTestNumber, token);
        assertEquals(405, response.statusCode());
    }

    @Title("VOTT-5 - AC1 - TC33 - DownloadTestCertificatePatchRequestTest")
    @Test
    public void DownloadTestCertificatePatchRequestTest() {
        Response response = DocRetrievalAPI.patchMOTCertUsingVINTestNumber(validVIN, validTestNumber, token);
        assertEquals(405, response.statusCode());
    }

    @Title("VOTT-5 - AC1 - TC34 - DownloadTestCertificateDeleteRequestTest")
    @Test
    public void DownloadTestCertificateDeleteRequestTest() {
        Response response = DocRetrievalAPI.deleteMOTCertUsingVINTestNumber(validVIN, validTestNumber, token);
        assertEquals(405, response.statusCode());
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
