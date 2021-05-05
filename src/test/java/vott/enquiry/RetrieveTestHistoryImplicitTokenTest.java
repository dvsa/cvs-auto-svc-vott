package vott.enquiry;


import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Title;
import net.thucydides.core.annotations.WithTag;
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
import vott.json.GsonInstance;
import vott.models.dao.*;
import vott.models.dto.enquiry.TestResult;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.Callable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static vott.e2e.RestAssuredAuthenticated.givenAuth;

@RunWith(SerenityRunner.class)
public class RetrieveTestHistoryImplicitTokenTest {

    // Variable + Constant Test Data Setup
    private VottConfiguration configuration = VottConfiguration.local();
    private String token;
    private final String xApiKey = configuration.getApiKeys().getEnquiryServiceApiKey();
    private String validVINNumber = "";
    private String validVehicleRegMark = "";
    private String nonAlphaVehicleMark = "!@/'";

    private final String invalidVINNumber = "A123456789";
    private final String invalidVehicleRegMark = "W01A00229";

    //Test Data Variables
    private Integer customDefectPK;
    private Integer testResultPK;
    private Integer vehiclePK;
    private Integer fuelEmissionPK;
    private Integer testStationPK;
    private Integer testerPK;
    private Integer vehicleClassPK;
    private Integer testTypePK;
    private Integer preparerPK;
    private Integer identityPK;
    private Integer defectPK;
    private Integer locationPK;
    private Integer testDefectPK;

    private CustomDefectRepository customDefectRepository;
    private TestResultRepository testResultRepository;
    private VehicleRepository vehicleRepository;
    private FuelEmissionRepository fuelEmissionRepository;
    private TestStationRepository testStationRepository;
    private TesterRepository testerRepository;
    private VehicleClassRepository vehicleClassRepository;
    private TestTypeRepository testTypeRepository;
    private PreparerRepository preparerRepository;
    private IdentityRepository identityRepository;
    private DefectRepository defectRepository;
    private LocationRepository locationRepository;
    private TestDefectRepository testDefectRepository;

    Vehicle vehicle = newTestVehicle();
    FuelEmission fe = newTestFuelEmission();
    TestStation ts = newTestTestStation();
    Tester tester = newTestTester();
    VehicleClass vc = newTestVehicleClass();
    TestType tt = newTestTestType();
    Preparer preparer = newTestPreparer();
    Identity identity = newTestIdentity();
    Defect defect = newTestDefect();
    Location location = newTestLocation();
    vott.models.dao.TestResult tr;
    CustomDefect cd;
    TestDefect td;


    @Before
    public void Setup() {

        RestAssured.baseURI = configuration.getApiProperties().getBranchSpecificUrl() + "/v1/enquiry/testResults";
        this.token = new TokenService(OAuthVersion.V1, GrantType.IMPLICIT).getBearerToken();

        //Connect to DB
        ConnectionFactory connectionFactory = new ConnectionFactory(
                VottConfiguration.local()
        );

        vehicleRepository = new VehicleRepository(connectionFactory);
        vehiclePK = vehicleRepository.fullUpsert(vehicle);

        fuelEmissionRepository = new FuelEmissionRepository(connectionFactory);
        fuelEmissionPK = fuelEmissionRepository.partialUpsert(fe);

        testStationRepository = new TestStationRepository(connectionFactory);
        testStationPK = testStationRepository.partialUpsert(ts);

        testerRepository = new TesterRepository(connectionFactory);
        testerPK = testerRepository.partialUpsert(tester);

        vehicleClassRepository = new VehicleClassRepository(connectionFactory);
        vehicleClassPK = vehicleClassRepository.partialUpsert(vc);

        testTypeRepository = new TestTypeRepository(connectionFactory);
        testTypePK = testTypeRepository.partialUpsert(tt);

        preparerRepository = new PreparerRepository(connectionFactory);
        preparerPK = preparerRepository.partialUpsert(preparer);

        identityRepository = new IdentityRepository(connectionFactory);
        identityPK = identityRepository.partialUpsert(identity);

        defectRepository = new DefectRepository(connectionFactory);
        defectPK = defectRepository.partialUpsert(newTestDefect());

        locationRepository = new LocationRepository(connectionFactory);
        locationPK = locationRepository.partialUpsert(location);

        testResultRepository = new TestResultRepository(connectionFactory);
        tr = newTestTestResult();
        testResultPK = testResultRepository.fullUpsert(tr);

        customDefectRepository = new CustomDefectRepository(connectionFactory);
        cd = newTestCustomDefect();
        customDefectPK = customDefectRepository.fullUpsert(cd);

        testDefectRepository = new TestDefectRepository(connectionFactory);
        td = newTestTestDefect();
        testDefectPK = testDefectRepository.fullUpsert(td);

        validVINNumber = vehicle.getVin();
        validVehicleRegMark = vehicle.getVrm_trm();

        with().timeout(Duration.ofSeconds(30)).await().until(vehicleIsPresentInDatabase(validVINNumber));
        with().timeout(Duration.ofSeconds(30)).await().until(testResultIsPresentInDatabase(String.valueOf(validVINNumber)));
    }

