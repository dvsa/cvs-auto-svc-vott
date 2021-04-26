package vott.documentretrieval;

import io.restassured.RestAssured;
import net.thucydides.core.annotations.Title;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import vott.auth.GrantType;
import vott.auth.OAuthVersion;
import vott.auth.TokenService;
import vott.config.VottConfiguration;
import vott.config.VottConfiguration;
import vott.database.*;
import vott.database.connection.ConnectionFactory;
import vott.models.dao.*;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static vott.e2e.RestAssuredAuthenticated.givenAuth;

public class DownloadMotCertificateClientCredentialsTest {

    // Variable + Constant Test Data Setup
    private VottConfiguration configuration = VottConfiguration.local();
    private String token;
    private final String xApiKey = configuration.getApiKeys().getEnquiryServiceApiKey();

    private String validVINNumber = "";
    private String validTestNumber = "";
    private String invalidVINNumber = "T123456789";
    private String invalidTestNumber = "A0A00000";

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
    public void Setup() {
        ConnectionFactory connectionFactory = new ConnectionFactory(
                VottConfiguration.local()
        );

        //Test Data Upserts
        vehicleRepository = new VehicleRepository(connectionFactory);
        Vehicle vehicle = newTestVehicle();
        vehiclePK = vehicleRepository.fullUpsert(vehicle);

        fuelEmissionRepository = new FuelEmissionRepository(connectionFactory);
        fuelEmissionPK = fuelEmissionRepository.partialUpsert(newTestFuelEmission());

        testStationRepository = new TestStationRepository(connectionFactory);
        testStationPK = testStationRepository.partialUpsert(newTestTestStation());

        testerRepository = new TesterRepository(connectionFactory);
        testerPK = testerRepository.partialUpsert(newTestTester());

        vehicleClassRepository = new VehicleClassRepository(connectionFactory);
        vehicleClassPK = vehicleClassRepository.partialUpsert(newTestVehicleClass());

        testTypeRepository = new TestTypeRepository(connectionFactory);
        testTypePK = testTypeRepository.partialUpsert(newTestTestType());

        preparerRepository = new PreparerRepository(connectionFactory);
        preparerPK = preparerRepository.partialUpsert(newTestPreparer());

        identityRepository = new IdentityRepository(connectionFactory);
        identityPK = identityRepository.partialUpsert(newTestIdentity());

        testResultRepository = new TestResultRepository(connectionFactory);
        TestResult tr = newTestTestResult();
        testResultPK = testResultRepository.fullUpsert(tr);

        validVINNumber = vehicle.getVin();
        validTestNumber= tr.getTestNumber();

        this.token = new TokenService(OAuthVersion.V1, GrantType.CLIENT_CREDENTIALS).getBearerToken();
        RestAssured.baseURI = configuration.getApiProperties().getBranchSpecificUrl() + "/v1/document-retrieval";
    }

    @After
    public void tearDown() {
        testResultRepository.delete(testResultPK);
        vehicleRepository.delete(vehiclePK);
        fuelEmissionRepository.delete(fuelEmissionPK);
        testStationRepository.delete(testStationPK);
        testerRepository.delete(testerPK);
        vehicleClassRepository.delete(vehicleClassPK);
        testTypeRepository.delete(testTypePK);
        preparerRepository.delete(preparerPK);
        identityRepository.delete(identityPK);
    }

