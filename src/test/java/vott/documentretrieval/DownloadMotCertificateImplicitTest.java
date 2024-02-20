package vott.documentretrieval;

import com.google.gson.Gson;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.annotations.WithTag;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import vott.api.*;
import vott.auth.GrantType;
import vott.auth.OAuthVersion;
import vott.auth.TokenService;
import vott.config.VottConfiguration;
import vott.database.*;
import vott.database.connection.ConnectionFactory;
import vott.database.sqlgeneration.SqlGenerator;
import vott.json.GsonInstance;
import vott.models.dto.enquiry.Vehicle;
import vott.models.dto.seeddata.TechRecordHgvCompleteGenerator;
import vott.models.dto.techrecordsv3.TechRecordHgvComplete;
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

import static org.awaitility.Awaitility.with;
import static org.junit.Assert.assertEquals;

@RunWith(SerenityRunner.class)
public class DownloadMotCertificateImplicitTest {

    // Variable + Constant Test Data Setup
    private String token;

    private String validVIN = "";
    private String validTestNumber = "";

    private final Gson gson = GsonInstance.get();
    private final TokenService v1ImplicitTokens = new TokenService(OAuthVersion.V1, GrantType.IMPLICIT);

    private final ConnectionFactory connectionFactory = new ConnectionFactory(VottConfiguration.local());
    private final TestResultRepository testResultRepository = new TestResultRepository(connectionFactory);
    private final VehicleRepository vehicleRepository = new VehicleRepository(connectionFactory);

    private static final int WAIT_IN_SECONDS = 60;

    @Before
    public void Setup() {

        this.token = new TokenService(OAuthVersion.V1, GrantType.IMPLICIT).getBearerToken();

        TechRecordHgvCompleteGenerator hgv_trg = new TechRecordHgvCompleteGenerator(new TechRecordHgvComplete());
        TechRecordHgvComplete techRecord = hgv_trg.createTechRecordFromJsonFile("src/main/resources/payloads/TechRecordsV3/HGV_2_Axel_Tech_Record_Annual_Test.json");
        TechnicalRecordsV3.postTechnicalRecordV3Object(techRecord, v1ImplicitTokens.getBearerToken());
        validVIN = techRecord.getVin();
        with().timeout(Duration.ofSeconds(WAIT_IN_SECONDS)).await().until(SqlGenerator.vehicleIsPresentInDatabase(validVIN, vehicleRepository));
        Vehicle vehicle = VehicleDataAPI.getVehicleByVIN(validVIN, v1ImplicitTokens.getBearerToken());
        techRecord.setSystemNumber(vehicle.getSystemNumber()); 

        CompleteTestResults testResult = testResult(techRecord);
        TestResultAPI.postTestResult(testResult, v1ImplicitTokens.getBearerToken());

        with().timeout(Duration.ofSeconds(WAIT_IN_SECONDS)).await().until(SqlGenerator.testResultIsPresentInDatabase(validVIN, testResultRepository));
        validTestNumber = getTestNumber(validVIN);
    }