    @After
    public void tearDown() {
        //Test Data Cleanup
        testDefectRepository.delete(testDefectPK);
        customDefectRepository.delete(customDefectPK);
        testResultRepository.delete(testResultPK);
        vehicleRepository.delete(vehiclePK);
        fuelEmissionRepository.delete(fuelEmissionPK);
        testStationRepository.delete(testStationPK);
        testerRepository.delete(testerPK);
        vehicleClassRepository.delete(vehicleClassPK);
        testTypeRepository.delete(testTypePK);
        preparerRepository.delete(preparerPK);
        identityRepository.delete(identityPK);
        defectRepository.delete(defectPK);
        locationRepository.delete(locationPK);
    }

    @WithTag("Vott")
    @Title ("VOTT-9 - AC1 - TC21 - Happy Path - Retrieve Test History Using Implicit token and a valid vin")
    @Test
    public void RetrieveTestHistoryUsingVinTest() throws InterruptedException {

        int tries = 0;
        int maxRetries = 20;
        int statusCode;
        Response response;

        do {
            response =
                    givenAuth(token, xApiKey)
                            .header("content-type", "application/json")
                            .queryParam("vinNumber", validVINNumber).

                            //send request
                                    when().//log().all().
                            get().

                            //verification
                                    then().//log().all().
                            extract().response();
            statusCode = response.statusCode();
            tries++;
            Thread.sleep(1000);
        } while (statusCode >= 400 && tries < maxRetries);

        assertEquals(200, statusCode);

        Gson gson = GsonInstance.get();

        TestResult[] testResultArray  = gson.fromJson(response.asString(), TestResult[].class);

        for (TestResult testResult : testResultArray) {

            assertThat(testResult.getTester().getName()).isEqualTo(tester.getName());
            assertThat(testResult.getTester().getStaffId()).isEqualTo(tester.getStaffID());
            assertThat(testResult.getTester().getEmailAddress()).isEqualTo(tester.getEmailAddress());

//        assertThat(testResult.getPreparer().getName()).isEqualTo(preparer.getName());
//        assertThat(testResult.getPreparer().getPreparerId()).isEqualTo(preparer.getPreparerID());

            assertThat(testResult.getRegnDate()).isEqualTo(tr.getRegnDate());
            assertThat(testResult.getTestCode()).isEqualTo(tr.getTestCode());

            assertThat(testResult.getTestType().getTestTypeName()).isEqualTo(tt.getTestTypeName());
            assertThat(testResult.getTestType().getTestTypeClassification()).isEqualTo(tt.getTestTypeClassification());

            assertThat(testResult.getCreatedAt()).isEqualTo(tr.getCreatedAt());
            assertThat(testResult.getNoOfAxles()).isEqualTo(Integer.valueOf(tr.getNoOfAxles()));
            assertThat(testResult.getTestNumber()).isEqualTo(tr.getTestNumber());
            assertThat(testResult.getTestResult()).isEqualTo(tr.getTestResult());
            assertThat(testResult.getTestStatus()).isEqualTo(tr.getTestStatus());
            assertThat(testResult.getFirstUseDate()).isEqualTo(tr.getFirstUseDate());

            assertThat(testResult.getFuelEmission().getFuelType()).isEqualTo(fe.getFuelType());
            assertThat(testResult.getFuelEmission().getDescription()).isEqualTo(fe.getDescription());
            assertThat(testResult.getFuelEmission().getModTypeCode()).isEqualTo(fe.getModTypeCode());
            assertThat(testResult.getFuelEmission().getEmissionStandard()).isEqualTo(fe.getEmissionStandard());

            assertThat(testResult.getTestStation().getName()).isEqualTo(ts.getName());
            assertThat(testResult.getTestStation().getType()).isEqualTo(ts.getType());
            assertThat(testResult.getTestStation().getStationNumber()).isEqualTo(ts.getPNumber());

            assertThat(testResult.getLastUpdatedAt()).isEqualTo(tr.getLastUpdatedAt());
            assertThat(testResult.getNumberOfSeats()).isEqualTo(Integer.valueOf(tr.getNumberOfSeats()));

            assertThat(testResult.getVehicleClass().getCode()).isEqualTo(vc.getCode());
            assertThat(testResult.getVehicleClass().getDescription()).isEqualTo(vc.getDescription());
            assertThat(testResult.getVehicleClass().getVehicleSize()).isEqualTo(vc.getVehicleSize());
            assertThat(testResult.getVehicleClass().getVehicleType()).isEqualTo(vc.getVehicleType());
            assertThat(testResult.getVehicleClass().getEuVehicleCategory()).isEqualTo(vc.getEuVehicleCategory());
            assertThat(testResult.getVehicleClass().getVehicleConfiguration()).isEqualTo(vc.getVehicleConfiguration());

            assertThat(testResult.getTestExpiryDate()).isEqualTo(tr.getTestExpiryDate());
            assertThat(testResult.getOdometerReading()).isEqualTo(Integer.valueOf(tr.getOdometerReading()));
            assertThat(testResult.getCertificateNumber()).isEqualTo(tr.getCertificateNumber());
            assertThat(testResult.getReasonForAbandoning()).isEqualTo(tr.getReasonForAbandoning());
            assertThat(testResult.getTestAnniversaryDate()).isEqualTo(tr.getTestAnniversaryDate());
            assertThat(testResult.getModificationTypeUsed()).isEqualTo(tr.getModificationTypeUsed());
            assertThat(testResult.getOdometerReadingUnits()).isEqualTo(tr.getOdometerReadingUnits());
            assertThat(testResult.getTestTypeEndTimestamp()).isEqualTo(tr.getTestTypeEndTimestamp());
            assertThat(testResult.getCountryOfRegistration()).isEqualTo(tr.getCountryOfRegistration());
            assertThat(testResult.getParticulateTrapFitted()).isEqualTo(tr.getParticulateTrapFitted());
            assertThat(testResult.getReasonForCancellation()).isEqualTo(tr.getReasonForCancellation());
            assertThat(testResult.getSmokeTestKLimitApplied()).isEqualTo(tr.getSmokeTestKLimitApplied());
            assertThat(testResult.getTestTypeStartTimestamp()).isEqualTo(tr.getTestTypeStartTimestamp());
            assertThat(testResult.getAdditionalNotesRecorded()).isEqualTo(tr.getAdditionalNotesRecorded());
            assertThat(testResult.getNumberOfSeatbeltsFitted()).isEqualTo(Integer.valueOf(tr.getNumberOfSeatbeltsFitted()));
            assertThat(testResult.getSecondaryCertificateNumber()).isEqualTo(tr.getSecondaryCertificateNumber());
            assertThat(testResult.getParticulateTrapSerialNumber()).isEqualTo(tr.getParticulateTrapSerialNumber());
            assertThat(testResult.getAdditionalCommentsForAbandon()).isEqualTo(tr.getAdditionalCommentsForAbandon());
            assertThat(testResult.getSeatbeltInstallationCheckDate()).isEqualTo("true");
            assertThat(testResult.getLastSeatbeltInstallationCheckDate()).isEqualTo(tr.getLastSeatbeltInstallationCheckDate());

            assertThat(testResult.getCustomDefect().get(0).getDefectName()).isEqualTo(cd.getDefectName());
            assertThat(testResult.getCustomDefect().get(0).getDefectNotes()).isEqualTo(cd.getDefectNotes());
            assertThat(testResult.getCustomDefect().get(0).getReferenceNumber()).isEqualTo(cd.getReferenceNumber());

            assertThat(testResult.getDefects().get(0).isPrs()).isEqualTo(true);
            assertThat(testResult.getDefects().get(0).getNotes()).isEqualTo(td.getNotes());
            assertThat(testResult.getDefects().get(0).getDefect().getImNumber()).isEqualTo(Integer.valueOf(defect.getImNumber()));
            assertThat(testResult.getDefects().get(0).getDefect().getItemNumber()).isEqualTo(Integer.valueOf(defect.getItemNumber()));
            assertThat(testResult.getDefects().get(0).getDefect().getDeficiencyId()).isEqualTo(defect.getDeficiencyID());
            assertThat(testResult.getDefects().get(0).getDefect().getDeficiencyRef()).isEqualTo(defect.getDeficiencyRef());
            assertThat(testResult.getDefects().get(0).getDefect().getImDescription()).isEqualTo(defect.getImDescription());
            assertThat(testResult.getDefects().get(0).getDefect().getDeficiencyText()).isEqualTo(defect.getDeficiencyText());
            assertThat(testResult.getDefects().get(0).getDefect().getDeficiencySubId()).isEqualTo(defect.getDeficiencySubID());
            assertThat(testResult.getDefects().get(0).getDefect().getItemDescription()).isEqualTo(defect.getItemDescription());
            assertThat(testResult.getDefects().get(0).getDefect().isStdForProhibition()).isEqualTo(true);
            assertThat(testResult.getDefects().get(0).getDefect().getDeficiencyCategory()).isEqualTo(defect.getDeficiencyCategory());

            assertThat(testResult.getDefects().get(0).getLocation().getLateral()).isEqualTo(location.getLateral());
            assertThat(testResult.getDefects().get(0).getLocation().getVertical()).isEqualTo(location.getVertical());
            assertThat(testResult.getDefects().get(0).getLocation().getRowNumber()).isEqualTo(Integer.valueOf(location.getRowNumber()));
            assertThat(testResult.getDefects().get(0).getLocation().getAxleNumber()).isEqualTo(Integer.valueOf(location.getAxleNumber()));
            assertThat(testResult.getDefects().get(0).getLocation().getHorizontal()).isEqualTo(location.getHorizontal());
            assertThat(testResult.getDefects().get(0).getLocation().getSeatNumber()).isEqualTo(Integer.valueOf(location.getSeatNumber()));
            assertThat(testResult.getDefects().get(0).getLocation().getLongitudinal()).isEqualTo(location.getLongitudinal());

            assertThat(testResult.getDefects().get(0).getProhibitionIssued()).isEqualTo(true);
        }

    }

