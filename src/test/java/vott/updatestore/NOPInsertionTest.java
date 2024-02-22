package vott.updatestore;

import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.annotations.WithTag;
import net.thucydides.junit.annotations.TestData;
import org.junit.*;
import org.junit.runner.RunWith;
import vott.auth.GrantType;
import vott.auth.OAuthVersion;
import vott.auth.TokenService;
import vott.config.VottConfiguration;
import vott.database.*;
import vott.database.connection.ConnectionFactory;
import vott.database.sqlgeneration.SqlGenerator;
import vott.models.dao.*;
import vott.models.dto.techrecordsv3.TechRecordHgvComplete;
import vott.models.dto.testresults.CompleteTestResults;
import vott.models.dto.testresults.TestTypeResults;
import vott.models.dto.testresults.TestTypes;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.awaitility.Awaitility.with;

@RunWith(SerenityParameterizedRunner.class)
public class NOPInsertionTest {
    @Rule
    public RetryRule retryRule = new RetryRule(3);
    private TokenService v1ImplicitTokens;
    private VehicleRepository vehicleRepository;
    private TestResultRepository testResultRepository;
    private DefectRepository defectRepository;
    private TesterRepository testerRepository;
    private PreparerRepository preparerRepository;
    private String payloadPath;
    private TestStationRepository testStationRepository;
    private TestTypeRepository testTypeRepository;
    private SharedUtilities sharedUtilities;
    private CompleteTestResults expectedTestResult;
    private List<TestResult> actualTestResultList;
    private TestResult actualTestResult;


    @Before
    public void setUp() throws Exception {

        VottConfiguration configuration = VottConfiguration.local();

        v1ImplicitTokens = new TokenService(OAuthVersion.V1, GrantType.IMPLICIT);

        ConnectionFactory connectionFactory = new ConnectionFactory(configuration);

        vehicleRepository = new VehicleRepository(connectionFactory);

        testResultRepository = new TestResultRepository(connectionFactory);

        defectRepository = new DefectRepository(connectionFactory);

        testerRepository = new TesterRepository(connectionFactory);

        preparerRepository = new PreparerRepository(connectionFactory);

        testStationRepository = new TestStationRepository(connectionFactory);

        testTypeRepository = new TestTypeRepository(connectionFactory);

        payloadPath = "src/main/resources/payloads/";

        sharedUtilities = new SharedUtilities();

        testSetup();

    }

    @TestData
    @WithTag("Remediation")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {"TestInsertionTimeRecords/TechRecords/HGV_2_Axel_Tech_Record_Annual_Test_1.json", "test-results_hgv.json"}
        });
    }
    private final String techRecordFileName;
    private final String testResultFileName;
    public NOPInsertionTest(String techRecordFileName, String testResultFileName) {
        this.techRecordFileName = techRecordFileName;
        this.testResultFileName = testResultFileName;
    }

    @Test
    @WithTag("Remediation")
    @Title("CB2-9237 - test inserts data in NOP")
    public void insertNopTest()
    {
        Assert.assertEquals("expected only one test result to be returned from database", actualTestResultList.size(), 1);
        //compare JSON input to NOP data
        compareTestResultJsonToNOP();
        compareDefectsFromJsonToNOP();
    }


    //re-factor to compare
    //test result
    //test type
    //tester
    private void compareTestResultJsonToNOP()
    {
        //get from test result table
        //test result attributes
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
        List<vott.models.dao.Tester> actualTesters = SqlGenerator.getTesterDetailsWithVehicleID(testResultVehicleID, testerRepository);
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
        Assert.assertEquals(testStations.size(), 1);
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
        Assert.assertEquals(preparers.size(), 1);
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
        Assert.assertEquals(testTypes.size(), 1);
        TestType testType = testTypes.get(0);
        //name
        String expectedTestTypeName = expectedTestType.getTestTypeName();
        String actualTestTypeName = testType.getTestTypeName();
        Assert.assertEquals(expectedTestTypeName,actualTestTypeName);
    }

    private void compareDefectsFromJsonToNOP()
    {
        //consider tests with multiple test types
        //create tech record from JSON
        String nopTestResultId = actualTestResult.getId();
        List<vott.models.dao.Defect> actualDefectList = SqlGenerator.getDefectWithNopTestResultId(nopTestResultId, defectRepository);
        //compare JSON input to NOP data

        for (int i = 0; i < actualDefectList.size(); i++){
            //Obtain expected and actual defects from list
            vott.models.dto.testresults.Defect expectedDefect = expectedTestResult.getTestTypes().get(0).getDefects().get(i);
            vott.models.dao.Defect actualDefect = actualDefectList.get(i);

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
            String expectedStringStdForProhibition = sharedUtilities.convertBooleanToStringNumericBoolean(expectedStdForProhibition);
            String actualStdForProhibition = actualDefect.getStdForProhibition();
            Assert.assertEquals("Standard for Prohibitions do not match", expectedStringStdForProhibition, actualStdForProhibition);
        }
    }
    private void testResultDateSynchronisation(CompleteTestResults expectedTestResult) {
        TestTypes testTypes = expectedTestResult.getTestTypes();
        OffsetDateTime datetime = OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC);
        expectedTestResult.setTestTypes(testTypes);
        testTypes.get(0).setTestExpiryDate(datetime);
        testTypes.get(0).setTestTypeStartTimestamp(datetime);
        testTypes.get(0).setTestTypeEndTimestamp(datetime);
        expectedTestResult.setTestStartTimestamp(datetime);
        expectedTestResult.setTestEndTimestamp(datetime);
    }

    private void testSetup() {
        //TODO consider tests with multiple test types
        //create tech record from JSON
        TechRecordHgvComplete techRecord = sharedUtilities.loadTechRecord(payloadPath + techRecordFileName);
        //create test result from JSON
        CompleteTestResults expectedTestResult = sharedUtilities.loadTestResults(techRecord, payloadPath + testResultFileName);
        //set timestamps to today's date
        testResultDateSynchronisation(expectedTestResult);

        //post both tech record and test result, and assert a 201 response
        sharedUtilities.postAndValidateTechRecordTestResultResponse(techRecord, expectedTestResult, v1ImplicitTokens.getBearerToken());

        String vin = expectedTestResult.getVin();
        //wait for data to be streamed to NOP
        with().timeout(Duration.ofSeconds(60)).await().until(SqlGenerator.vehicleIsPresentInDatabase(vin, vehicleRepository));
        with().timeout(Duration.ofSeconds(60)).await().until(SqlGenerator.testResultIsPresentInDatabase(vin, testResultRepository));

        //get test result from NOP
        List<TestResult> actualTestResults = SqlGenerator.getTestResultWithVIN(vin, testResultRepository);

        this.expectedTestResult = expectedTestResult;
        this.actualTestResultList = actualTestResults;
        this.actualTestResult = actualTestResults.get(0);

    }
}
