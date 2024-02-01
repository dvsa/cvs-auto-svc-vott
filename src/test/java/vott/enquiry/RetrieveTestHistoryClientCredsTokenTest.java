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
import vott.api.TestHistoryAPI;
import vott.auth.GrantType;
import vott.auth.OAuthVersion;
import vott.auth.TokenService;
import vott.config.VottConfiguration;
import vott.database.*;
import vott.database.connection.ConnectionFactory;
import vott.database.seeddata.SeedData;
import vott.database.sqlgeneration.SqlGenerator;
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
public class RetrieveTestHistoryClientCredsTokenTest {

    // Variable + Constant Test Data Setup
    private String token;

    private String validVINNumber = "";
    private String validVehicleRegMark = "";
    private String testNumber = "";

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

    Vehicle vehicle = SeedData.newTestVehicle();
    FuelEmission fe = SeedData.newTestFuelEmission();
    TestStation ts = SeedData.newTestTestStation();
    Tester tester = SeedData.newTestTester();
    VehicleClass vc = SeedData.newTestVehicleClass();
    TestType tt = SeedData.newTestTestType();
    Preparer preparer = SeedData.newTestPreparer();
    Identity identity = SeedData.newTestIdentity();
    Defect defect = SeedData.newTestDefect();
    Location location = SeedData.newTestLocation();
    vott.models.dao.TestResult tr;
    CustomDefect cd;
    TestDefect td;