    @WithTag("Vott")
    @Title("VOTT-9 - AC1 - TC22 - Happy Path - Retrieve Test History Using Implicit token and a valid vrm")
    @Test
    public void RetrieveTestHistoryUsingVrmTest() throws InterruptedException {

        int tries = 0;
        int maxRetries = 20;
        int statusCode;
        Response response;

        do {
            response =
                    givenAuth(token, xApiKey)
                            .header("content-type", "application/json")
                            .queryParam("VehicleRegMark", validVehicleRegMark).

                            //send request
                                    when().//log().all().
                            get().

                            //verification
                                    then().//log().all().
                            statusCode(200).
                            extract().response();
            statusCode = response.statusCode();
            tries++;
            Thread.sleep(1000);
        } while (statusCode >= 400 && tries < maxRetries);

        assertEquals(200, statusCode);

        Gson gson = GsonInstance.get();

        TestResult[] testResultArray  = gson.fromJson(response.asString(), TestResult[].class);

        for (TestResult testResult : testResultArray) {

            assertThat(testResult.getTester().getName()).isEqualTo(tester.getName());
            assertThat(testResult.getTester().getStaffId()).isEqualTo(tester.getStaffID());
            assertThat(testResult.getTester().getEmailAddress()).isEqualTo(tester.getEmailAddress());

//        assertThat(testResult.getPreparer().getName()).isEqualTo(preparer.getName());
//        assertThat(testResult.getPreparer().getPreparerId()).isEqualTo(preparer.getPreparerID());

            assertThat(testResult.getRegnDate()).isEqualTo(tr.getRegnDate());
            assertThat(testResult.getTestCode()).isEqualTo(tr.getTestCode());

            assertThat(testResult.getTestType().getTestTypeName()).isEqualTo(tt.getTestTypeName());
            assertThat(testResult.getTestType().getTestTypeClassification()).isEqualTo(tt.getTestTypeClassification());

            assertThat(testResult.getCreatedAt()).isEqualTo(tr.getCreatedAt());
            assertThat(testResult.getNoOfAxles()).isEqualTo(Integer.valueOf(tr.getNoOfAxles()));
            assertThat(testResult.getTestNumber()).isEqualTo(tr.getTestNumber());
            assertThat(testResult.getTestResult()).isEqualTo(tr.getTestResult());
            assertThat(testResult.getTestStatus()).isEqualTo(tr.getTestStatus());
            assertThat(testResult.getFirstUseDate()).isEqualTo(tr.getFirstUseDate());

            assertThat(testResult.getFuelEmission().getFuelType()).isEqualTo(fe.getFuelType());
            assertThat(testResult.getFuelEmission().getDescription()).isEqualTo(fe.getDescription());
            assertThat(testResult.getFuelEmission().getModTypeCode()).isEqualTo(fe.getModTypeCode());
            assertThat(testResult.getFuelEmission().getEmissionStandard()).isEqualTo(fe.getEmissionStandard());

            assertThat(testResult.getTestStation().getName()).isEqualTo(ts.getName());
            assertThat(testResult.getTestStation().getType()).isEqualTo(ts.getType());
            assertThat(testResult.getTestStation().getStationNumber()).isEqualTo(ts.getPNumber());

            assertThat(testResult.getLastUpdatedAt()).isEqualTo(tr.getLastUpdatedAt());
            assertThat(testResult.getNumberOfSeats()).isEqualTo(Integer.valueOf(tr.getNumberOfSeats()));

            assertThat(testResult.getVehicleClass().getCode()).isEqualTo(vc.getCode());
            assertThat(testResult.getVehicleClass().getDescription()).isEqualTo(vc.getDescription());
            assertThat(testResult.getVehicleClass().getVehicleSize()).isEqualTo(vc.getVehicleSize());
            assertThat(testResult.getVehicleClass().getVehicleType()).isEqualTo(vc.getVehicleType());
            assertThat(testResult.getVehicleClass().getEuVehicleCategory()).isEqualTo(vc.getEuVehicleCategory());
            assertThat(testResult.getVehicleClass().getVehicleConfiguration()).isEqualTo(vc.getVehicleConfiguration());

            assertThat(testResult.getTestExpiryDate()).isEqualTo(tr.getTestExpiryDate());
            assertThat(testResult.getOdometerReading()).isEqualTo(Integer.valueOf(tr.getOdometerReading()));
            assertThat(testResult.getCertificateNumber()).isEqualTo(tr.getCertificateNumber());
            assertThat(testResult.getReasonForAbandoning()).isEqualTo(tr.getReasonForAbandoning());
            assertThat(testResult.getTestAnniversaryDate()).isEqualTo(tr.getTestAnniversaryDate());
            assertThat(testResult.getModificationTypeUsed()).isEqualTo(tr.getModificationTypeUsed());
            assertThat(testResult.getOdometerReadingUnits()).isEqualTo(tr.getOdometerReadingUnits());
            assertThat(testResult.getTestTypeEndTimestamp()).isEqualTo(tr.getTestTypeEndTimestamp());
            assertThat(testResult.getCountryOfRegistration()).isEqualTo(tr.getCountryOfRegistration());
            assertThat(testResult.getParticulateTrapFitted()).isEqualTo(tr.getParticulateTrapFitted());
            assertThat(testResult.getReasonForCancellation()).isEqualTo(tr.getReasonForCancellation());
            assertThat(testResult.getSmokeTestKLimitApplied()).isEqualTo(tr.getSmokeTestKLimitApplied());
            assertThat(testResult.getTestTypeStartTimestamp()).isEqualTo(tr.getTestTypeStartTimestamp());
            assertThat(testResult.getAdditionalNotesRecorded()).isEqualTo(tr.getAdditionalNotesRecorded());
            assertThat(testResult.getNumberOfSeatbeltsFitted()).isEqualTo(Integer.valueOf(tr.getNumberOfSeatbeltsFitted()));
            assertThat(testResult.getSecondaryCertificateNumber()).isEqualTo(tr.getSecondaryCertificateNumber());
            assertThat(testResult.getParticulateTrapSerialNumber()).isEqualTo(tr.getParticulateTrapSerialNumber());
            assertThat(testResult.getAdditionalCommentsForAbandon()).isEqualTo(tr.getAdditionalCommentsForAbandon());
            assertThat(testResult.getSeatbeltInstallationCheckDate()).isEqualTo("true");
            assertThat(testResult.getLastSeatbeltInstallationCheckDate()).isEqualTo(tr.getLastSeatbeltInstallationCheckDate());

            assertThat(testResult.getCustomDefect().get(0).getDefectName()).isEqualTo(cd.getDefectName());
            assertThat(testResult.getCustomDefect().get(0).getDefectNotes()).isEqualTo(cd.getDefectNotes());
            assertThat(testResult.getCustomDefect().get(0).getReferenceNumber()).isEqualTo(cd.getReferenceNumber());

            assertThat(testResult.getDefects().get(0).isPrs()).isEqualTo(true);
            assertThat(testResult.getDefects().get(0).getNotes()).isEqualTo(td.getNotes());
            assertThat(testResult.getDefects().get(0).getDefect().getImNumber()).isEqualTo(Integer.valueOf(defect.getImNumber()));
            assertThat(testResult.getDefects().get(0).getDefect().getItemNumber()).isEqualTo(Integer.valueOf(defect.getItemNumber()));
            assertThat(testResult.getDefects().get(0).getDefect().getDeficiencyId()).isEqualTo(defect.getDeficiencyID());
            assertThat(testResult.getDefects().get(0).getDefect().getDeficiencyRef()).isEqualTo(defect.getDeficiencyRef());
            assertThat(testResult.getDefects().get(0).getDefect().getImDescription()).isEqualTo(defect.getImDescription());
            assertThat(testResult.getDefects().get(0).getDefect().getDeficiencyText()).isEqualTo(defect.getDeficiencyText());
            assertThat(testResult.getDefects().get(0).getDefect().getDeficiencySubId()).isEqualTo(defect.getDeficiencySubID());
            assertThat(testResult.getDefects().get(0).getDefect().getItemDescription()).isEqualTo(defect.getItemDescription());
            assertThat(testResult.getDefects().get(0).getDefect().isStdForProhibition()).isEqualTo(true);
            assertThat(testResult.getDefects().get(0).getDefect().getDeficiencyCategory()).isEqualTo(defect.getDeficiencyCategory());

            assertThat(testResult.getDefects().get(0).getLocation().getLateral()).isEqualTo(location.getLateral());
            assertThat(testResult.getDefects().get(0).getLocation().getVertical()).isEqualTo(location.getVertical());
            assertThat(testResult.getDefects().get(0).getLocation().getRowNumber()).isEqualTo(Integer.valueOf(location.getRowNumber()));
            assertThat(testResult.getDefects().get(0).getLocation().getAxleNumber()).isEqualTo(Integer.valueOf(location.getAxleNumber()));
            assertThat(testResult.getDefects().get(0).getLocation().getHorizontal()).isEqualTo(location.getHorizontal());
            assertThat(testResult.getDefects().get(0).getLocation().getSeatNumber()).isEqualTo(Integer.valueOf(location.getSeatNumber()));
            assertThat(testResult.getDefects().get(0).getLocation().getLongitudinal()).isEqualTo(location.getLongitudinal());

            assertThat(testResult.getDefects().get(0).getProhibitionIssued()).isEqualTo(true);
        }
    }