    @Title("VOTT-5 - AC1 - TC18 - Happy Path - Download Test Certificate Using Client Credentials generated JWT Token")
    @Test
    public void DownloadTestCertificateTest() {

        System.out.println("Test Certificate Client Creds Happy Path");
        System.out.println("Valid access token: " + token);

        //Retrieve and save test certificate (pdf) as byteArray
        byte[] pdf =
            givenAuth(token, xApiKey)
                        .header("content-type", "application/pdf")
                        .queryParam("vinNumber", validVINNumber)
                        .queryParam("testNumber", validTestNumber).

                        //send request
                                when().//log().all().
                        get().

                        //verification
                                then().//log().all().
                        statusCode(200).
                        extract().response().asByteArray();

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

    @Title("VOTT-5 - AC1 - TC22 - DownloadTestCertificateNoTestNumberTest")
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

    @Title("VOTT-5 - AC1 - TC23 - DownloadTestCertificateNoAPIKeyTest")
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

    @Title("VOTT-5 - AC1 - TC24 - DownloadTestCertificateInvalidAPIKeyTest")
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

    @Title("VOTT-5 - AC1 - TC25 - DownloadTestCertificateTestNumberDoesntExistTest")
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

    @Title("VOTT-5 - AC1 - TC26 - DownloadTestCertificateNumericTestNumberTest")
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

    @Title("VOTT-5 - AC1 - TC27 - DownloadTestCertificateVinNumberDoesntExistTest")
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

    @Title("VOTT-5 - AC1 - TC28 - DownloadTestCertificateNumericVINNumberTest")
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

    @Title("VOTT-5 - AC1 - TC29 - DownloadTestCertificateVinNumberSpecialCharsTest")
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

    @Title("VOTT-5 - AC1 - TC30 - DownloadTestCertificateTestNumberSpecialCharsTest")
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

    @Title("VOTT-5 - AC1 - TC31 - DownloadTestCertificatePostRequestTest")
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

    @Title("VOTT-5 - AC1 - TC32 - DownloadTestCertificatePutRequestTest")
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

    @Title("VOTT-5 - AC1 - TC33 - DownloadTestCertificatePatchRequestTest")
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

    @Title("VOTT-5 - AC1 - TC34 - DownloadTestCertificateDeleteRequestTest")
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

    //Test Data Setup
    private Vehicle newTestVehicle() {
        Vehicle vehicle = new Vehicle();

        vehicle.setSystemNumber("SYSTEM-NUMBER");
        vehicle.setVin("A12345");
        vehicle.setVrm_trm("999999999");
        vehicle.setTrailerID("88888888");

        return vehicle;
    }

    private FuelEmission newTestFuelEmission() {
        FuelEmission fe = new FuelEmission();

        fe.setModTypeCode("a");
        fe.setDescription("Test Description");
        fe.setEmissionStandard("Test Standard");
        fe.setFuelType("Petrol");

        return fe;
    }

    private TestStation newTestTestStation() {
        TestStation ts = new TestStation();

        ts.setPNumber("987654321");
        ts.setName("Test Test Station");
        ts.setType("Test");

        return ts;
    }

    private Tester newTestTester() {
        Tester tester = new Tester();

        tester.setStaffID("1");
        tester.setName("Auto Test");
        tester.setEmailAddress("auto@test.com");

        return tester;
    }

    private VehicleClass newTestVehicleClass() {
        VehicleClass vc = new VehicleClass();

        vc.setCode("1");
        vc.setDescription("Test Description");
        vc.setVehicleType("Test Type");
        vc.setVehicleSize("55555");
        vc.setVehicleConfiguration("Test Configuration");
        vc.setEuVehicleCategory("ABC");

        return vc;
    }

    private TestType newTestTestType() {
        TestType tt = new TestType();

        tt.setTestTypeClassification("Test Test Type");
        tt.setTestTypeName("Test Name");

        return tt;
    }

    private Preparer newTestPreparer() {
        Preparer preparer = new Preparer();

        preparer.setPreparerID("1");
        preparer.setName("Test Name");

        return preparer;
    }

    private Identity newTestIdentity() {
        Identity identity = new Identity();

        identity.setIdentityID("55555");
        identity.setName("Test Name");

        return identity;
    }

    private TestResult newTestTestResult() {
        TestResult tr = new TestResult();

        tr.setVehicleID(String.valueOf(vehiclePK));
        tr.setFuelEmissionID(String.valueOf(fuelEmissionPK));
        tr.setTestStationID(String.valueOf(testStationPK));
        tr.setTesterID(String.valueOf(testerPK));
        tr.setPreparerID(String.valueOf(preparerPK));
        tr.setVehicleClassID(String.valueOf(vehicleClassPK));
        tr.setTestTypeID(String.valueOf(testTypePK));
        tr.setTestStatus("Test Pass");
        tr.setReasonForCancellation("Automation Test Run");
        tr.setNumberOfSeats("3");
        tr.setOdometerReading("900");
        tr.setOdometerReadingUnits("Test Units");
        tr.setCountryOfRegistration("Test Country");
        tr.setNoOfAxles("4");
        tr.setRegnDate("2100-12-31");
        tr.setFirstUseDate("2100-12-31");
        tr.setCreatedAt("2021-01-01 00:00:00");
        tr.setLastUpdatedAt("2021-01-01 00:00:00");
        tr.setTestCode("111");
        tr.setTestNumber("A111B222");
        tr.setCertificateNumber("A111B222");
        tr.setSecondaryCertificateNumber("A111B222");
        tr.setTestExpiryDate("2022-01-01");
        tr.setTestAnniversaryDate("2022-01-01");
        tr.setTestTypeStartTimestamp("2022-01-01 00:00:00");
        tr.setTestTypeEndTimestamp("2022-01-01 00:00:00");
        tr.setNumberOfSeatbeltsFitted("2");
        tr.setLastSeatbeltInstallationCheckDate("2022-01-01");
        tr.setSeatbeltInstallationCheckDate("1");
        tr.setTestResult("Auto Test");
        tr.setReasonForAbandoning("Test Automation Run");
        tr.setAdditionalNotesRecorded("Additional Test Notes");
        tr.setAdditionalCommentsForAbandon("Additional Test Comments");
        tr.setParticulateTrapFitted("Particulate Test");
        tr.setParticulateTrapSerialNumber("ABC123");
        tr.setModificationTypeUsed("Test Modification");
        tr.setSmokeTestKLimitApplied("Smoke Test");
        tr.setCreatedByID(String.valueOf(identityPK));
        tr.setLastUpdatedByID(String.valueOf(identityPK));

        return tr;
    }
}