    @WithTag("Vott")
    @Title("VOTT-5 - AC1 - TC1 - Happy Path - Download Test Certificate Using Implicit JWT Token")
    @Test
    public void DownloadTestCertificateTest() throws InterruptedException {

        int tries = 0;
        int maxRetries = 20;
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

    @WithTag("Vott")
    @Title("VOTT-5 - AC1 - TC2 - Download Test Certificate Using a bad implicit JWT Token")
    @Test
    public void DownloadTestCertificateBadJwtTokenTest() {
        Response response = DocRetrievalAPI.getMOTCertUsingVINTestNumber(validVIN, validTestNumber, token+1);
        assertEquals(403, response.statusCode());
    }

    @WithTag("Vott")
    @Title("VOTT-5 - AC1 - TC3 - Download Test Certificate without an implicit JWT token")
    @Test
    public void DownloadTestCertificateNoJwtTokenTest() {
        Response response = DocRetrievalAPI.getMOTCertUsingVINTestNumberNoJWT(validVIN, validTestNumber);
        assertEquals(403, response.statusCode());
    }

    @WithTag("Vott")
    @Title("VOTT-5 - AC1 - TC4 - Download Test Certificate Using implicit JWT Token without providing a VIN")
    @Test
    public void DownloadTestCertificateNoVinNumberTest() {
        Response response = DocRetrievalAPI.getMOTCertUsingTestNumber(validTestNumber, token);
        assertEquals(400, response.statusCode());
    }

    @WithTag("Vott")
    @Title("VOTT-5 - AC1 - TC5 - Download Test Certificate Using implicit JWT Token without providing a test number")
    @Test
    public void DownloadTestCertificateNoTestNumberTest() {
        Response response = DocRetrievalAPI.getMOTCertUsingVIN(validVIN, token);
        assertEquals(400, response.statusCode());
    }

    @WithTag("Vott")
    @Title("VOTT-5 - AC1 - TC6 - Download Test Certificate Using Implicit JWT Token without providing an api key")
    @Test
    public void DownloadTestCertificateNoAPIKeyTest() {
        Response response = DocRetrievalAPI.getMOTCertNoAPIKey(validVIN, validTestNumber, token);
        assertEquals(403, response.statusCode());
    }

    @WithTag("Vott")
    @Title("VOTT-5 - AC1 - TC7 - Download Test Certificate Using Implicit JWT Token with wrong api key")
    @Test
    public void DownloadTestCertificateInvalidAPIKeyTest() {
        Response response = DocRetrievalAPI.getMOTCertInvalidAPIKey(validVIN, validTestNumber, token);
        assertEquals(403, response.statusCode());
    }

    @WithTag("Vott")
    @Title("VOTT-5 - AC1 - TC8 - Download Test Certificate Using Implicit JWT Token for a test number that doesn't exist in db")
    @Test
    public void DownloadTestCertificateTestNumberDoesntExistTest() {
        String invalidTestNumber = "W11A11111";
        Response response = DocRetrievalAPI.getMOTCertUsingVINTestNumber(validVIN, invalidTestNumber, token);
        assertEquals(404, response.statusCode());
        assertEquals("NoSuchKey", response.asString());
    }

    @WithTag("Vott")
    @Title("VOTT-5 - AC1 - TC9 - Download Test Certificate Using Implicit JWT Token with a wrong format test number (numeric only)")
    @Test
    public void DownloadTestCertificateNumericTestNumberTest() {
        String numericTestNumber = "123456789";
        Response response = DocRetrievalAPI.getMOTCertUsingVINTestNumber(validVIN, numericTestNumber, token);
        assertEquals(400, response.statusCode());
        assertEquals("Test number is in incorrect format", response.asString());
    }

    @WithTag("Vott")
    @Title("VOTT-5 - AC1 - TC10 - Download Test Certificate Using Implicit JWT Token for a vin number that doesn't exist in db")
    @Test
    public void DownloadTestCertificateVinNumberDoesntExistTest() {
        String invalidVIN = "T123456789";
        Response response = DocRetrievalAPI.getMOTCertUsingVINTestNumber(invalidVIN, validTestNumber, token);
        assertEquals(404, response.statusCode());
        assertEquals("NoSuchKey", response.asString());
    }

    @WithTag("Vott")
    @Title("VOTT-5 - AC1 - TC11 - Download Test Certificate Using Implicit JWT Token with a wrong format vin number (numeric only)")
    @Test
    public void DownloadTestCertificateNumericVINNumberTest() {
        String numericVIN = "123456789";
        Response response = DocRetrievalAPI.getMOTCertUsingVINTestNumber(numericVIN, validTestNumber, token);
        assertEquals(404, response.statusCode());
        assertEquals("NoSuchKey", response.asString());
    }

    @WithTag("Vott")
    @Title("VOTT-5 - AC1 - TC12 - Download Test Certificate Using Implicit JWT Token with a wrong format vin number (containing special chars)")
    @Test
    public void DownloadTestCertificateVinNumberSpecialCharsTest() {
        String specialCharVIN = "T12765@!'";
        Response response = DocRetrievalAPI.getMOTCertUsingVINTestNumber(specialCharVIN, validTestNumber, token);
        assertEquals(400, response.statusCode());
        assertEquals("VIN is in incorrect format", response.asString());
    }

    @WithTag("Vott")
    @Title("VOTT-5 - AC1 - TC13 - Download Test Certificate Using Implicit JWT Token with a wrong format test number (containing special chars)")
    @Test
    public void DownloadTestCertificateTestNumberSpecialCharsTest() {
        String specialCharTestNumber = "T12765@!'";
        Response response = DocRetrievalAPI.getMOTCertUsingVINTestNumber(validVIN, specialCharTestNumber, token);
        assertEquals(400, response.statusCode());
        assertEquals("Test number is in incorrect format", response.asString());
    }

    @WithTag("Vott")
    @Title("VOTT-5 - AC1 - TC14 - Test POST request for document retrieval service using implicit token")
    @Test
    public void DownloadTestCertificatePostRequestTest() {
        Response response = DocRetrievalAPI.postMOTCertUsingVINTestNumber(validVIN, validTestNumber, token);
        assertEquals(405, response.statusCode());
    }

    @WithTag("Vott")
    @Title("VOTT-5 - AC1 - TC15 - Test POST request for document retrieval service using implicit token")
    @Test
    public void DownloadTestCertificatePutRequestTest() {
        Response response = DocRetrievalAPI.putMOTCertUsingVINTestNumber(validVIN, validTestNumber, token);
        assertEquals(405, response.statusCode());
    }

    @WithTag("Vott")
    @Title("VOTT-5 - AC1 - TC16 - Test POST request for document retrieval service using implicit token")
    @Test
    public void DownloadTestCertificatePatchRequestTest() {
        Response response = DocRetrievalAPI.patchMOTCertUsingVINTestNumber(validVIN, validTestNumber, token);
        assertEquals(405, response.statusCode());
    }

    @WithTag("Vott")
    @Title("VOTT-5 - AC1 - TC17 - Test POST request for document retrieval service using implicit token")
    @Test
    public void DownloadTestCertificateDeleteRequestTest() {
        Response response = DocRetrievalAPI.deleteMOTCertUsingVINTestNumber(validVIN, validTestNumber, token);
        assertEquals(405, response.statusCode());
    }

    private CompleteTestResults testResult(TechRecordHgvComplete techRecord) {
        return matchKeys(techRecord, readTestResult("src/main/resources/payloads/test-results-user-auth-doc-retrieval.json"));
    }

    private CompleteTestResults matchKeys(TechRecordHgvComplete techRecord, CompleteTestResults testResult) {
        testResult.setTestResultId(UUID.randomUUID().toString());
        testResult.setTesterName(UUID.randomUUID().toString());
        testResult.setVin(techRecord.getVin());
        testResult.setSystemNumber(techRecord.getSystemNumber());

        return testResult;
    }

    @SneakyThrows(IOException.class)
    private CompleteTestResults readTestResult(String path) {
        return gson.fromJson(
                Files.newBufferedReader(Paths.get(path)),
                CompleteTestResults.class
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