    @WithTag("Vott")
    @Title("VOTT-9 - AC1 - TC23 - Retrieve Test History Using a bad implicit JWT Token")
    @Test
    public void RetrieveTestHistoryBadJwtTokenTest() {

        //prep request
        givenAuth(token + 1, xApiKey)
                .header("content-type", "application/json")
                .queryParam("VinNumber", validVINNumber).

                //send request
                        when().//log().all().
                get().

                //verification
                        then().//log().all().
                statusCode(403).
                body("message", equalTo("User is not authorized to access this resource with an explicit deny"));
    }

    @WithTag("Vott")
    @Title("VOTT-9 - AC1 - TC24 - Retrieve Test History Using an implicit JWT Token and no query params")
    @Test
    public void RetrieveTestHistoryNoParamsTest() {

        //prep request
        givenAuth(token, xApiKey)
                .header("content-type", "application/json").

                //send request
                        when().//log().all().
                get().

                //verification
                        then().//log().all().
                statusCode(400).
                body(equalTo("No parameter defined"));
    }

    @WithTag("Vott")
    @Title("VOTT-9 - AC1 - TC25 - Retrieve Test History Using an implicit JWT Token and both vin and vrm as query params")
    @Test
    public void RetrieveTestHistoryBothVinAndVrmTest() {

        //prep request
        givenAuth(token, xApiKey)
                .header("content-type", "application/json")
                .queryParam("vinNumber", validVINNumber)
                .queryParam("VehicleRegMark", invalidVehicleRegMark).

                //send request
                        when().//log().all().
                get().

                //verification
                        then().//log().all().
                statusCode(400).
                body(equalTo("Too many parameters defined"));
    }

