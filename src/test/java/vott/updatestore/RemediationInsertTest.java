package vott.updatestore;

import com.google.gson.Gson;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import vott.auth.GrantType;
import vott.auth.OAuthVersion;
import vott.auth.TokenService;
import vott.config.VottConfiguration;
import vott.database.*;
import vott.database.connection.ConnectionFactory;
import vott.database.sqlgeneration.SqlGenerator;
import vott.e2e.FieldGenerator;
import vott.json.GsonInstance;
import vott.models.dao.*;
import vott.models.dto.testresults.CompleteTestResults;
import vott.models.dto.testresults.TestTypeResults;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

@RunWith(Parameterized.class)
//@Category(UnitTest.class)
public class RemediationInsertTest {
    @Rule
    public RetryRule retryRule = new RetryRule(3);
    private Gson gson;
    private FieldGenerator fieldGenerator;
    private TokenService v1ImplicitTokens;
    private VehicleRepository vehicleRepository;
    private TestResultRepository testResultRepository;
    private DefectRepository defectRepository;
    private TechnicalRecordRepository technicalRecordRepository;
    private TesterRepository testerRepository;
    private PreparerRepository preparerRepository;
    private EVLViewRepository evlViewRepository;
    private TFLViewRepository tflViewRepository;
    private AuthIntoServicesRepository authIntoServicesRepository;
    private VtEVLAdditionsRepository vtEVLAdditionsRepository;
    private VtEvlCvsRemovedRepository vtEvlCvsRemovedRepository;
    private String payloadPath;
    private TestStationRepository testStationRepository;
    private TestTypeRepository testTypeRepository;
    private CompleteTestResults expectedTestResult;
    private TestResult actualTestResult;
    @Before
    public void setUp() throws Exception {

        VottConfiguration configuration = VottConfiguration.local();

        gson = GsonInstance.get();

        fieldGenerator = new FieldGenerator();

        v1ImplicitTokens = new TokenService(OAuthVersion.V1, GrantType.IMPLICIT);

        ConnectionFactory connectionFactory = new ConnectionFactory(configuration);

        vehicleRepository = new VehicleRepository(connectionFactory);

        testResultRepository = new TestResultRepository(connectionFactory);

        defectRepository = new DefectRepository(connectionFactory);

        technicalRecordRepository = new TechnicalRecordRepository(connectionFactory);

        testerRepository = new TesterRepository(connectionFactory);

        preparerRepository = new PreparerRepository(connectionFactory);

        evlViewRepository = new EVLViewRepository(connectionFactory);

        tflViewRepository = new TFLViewRepository(connectionFactory);

        authIntoServicesRepository = new AuthIntoServicesRepository(connectionFactory);

        vtEVLAdditionsRepository = new VtEVLAdditionsRepository(connectionFactory);

        vtEvlCvsRemovedRepository = new VtEvlCvsRemovedRepository(connectionFactory);

        testStationRepository = new TestStationRepository(connectionFactory);

        testTypeRepository = new TestTypeRepository(connectionFactory);

        payloadPath = "src/main/resources/payloads/";

    }