    @Before
    public void Setup() {
        this.token = new TokenService(OAuthVersion.V2, GrantType.CLIENT_CREDENTIALS).getBearerToken();

        //Connect to DB
        ConnectionFactory connectionFactory = new ConnectionFactory(VottConfiguration.local());

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
        defectPK = defectRepository.partialUpsert(defect);

        locationRepository = new LocationRepository(connectionFactory);
        locationPK = locationRepository.partialUpsert(location);

        testResultRepository = new TestResultRepository(connectionFactory);
        tr = SeedData.newTestTestResult(vehiclePK, fuelEmissionPK, testStationPK, testerPK, preparerPK, vehicleClassPK, testTypePK, identityPK);
        testResultPK = testResultRepository.fullUpsertIfNotExists(tr);

        customDefectRepository = new CustomDefectRepository(connectionFactory);
        cd = SeedData.newTestCustomDefect(testResultPK);
        customDefectPK = customDefectRepository.fullUpsert(cd);

        testDefectRepository = new TestDefectRepository(connectionFactory);
        td = SeedData.newTestTestDefect(testResultPK, defectPK, locationPK);
        testDefectPK = testDefectRepository.fullUpsert(td);

        validVINNumber = vehicle.getVin();
        validVehicleRegMark = vehicle.getVrm_trm();
        testNumber = tr.getTestNumber();

        with().timeout(Duration.ofSeconds(30)).await().until(SqlGenerator.vehicleIsPresentInDatabase(validVINNumber, vehicleRepository));
        with().timeout(Duration.ofSeconds(30)).await().until(SqlGenerator.testResultIsPresentInDatabase(validVINNumber, testResultRepository));
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
    @Title ("VOTT-9 - AC1 - TC31 - Happy Path - Retrieve Test History using client credentials token and a valid vin")
    @Test
    public void RetrieveTestHistoryUsingVinTest() throws InterruptedException {

        int tries = 0;
        int maxRetries = 20;
        int statusCode;
        Response response;

        //Retrieve and save test certificate (pdf) as byteArray
        do {
            response = TestHistoryAPI.getTestHistoryUsingVIN(validVINNumber, token);
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
            assertThat(testResult.getTestTypeEndTimestamp()).isEqualTo(tr.getTestTypeEndTimestamp().substring(0,19));
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
    @Title("VOTT-9 - AC1 - TC32 - Happy Path - Retrieve Test History Using Client Credentials token and a valid vrm")
    @Test
    public void RetrieveTestHistoryUsingVrmTest() throws InterruptedException {

        int tries = 0;
        int maxRetries = 20;
        int statusCode;
        Response response;

        do {
            response = TestHistoryAPI.getTestHistoryUsingVRM(validVehicleRegMark, token);
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
            assertThat(testResult.getTestTypeEndTimestamp()).isEqualTo(tr.getTestTypeEndTimestamp().substring(0,19));
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
    @Title("VOTT-9 - AC1 - TC41 - Happy Path - Retrieve Single Test History Using Client Credentials token and a test number")
    @Test
    public void RetrieveTestHistoryUsingTestNumberTest() throws InterruptedException {

        int tries = 0;
        int maxRetries = 20;
        int statusCode;
        Response response;

        do {
            response = TestHistoryAPI.getSpecificTestHistoryUsingTestNumber(testNumber, token);
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
            assertThat(testResult.getTestTypeEndTimestamp()).isEqualTo(tr.getTestTypeEndTimestamp().substring(0,19));
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
    @Title("VOTT-9 - AC1 - TC33 - Retrieve Test History Using a bad client creds JWT Token")
    @Test
    public void RetrieveTestHistoryBadJwtTokenTest() {
        Response response = TestHistoryAPI.getTestHistoryUsingVIN(validVINNumber,token+1);
        assertEquals(403, response.statusCode());
    }

    @WithTag("Vott")
    @Title("VOTT-9 - AC1 - TC34 - Retrieve Test History Using a client creds JWT Token and no query params")
    @Test
    public void RetrieveTestHistoryNoParamsTest() {
        Response response = TestHistoryAPI.getTestHistoryNoParams(token);
        assertEquals(400, response.statusCode());
        assertEquals("No parameter defined", response.asString());
    }

    @WithTag("Vott")
    @Title("VOTT-9 - AC1 - TC35 - Retrieve Test History Using a client creds JWT Token and both vin and vrm as query params")
    @Test
    public void RetrieveTestHistoryBothVinAndVrmTest() {
        Response response = TestHistoryAPI.getTestHistoryUsingVIN_VRM(validVINNumber, validVehicleRegMark, token);
        assertEquals(400, response.statusCode());
        assertEquals("Too many parameters defined", response.asString());
    }

    @WithTag("Vott")
    @Title("VOTT-9 - AC1 - TC36 - Retrieve Test History Using a client creds JWT Token and no api key")
    @Test
    public void RetrieveTestHistoryNoAPIKeyTest() {
        Response response = TestHistoryAPI.getTestHistoryNoAPIKey(validVINNumber, token);
        assertEquals(403, response.statusCode());
    }

    @WithTag("Vott")
    @Title("VOTT-9 - AC1 - TC37 - Retrieve Test History Using a client creds JWT Token and an invalid api key")
    @Test
    public void RetrieveTestHistoryInvalidAPIKey() {
        Response response = TestHistoryAPI.getTestHistoryInvalidAPIKey(validVINNumber, token);
        assertEquals(403, response.statusCode());
    }

    @WithTag("Vott")
    @Title("VOTT-9 - AC1 - TC38 - Retrieve Test History Using a client creds JWT Token and vrm that doesn't exist in db")
    @Test
    public void RetrieveTestHistoryVehicleRegMarkDoesntExistTest() {
        String invalidVehicleRegMark = "W01A00229";
        Response response = TestHistoryAPI.getTestHistoryUsingVRM(invalidVehicleRegMark, token);
        assertEquals(404, response.statusCode());
        assertEquals("No tests found", response.asString());
    }

    @WithTag("Vott")
    @Title("VOTT-9 - AC1 - TC39 - Retrieve Test History Using a client creds JWT Token and vin that doesn't exist in db")
    @Test
    public void RetrieveTestHistoryVinNumberDoesntExistTest() {
        String invalidVINNumber = "A123456789";
        Response response = TestHistoryAPI.getTestHistoryUsingVIN(invalidVINNumber, token);
        assertEquals(404, response.statusCode());
        assertEquals("No tests found", response.asString());
    }

    @WithTag("Vott")
    @Title("VOTT-9 - AC1 - TC40 - Retrieve Test History Using a client creds and non alpha numeric vrm")
    @Test
    public void RetrieveTestHistoryNonPrintableCharsParamsTest() {
        String nonAlphaVehicleMark = "!@/'";
        Response response = TestHistoryAPI.getTestHistoryUsingVRM(nonAlphaVehicleMark, token);
        assertEquals(500, response.statusCode());
        assertEquals("Vehicle identifier is invalid", response.asString());
    }
}