    @WithTag("Vott")
    @Title("VOTT-9 - AC1 - TC26 Retrieve Test History Using an implicit JWT Token and no api key")
    @Test
    public void RetrieveTestHistoryNoAPIKeyTest() {

        //prep request
        givenAuth(token)
                .header("content-type", "application/json")
                .queryParam("vinNumber", validVINNumber).

                //send request
                        when().//log().all().
                get().

                //verification
                        then().//log().all().
                statusCode(403).
                body("message", equalTo("Forbidden"));
    }

    @WithTag("Vott")
    @Title("VOTT-9 - AC1 - TC27 - Retrieve Test History Using an implicit JWT Token and vrm that doesn't exist in db")
    @Test
    public void RetrieveTestHistoryInvalidAPIKey() {

        //prep request
        givenAuth(token, xApiKey + "badkey")
                .header("content-type", "application/json")
                .queryParam("vinNumber", validVINNumber).

                //send request
                        when().//log().all().
                get().

                //verification
                        then().//log().all().
                statusCode(403).
                body("message", equalTo("Forbidden"));
    }

    @WithTag("Vott")
    @Title("VOTT-9 - AC1 - TC28 - Retrieve Test History Using an implicit JWT Token and vrm that doesn't exist in db")
    @Test
    public void RetrieveTestHistoryVehicleRegMarkDoesntExistTest() {

        //prep request
        givenAuth(token, xApiKey)
                .header("content-type", "application/json")
                .queryParam("VehicleRegMark", invalidVehicleRegMark).

                //send request
                        when().//log().all().
                get().

                //verification
                        then().//log().all().
                statusCode(404).
                body(equalTo("No tests found"));
    }