    private void testSetUp(String filename) {

        //Obtain expectedTestResult from JSON file
        CompleteTestResults expectedTestResult = SharedUtilities.readTestResult(payloadPath + filename);
        //Extract vin for use in following tests
        String vin = expectedTestResult.getVin();

        //Check vehicle and testResult are present
        SqlGenerator.vehicleIsPresentInDatabase(vin, vehicleRepository);
        SqlGenerator.testResultIsPresentInDatabase(vin, testResultRepository);

        //Pull actualTestResultList from NOP
        List<vott.models.dao.TestResult> actualTestResultList = SqlGenerator.getTestResultWithVIN(vin, testResultRepository);

        this.expectedTestResult = expectedTestResult;

        this.actualTestResult = actualTestResultList.get(0);
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {"test-results_remediation_insertion_single_defect.json"},
                {"test-results_remediation_insertion_multiple_defects.json"},
                {"TestResultsRemediationInserts/HGV_2_Axel_Annual_Test.json"},
                {"TestResultsRemediationInserts/HGV_3_Axel_First_Test.json"},
                {"TestResultsRemediationInserts/HGV_4_Axel_Paid_Annual_Test_Retest.json"},
                {"TestResultsRemediationInserts/HGV_5_Axel_Part_Paid_Annual_Test_Retest.json"}
        });
    }
    private String filename;
    public RemediationInsertTest(String filename) {
        this.filename = filename;
    }

    @Title("CB2-9237 - Testing testResults remediation inserts for single defect within NOP")
    @Test
    public void testResultsRemediationInsert() {
        testSetUp(filename);
        testResultTests(expectedTestResult, actualTestResult);
    }

    @Title("CB2-9237 - Testing testTypes remediation inserts for single defect within NOP")
    @Test
    public void testTypesRemediationInsert() {
        testSetUp(filename);
        testTypeTests(expectedTestResult, actualTestResult);
    }

    @Title("CB2-9237 - Testing defects remediation inserts for single defect within NOP")
    @Test
    public void defectsRemediationInsert() {
        testSetUp(filename);
        defectTestsLoop(expectedTestResult, actualTestResult);
    }

    //--------------------------------------------------------------------
    private void testResultTests (CompleteTestResults expectedTestResult, TestResult actualTestResult) {
        //testResultId
        String expectedTestResultId = expectedTestResult.getTestResultId();
        String actualTestResultId = actualTestResult.getTestResultId();
        Assert.assertEquals(expectedTestResultId,actualTestResultId);
        //testStatus
        String expectedTestStatus = String.valueOf(expectedTestResult.getTestStatus());
        String actualTestStatus = actualTestResult.getTestStatus();
        Assert.assertEquals(expectedTestStatus,actualTestStatus);
        //noOfAxles
        String expectedNoOfAxles = String.valueOf(expectedTestResult.getNoOfAxles());
        String actualNoOfAxles = actualTestResult.getNoOfAxles();
        Assert.assertEquals(expectedNoOfAxles,actualNoOfAxles);
        //countryOfRegistration
        String expectedCountryOfRegistration = expectedTestResult.getCountryOfRegistration();
        String actualCountryOfRegistration = actualTestResult.getCountryOfRegistration();
        Assert.assertEquals(expectedCountryOfRegistration,actualCountryOfRegistration);
        //odometerReading
        String expectedOdometerReading = String.valueOf(expectedTestResult.getOdometerReading());
        String actualOdometerReading = actualTestResult.getOdometerReading();
        Assert.assertEquals(expectedOdometerReading,actualOdometerReading);
        //odometerReadingUnits
        String expectedOdometerReadingUnits = String.valueOf(expectedTestResult.getOdometerReadingUnits());
        String actualOdometerReadingUnits = actualTestResult.getOdometerReadingUnits();
        Assert.assertEquals(expectedOdometerReadingUnits,actualOdometerReadingUnits);
        //reasonForCancellation
        String expectedReasonForCancellation = expectedTestResult.getReasonForCancellation();
        String actualReasonForCancellation = actualTestResult.getReasonForCancellation();
        Assert.assertEquals(expectedReasonForCancellation,actualReasonForCancellation);
    }
    private void testTypeTests(CompleteTestResults expectedTestResult, TestResult actualTestResult) {
        //test type attributes
        //assert there is 1 test type
        TestTypeResults expectedTestType = expectedTestResult.getTestTypes().get(0);
        //testTypeStartTimestamp
        OffsetDateTime expectedTestTypeStartTimestamp = expectedTestType.getTestTypeStartTimestamp();
        //format to match NOP timestamp
        String formattedStartTimeStamp = expectedTestTypeStartTimestamp.format(DateTimeFormatter.ofPattern("uuuu-MM-dd' 'HH:mm:ss.SSS", Locale.ENGLISH));
        String actualTestTypeStartTimestamp = actualTestResult.getTestTypeStartTimestamp();
        Assert.assertEquals(formattedStartTimeStamp,actualTestTypeStartTimestamp);
        //testTypeEndTimestamp
        OffsetDateTime expectedTestTypeEndTimestamp = expectedTestType.getTestTypeEndTimestamp();
        //format to match NOP timestamp
        String formattedEndTimeStamp = expectedTestTypeEndTimestamp.format(DateTimeFormatter.ofPattern("uuuu-MM-dd' 'HH:mm:ss.SSS", Locale.ENGLISH));
        String actualTestTypeEndTimestamp = actualTestResult.getTestTypeEndTimestamp();
        Assert.assertEquals(formattedEndTimeStamp,actualTestTypeEndTimestamp);
        //reasonForAbandoning
        String expectedReasonForAbandoning = expectedTestType.getReasonForAbandoning();
        String actualReasonForAbandoning = actualTestResult.getReasonForAbandoning();
        Assert.assertEquals(expectedReasonForAbandoning,actualReasonForAbandoning);
        //testResult (outcome)
        String expectedTestResultOutcome = String.valueOf(expectedTestType.getTestResult());
        String actualTestResultOutcome = actualTestResult.getTestResult();
        Assert.assertEquals(expectedTestResultOutcome,actualTestResultOutcome);
        //additionalNotesRecorded
        String expectedAdditionalNotesRecorded = expectedTestType.getAdditionalNotesRecorded();
        String actualAdditionalNotesRecorded = actualTestResult.getAdditionalNotesRecorded();
        Assert.assertEquals(expectedAdditionalNotesRecorded,actualAdditionalNotesRecorded);

        //get from tester table
        String testResultVehicleID = actualTestResult.getVehicleID();
        List<Tester> actualTesters = SqlGenerator.getTesterDetailsWithVehicleID(testResultVehicleID, testerRepository);
        Assert.assertEquals(actualTesters.size(),1);
        Tester actualTester = actualTesters.get(0);
        //testerName
        String expectedTesterName = expectedTestResult.getTesterName();
        String actualTesterName = actualTester.getName();
        Assert.assertEquals(expectedTesterName,actualTesterName);
        //testerEmailAddress
        String expectedTesterEmailAddress = expectedTestResult.getTesterEmailAddress();
        String actualTesterEmailAddress = actualTester.getEmailAddress();
        Assert.assertEquals(expectedTesterEmailAddress,actualTesterEmailAddress);
        //testerStaffId
        String expectedTesterStaffId = expectedTestResult.getTesterStaffId();
        String actualTesterStaffId = actualTester.getStaffID();
        Assert.assertEquals(expectedTesterStaffId,actualTesterStaffId);

        //get from test station table
        String testResultId = actualTestResult.getTestResultId();
        List<vott.models.dao.TestStation> testStations = SqlGenerator.getTestStationWithTestResultId(testResultId,testStationRepository);
        //TODO Uncomment below assertion when NOP data is fixed
//        Assert.assertEquals(testStations.size(), 1);
        TestStation testStation = testStations.get(0);
        //testStationName
        String expectedTestStation = expectedTestResult.getTestStationName();
        String actualTestStation = testStation.getName();
        Assert.assertEquals(expectedTestStation,actualTestStation);
        //testStationPNumber
        String expectedTestStationPNumber = expectedTestResult.getTestStationPNumber();
        String actualTestStationPNumber = testStation.getPNumber();
        Assert.assertEquals(expectedTestStationPNumber,actualTestStationPNumber);
        //testStationType
        String expectedTestStationType = String.valueOf(expectedTestResult.getTestStationType());
        String actualTestStationType = testStation.getType();
        Assert.assertEquals(expectedTestStationType,actualTestStationType);

        //get from prepare table
        List<vott.models.dao.Preparer> preparers = SqlGenerator.getPreparerDetailsWithVehicleID(testResultVehicleID,preparerRepository);
        //TODO Uncomment below assertion when NOP data is fixed
//        Assert.assertEquals(preparers.size(), 1);
        Preparer preparer = preparers.get(0);
        //preparerName
        String expectedPreparerName = expectedTestResult.getPreparerName();
        String actualPreparerName = preparer.getName();
        Assert.assertEquals(expectedPreparerName,actualPreparerName);
        //preparerId
        String expectedPreparerId = expectedTestResult.getPreparerId();
        String actualPreparerId = preparer.getPreparerID();
        Assert.assertEquals(expectedPreparerId,actualPreparerId);

        //get from test type table
        String testTypeId = actualTestResult.getTestTypeID();
        List<vott.models.dao.TestType> testTypes = SqlGenerator.getTestTypeWithTestTypeId(testTypeId,testTypeRepository);
        //TODO Uncomment below assertion when NOP data is fixed
//        Assert.assertEquals(testTypes.size(), 1);
        TestType testType = testTypes.get(0);
        //name
        String expectedTestTypeName = expectedTestType.getTestTypeName();
        String actualTestTypeName = testType.getTestTypeName();
        Assert.assertEquals(expectedTestTypeName,actualTestTypeName);
    }
    private void defectTestsLoop(CompleteTestResults expectedTestResult, TestResult actualTestResult) {
        //Obtain testResultId from NOP, use it to obtain Defect List
        String nopTestResultId = actualTestResult.getId();
        List<vott.models.dao.Defect> actualDefectList = SqlGenerator.getDefectWithNopTestResultId(nopTestResultId, defectRepository);
        for (int i = 0; i < actualDefectList.size(); i++) {
            //Obtain expected and actual defects from list
            vott.models.dto.testresults.Defect expectedDefect = expectedTestResult.getTestTypes().get(0).getDefects().get(i);
            vott.models.dao.Defect actualDefect = actualDefectList.get(i);
            defectTests(expectedDefect, actualDefect);
        }
    }
    private void defectTests(vott.models.dto.testresults.Defect expectedDefect, vott.models.dao.Defect actualDefect) {
        String expectedImNumber = String.valueOf(expectedDefect.getImNumber());
        String actualImNumber = actualDefect.getImNumber();
        Assert.assertEquals("imNumbers do not match", expectedImNumber, actualImNumber);

        String expectedImDescription = expectedDefect.getImDescription();
        String actualImDescription = actualDefect.getImDescription();
        Assert.assertEquals("imDescriptions do not match", expectedImDescription, actualImDescription);

        String expectedItemNumber = String.valueOf(expectedDefect.getItemNumber());
        String actualItemNumber = actualDefect.getItemNumber();
        Assert.assertEquals("Item Numbers do not match", expectedItemNumber, actualItemNumber);

        String expectedItemDescription = expectedDefect.getItemDescription();
        String actualItemDescription = actualDefect.getItemDescription();
        Assert.assertEquals("Item Descriptions do not match", expectedItemDescription, actualItemDescription);

        String expectedDeficiencyRef = expectedDefect.getDeficiencyRef();
        String actualDeficiencyRef = actualDefect.getDeficiencyRef();
        Assert.assertEquals("Deficiency Refs do not match", expectedDeficiencyRef, actualDeficiencyRef);

        String expectedDeficiencyID = expectedDefect.getDeficiencyId();
        String actualDeficiencyID = actualDefect.getDeficiencyID();
        Assert.assertEquals("Deficiency IDs do not match", expectedDeficiencyID, actualDeficiencyID);

        String expectedDeficiencySubID = expectedDefect.getDeficiencySubId();
        String actualDeficiencySubID = actualDefect.getDeficiencySubID();
        Assert.assertEquals("Deficiency Sub IDs do not match", expectedDeficiencySubID, actualDeficiencySubID);

        String expectedDeficiencyCategory = String.valueOf(expectedDefect.getDeficiencyCategory());
        String actualDeficiencyCategory = actualDefect.getDeficiencyCategory();
        Assert.assertEquals("Deficiency Categories do not match", expectedDeficiencyCategory, actualDeficiencyCategory);

        String expectedDeficiencyText = expectedDefect.getDeficiencyText();
        String actualDeficiencyText = actualDefect.getDeficiencyText();
        Assert.assertEquals("Deficiency Texts do not match", expectedDeficiencyText, actualDeficiencyText);

        Boolean expectedStdForProhibition = expectedDefect.isStdForProhibition();
        String expectedStringStdForProhibition = SharedUtilities.convertBooleanToStringNumericBoolean(expectedStdForProhibition);
        String actualStdForProhibition = actualDefect.getStdForProhibition();
        Assert.assertEquals("Standard for Prohibitions do not match", expectedStringStdForProhibition, actualStdForProhibition);
    }
}