    @WithTag("Vott")
    @Title("VOTT-9 - AC1 - TC29 - Retrieve Test History Using an implicit JWT Token and vin that doesn't exist in db")
    @Test
    public void RetrieveTestHistoryVinNumberDoesntExistTest() {

        //prep request
        givenAuth(token, xApiKey)
                .header("content-type", "application/json")
                .queryParam("vinNumber", invalidVINNumber).

                //send request
                        when().//log().all().
                get().

                //verification
                        then().//log().all().
                statusCode(404).
                body(equalTo("No tests found"));
    }

    @WithTag("Vott")
    @Title("VOTT-9 - AC1 - TC30 - Retrieve Test History Using an implicit and non alpha numeric vrm")
    @Test
    public void RetrieveTestHistoryNonPrintableCharsParamsTest() {

        //prep request
        givenAuth(token, xApiKey)
                .header("content-type", "application/json")
                .queryParam("VehicleRegMark", nonAlphaVehicleMark).

                //send request
                        when().//log().all().
                get().

                //verification
                        then().//log().all().
                statusCode(500).
                body(equalTo("Vehicle identifier is invalid"));
    }

    private CustomDefect newTestCustomDefect() {
        CustomDefect cd = new CustomDefect();

        cd.setTestResultID(String.valueOf(testResultPK));
        cd.setReferenceNumber("444444");
        cd.setDefectName("Test Custom Defect");
        cd.setDefectNotes("Test Custom Defect Notes");

        return cd;
    }

    private vott.models.dao.Vehicle newTestVehicle() {
        vott.models.dao.Vehicle vehicle = new vott.models.dao.Vehicle();

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

    private vott.models.dao.TestResult newTestTestResult() {
        vott.models.dao.TestResult tr = new vott.models.dao.TestResult();

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
        tr.setCreatedAt("2021-01-01 00:00:00.000000");
        tr.setLastUpdatedAt("2021-01-01 00:00:00.000000");
        tr.setTestCode("111");
        tr.setTestNumber("A111B222");
        tr.setCertificateNumber("A111B222");
        tr.setSecondaryCertificateNumber("A111B222");
        tr.setTestExpiryDate("2022-01-01");
        tr.setTestAnniversaryDate("2022-01-01");
        tr.setTestTypeStartTimestamp("2022-01-01 00:00:00.000000");
        tr.setTestTypeEndTimestamp("2022-01-01 00:00:00.000000");
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

    private Defect newTestDefect() {
        Defect defect = new Defect();

        defect.setImNumber("123");
        defect.setImDescription("Test IM Description");
        defect.setItemNumber("5555");
        defect.setItemDescription("Test Item Description");
        defect.setDeficiencyRef("Test Reference");
        defect.setDeficiencyID("1");
        defect.setDeficiencySubID("444");
        defect.setDeficiencyCategory("Category");
        defect.setDeficiencyText("Test Test");
        defect.setStdForProhibition("1");

        return defect;
    }

    private Location newTestLocation() {
        Location location = new Location();

        location.setVertical("TestV");
        location.setHorizontal("TestH");
        location.setLateral("TestLat");
        location.setLongitudinal("TestL");
        location.setRowNumber("10");
        location.setSeatNumber("20");
        location.setAxleNumber("30");

        return location;
    }

    private TestDefect newTestTestDefect() {
        TestDefect td = new TestDefect();

        td.setTestResultID(String.valueOf(testResultPK));
        td.setDefectID(String.valueOf(defectPK));
        td.setLocationID(String.valueOf(locationPK));
        td.setNotes("Test Notes");
        td.setPrs("1");
        td.setProhibitionIssued("1");

        return td;
    }

    private Callable<Boolean> vehicleIsPresentInDatabase(String vin) {
        return () -> {
            List<vott.models.dao.Vehicle> vehicles = vehicleRepository.select(String.format("SELECT * FROM `vehicle` WHERE `vin` = '%s'", vin));
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
}
