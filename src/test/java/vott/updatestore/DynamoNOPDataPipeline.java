package vott.updatestore;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Title;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import vott.models.dto.techrecords.TechRecords;
import vott.models.dto.testresults.CompleteTestResults;
import vott.models.dto.testresults.TestTypeResults;
import vott.models.dto.testresults.TestTypes;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import vott.models.dto.techrecords.TechRecord.ApprovalTypeEnum;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.with;
import static vott.database.sqlgeneration.SqlGenerator.getPreparerDetailsWithVehicleID;
import static vott.database.sqlgeneration.SqlGenerator.getTesterDetailsWithVehicleID;
import static vott.database.sqlgeneration.SqlGenerator.getVehicleWithVIN;
import static vott.database.sqlgeneration.SqlGenerator.getTestResultWithVIN;
import static vott.database.sqlgeneration.SqlGenerator.getEVLViewWithCertificateNumberAndVrm;
import static vott.database.sqlgeneration.SqlGenerator.getAuthIntoServices;
import static vott.database.sqlgeneration.SqlGenerator.getTFLViewWithVin;
import static vott.models.dto.testresults.TestTypeResults.EmissionStandardEnum.*;

@RunWith(SerenityRunner.class)
public class DynamoNOPDataPipeline {
    private VottConfiguration configuration;
    private Gson gson;
    private FieldGenerator fieldGenerator;
    private TokenService v1ImplicitTokens;
    private VehicleRepository vehicleRepository;
    private TestResultRepository testResultRepository;
    private TechnicalRecordRepository technicalRecordRepository;
    private TesterRepository testerRepository;
    private PreparerRepository preparerRepository;
    private EVLViewRepository evlViewRepository;
    private TFLViewRepository tflViewRepository;
    private AuthIntoServicesRepository authIntoServicesRepository;
    private String payloadPath;

    @Before
    public void setUp() throws Exception {
        configuration = VottConfiguration.local();

        gson = GsonInstance.get();

        fieldGenerator = new FieldGenerator();

        v1ImplicitTokens = new TokenService(OAuthVersion.V1, GrantType.IMPLICIT);

        ConnectionFactory connectionFactory = new ConnectionFactory(VottConfiguration.local());

        vehicleRepository = new VehicleRepository(connectionFactory);

        testResultRepository = new TestResultRepository(connectionFactory);

        technicalRecordRepository = new TechnicalRecordRepository(connectionFactory);

        testerRepository = new TesterRepository(connectionFactory);

        preparerRepository = new PreparerRepository(connectionFactory);

        evlViewRepository = new EVLViewRepository(connectionFactory);

        tflViewRepository = new TFLViewRepository(connectionFactory);

        authIntoServicesRepository = new AuthIntoServicesRepository(connectionFactory);

        payloadPath="src/main/resources/payloads/" ;

    }

    @Title("CB2-7258 - TC1 - testCode value truncation")
    @Test
    public void testCodeTruncation() {
        TechRecordPOST truncationTechRecord = loadTechRecord(payloadPath+"truncation_technical-records.json");
        CompleteTestResults truncationTestResult = loadTestResults(truncationTechRecord, payloadPath+"truncation_test-results.json");

        VehiclesAPI.postVehicleTechnicalRecord(truncationTechRecord, v1ImplicitTokens.getBearerToken());
        TestResultAPI.postTestResult(truncationTestResult, v1ImplicitTokens.getBearerToken());

        String vin = truncationTestResult.getVin();
        System.out.println("vin " + vin);
        with().timeout(Duration.ofSeconds(30)).await().until(SqlGenerator.vehicleIsPresentInDatabase(vin, vehicleRepository));
        with().timeout(Duration.ofSeconds(30)).await().until(SqlGenerator.testResultIsPresentInDatabase(vin, testResultRepository));

        List<vott.models.dao.TestResult> testResultNOP = getTestResultWithVIN(vin, testResultRepository);
        assertThat(testResultNOP.get(0).getTestCode().length()).isEqualTo(3);
    }

    @Title("CB2-7548 - Remove technical-record stream from edh-marshaller - Verify Tech and test results are updated correctly")
    @Test
    public void testTechUpdate(){
/***
 * EDH-MARSHALLER lamda is modified with respect to processing of tech records
 * The test loads 10 set of records and verifies that its all been loaded to NOPS
 * When EDH-MARSHALLER is not processing records correclty then the test will fail
  */
        ArrayList<String> vinList = new ArrayList<>();
        TechRecordPOST truncationTechRecord;
        CompleteTestResults truncationTestResult;
        for(int i=0;i<20;i++) {
            truncationTechRecord = loadTechRecord(payloadPath+"performance_technical-records.json");
            truncationTestResult = loadTestResults(truncationTechRecord, payloadPath+"performance_test-results.json");
            VehiclesAPI.postVehicleTechnicalRecord(truncationTechRecord, v1ImplicitTokens.getBearerToken());
            TestResultAPI.postTestResult(truncationTestResult, v1ImplicitTokens.getBearerToken());
            String vin = truncationTestResult.getVin();
            vinList.add(vin);
        }
        System.out.println("vinlist " + vinList);
        for (int i=0;i<vinList.size();i++){
            with().timeout(Duration.ofSeconds(30)).await().until(SqlGenerator.vehicleIsPresentInDatabase(vinList.get(i),vehicleRepository));
            with().timeout(Duration.ofSeconds(30)).await().until(SqlGenerator.testResultIsPresentInDatabase(vinList.get(i),testResultRepository));
            List<vott.models.dao.TestResult> testResultNOP=getTestResultWithVIN(vinList.get(i),testResultRepository);
            assertThat(testResultNOP.get(0).getTestCode().length()).isEqualTo(3);
        }
    }
    @Title("CB2-7553 - Handle test results with no test types in update-store function. Has two test types")
    @Test
    public void twoTestType() {
        /***
         * Vehicle test results have two test types of same test type id
         */
        TechRecordPOST techRecord = loadTechRecord(payloadPath+"twotesttype_technical-records.json");
        CompleteTestResults testResult = loadTestResults(techRecord,payloadPath+"twotesttype_test-results.json");

        VehiclesAPI.postVehicleTechnicalRecord(techRecord, v1ImplicitTokens.getBearerToken());
        TestResultAPI.postTestResult(testResult, v1ImplicitTokens.getBearerToken());

        String vin = testResult.getVin();

        with().timeout(Duration.ofSeconds(30)).await().until(SqlGenerator.vehicleIsPresentInDatabase(vin, vehicleRepository));
        with().timeout(Duration.ofSeconds(30)).await().until(SqlGenerator.testResultIsPresentInDatabase(vin, testResultRepository));

        List<vott.models.dao.TestResult> testResultNOP = getTestResultWithVIN(vin, testResultRepository);
        assertThat(testResultNOP.get(0).getTestTypeStartTimestamp()).isNotEqualTo(testResultNOP.get(1).getTestTypeStartTimestamp());
        assertThat(testResultNOP.get(0).getTestTypeEndTimestamp()).isNotEqualTo(testResultNOP.get(1).getTestTypeEndTimestamp());
        assertThat(testResultNOP.get(0).getCertificateNumber()).isNotEqualTo(testResultNOP.get(1).getCertificateNumber());
    }

    @Title("CB2-7553 - numberOfWheelsDriven accepted as string as well as string")
    @Test
    public void wheelsDrivenString() {
        /***
         * Tech records and Test Records numberOfWheelsDriven can be considered as string as well as numbers
         */
        TechRecordPOST techRecord = loadTechRecord(payloadPath+"wheelNumber_technical-records.json");

        VehiclesAPI.postVehicleTechnicalRecord(techRecord, v1ImplicitTokens.getBearerToken());

        String vin = techRecord.getVin();

        with().timeout(Duration.ofSeconds(30)).await().until(SqlGenerator.vehicleIsPresentInDatabase(vin, vehicleRepository));

        List<vott.models.dao.TechnicalRecord> techRecordNOP = getVehicleWithVIN(vin, technicalRecordRepository);
        assertThat(techRecordNOP.get(0).getNumberOfWheelsDriven()).isEqualTo("4");
    }

    @Title("CB2-7578 - Values in NOP Schema are truncated for Test Results")
    @Test
    public void testResultsTruncation() {
        /***
         * Tech records and Test Records numberOfWheelsDriven can be considered as string as well as numbers
         */
        TechRecordPOST techRecord = loadTechRecord(payloadPath+"testresultTruncation_technical-records.json");
        CompleteTestResults testResult = loadTestResults(techRecord,payloadPath+"testresultTruncation_test-results.json");

        String token = v1ImplicitTokens.getBearerToken();

        VehiclesAPI.postVehicleTechnicalRecord(techRecord, v1ImplicitTokens.getBearerToken());
        TestResultAPI.postTestResult(testResult, v1ImplicitTokens.getBearerToken());

        String vin = testResult.getVin();

        with().timeout(Duration.ofSeconds(30)).await().until(SqlGenerator.vehicleIsPresentInDatabase(vin, vehicleRepository));
        with().timeout(Duration.ofSeconds(30)).await().until(SqlGenerator.testResultIsPresentInDatabase(vin, testResultRepository));

        List<vott.models.dao.TestResult> testResultNOP = getTestResultWithVIN(vin, testResultRepository);
        List<vott.models.dao.Tester> tester = getTesterDetailsWithVehicleID(testResultNOP.get(0).getVehicleID(),testerRepository);
        List<vott.models.dao.Preparer> preparers = getPreparerDetailsWithVehicleID(testResultNOP.get(0).getVehicleID(),preparerRepository);

        // verify certificate number is not null
        assertThat(testResultNOP.get(0).getCertificateNumber()).isNotNull();

        // verify secondary certificate number
        assertThat(testResultNOP.get(0).getSecondaryCertificateNumber()).isEqualTo("12345678912345678910");

        //verify StaffId
        assertThat(tester.get(0).getStaffID()).isEqualTo("12345345678912");

        //verify preparerid
        assertThat(preparers.get(0).getPreparerID()).isEqualTo("12345678912345678910");

        //verify reasonForAbandoning
        assertThat(testResultNOP.get(0).getReasonForAbandoning().length()).isEqualTo(1200);

    }

    @Title("CB2-7578 - Values in NOP Schema are truncated for Test Results")
    @Test
    public void evlFeeds() {

        TechRecordPOST techRecord = loadTechRecord(payloadPath+"testresultTruncation_technical-records.json");
        CompleteTestResults testResult = loadTestResults(techRecord,payloadPath+"testresultTruncation_test-results.json");

        TestTypes ts = testResult.getTestTypes();
        LocalDate ld = LocalDate.now();
        OffsetDateTime datetime = OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC);

        ts.get(0).setTestExpiryDate(ld);
        ts.get(0).setTestTypeStartTimestamp(datetime);
        ts.get(0).setTestTypeEndTimestamp(datetime);
        testResult.setTestTypes(ts);
        testResult.setTestStartTimestamp(datetime);
        testResult.setTestEndTimestamp(datetime);

        VehiclesAPI.postVehicleTechnicalRecord(techRecord, v1ImplicitTokens.getBearerToken());
        TestResultAPI.postTestResult(testResult, v1ImplicitTokens.getBearerToken());
        String vin = testResult.getVin();

        with().timeout(Duration.ofSeconds(30)).await().until(SqlGenerator.vehicleIsPresentInDatabase(vin, vehicleRepository));
        with().timeout(Duration.ofSeconds(30)).await().until(SqlGenerator.testResultIsPresentInDatabase(vin, testResultRepository));

        List<vott.models.dao.TestResult> testResultNOP = getTestResultWithVIN(vin, testResultRepository);
        List<vott.models.dao.EVLView> evlViews = getEVLViewWithCertificateNumberAndVrm(testResultNOP.get(0).getCertificateNumber(),techRecord.getPrimaryVrm(),evlViewRepository);
        assertThat(evlViews.get(0)).isNotNull();
    }

    @Title("CB2-7578 - Values in NOP Schema are truncated for Test Results")
    @Test
    public void evlFeedsRekey() {

        TechRecordPOST techRecord = loadTechRecord(payloadPath+"testresultTruncation_technical-records.json");
        CompleteTestResults testResult = loadTestResults(techRecord,payloadPath+"testresultTruncation_test-results.json");

        TestTypes ts = testResult.getTestTypes();
        LocalDate ld = LocalDate.now();
        OffsetDateTime datetime = OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC);

        ts.get(0).setTestExpiryDate(ld);
        ts.get(0).setTestTypeStartTimestamp(datetime);
        ts.get(0).setTestTypeEndTimestamp(datetime);
        testResult.setTestTypes(ts);
        testResult.setTestStartTimestamp(datetime);
        testResult.setTestEndTimestamp(datetime);

        VehiclesAPI.postVehicleTechnicalRecord(techRecord, v1ImplicitTokens.getBearerToken());
        TestResultAPI.postTestResult(testResult, v1ImplicitTokens.getBearerToken());

        String vin = testResult.getVin();

        with().timeout(Duration.ofSeconds(30)).await().until(SqlGenerator.vehicleIsPresentInDatabase(vin, vehicleRepository));
        with().timeout(Duration.ofSeconds(30)).await().until(SqlGenerator.testResultIsPresentInDatabase(vin, testResultRepository));

        //add rekey records
        testResult.setTestStartTimestamp(testResult.getTestStartTimestamp().minusNanos(testResult.getTestStartTimestamp().getNano()));
        testResult.setTestEndTimestamp(testResult.getTestEndTimestamp().minusNanos(testResult.getTestEndTimestamp().getNano()));
        testResult.setTestResultId(testResult.getTestResultId()+"-1");

        //post rekey records to dynamodb
        TestResultAPI.postTestResult(testResult, v1ImplicitTokens.getBearerToken());

        with().timeout(Duration.ofSeconds(30)).await().until(SqlGenerator.vehicleIsPresentInDatabase(vin, vehicleRepository));
        with().timeout(Duration.ofSeconds(30)).await().until(SqlGenerator.testResultIsPresentInDatabase(vin, testResultRepository));

        List<vott.models.dao.TestResult> testResultNOP = getTestResultWithVIN(vin, testResultRepository);
        List<vott.models.dao.EVLView> evlViews = getEVLViewWithCertificateNumberAndVrm(testResultNOP.get(0).getCertificateNumber(),techRecord.getPrimaryVrm(),evlViewRepository);
        assertThat(evlViews.get(0)).isNotNull();
    }
    @Title("CB2-7792 - Add Authorisation Into Service data to NOP")
    @Test
    public void trailerOCO() {

        TechRecordPOST techRecord = loadTechRecord(payloadPath+"technical-records_trl_oco.json");
        VehiclesAPI.postVehicleTechnicalRecord(techRecord, v1ImplicitTokens.getBearerToken());
        String vin = techRecord.getVin();
        with().timeout(Duration.ofSeconds(30)).await().until(SqlGenerator.vehicleIsPresentInDatabase(vin, vehicleRepository));
        List<vott.models.dao.AuthIntoServices> ais = getAuthIntoServices(vin,authIntoServicesRepository);
        assertThat(ais.get(0)).isNotNull();
     }

    @Title("CB2-7793 - Add Type Approval data to NOP")
    @Test
    public void trailerApprovalType() {
        TechRecordPOST techRecord;
        TechRecords trs;
        List<ApprovalTypeEnum> approvalList = new ArrayList<ApprovalTypeEnum>();
        for (ApprovalTypeEnum e : EnumSet.allOf(ApprovalTypeEnum.class)) {approvalList.add(e);}
        for (ApprovalTypeEnum appList : approvalList) {
            techRecord=loadTechRecord(payloadPath+"technical-records_trl_oco.json");
            trs= techRecord.getTechRecord();
            trs.get(0).setApprovalType(appList);
            techRecord.setTechRecord(trs);
            VehiclesAPI.postVehicleTechnicalRecord(techRecord, v1ImplicitTokens.getBearerToken());
            String vin = techRecord.getVin();
            System.out.println("vin :"+vin);
            with().timeout(Duration.ofSeconds(30)).await().until(SqlGenerator.vehicleIsPresentInDatabase(vin, vehicleRepository));
            List<vott.models.dao.TechnicalRecord> nopsTechRecord = getVehicleWithVIN(vin,technicalRecordRepository);
            assertThat(nopsTechRecord.get(0).getApprovalType()).isEqualTo(appList.getValue());
        }
    }

    @Title("CB2-5043 - TFL View reporting ")
    @Test
    public void tflFeeds() {

        TechRecordPOST techRecord = loadTechRecord(payloadPath+"technical-records_psv.json");
        CompleteTestResults testResult = loadTestResults(techRecord,payloadPath+"test-results_psv.json");

        TestTypes ts = testResult.getTestTypes();
        LocalDate ld = LocalDate.now();
        OffsetDateTime datetime = OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC);

        ts.get(0).setTestExpiryDate(ld);
        ts.get(0).setTestTypeStartTimestamp(datetime);
        ts.get(0).setTestTypeEndTimestamp(datetime);
        testResult.setTestTypes(ts);
        testResult.setTestStartTimestamp(datetime);
        testResult.setTestEndTimestamp(datetime);

        VehiclesAPI.postVehicleTechnicalRecord(techRecord, v1ImplicitTokens.getBearerToken());
        TestResultAPI.postTestResult(testResult, v1ImplicitTokens.getBearerToken());
        String vin = testResult.getVin();

        System.out.println("vin " + vin);
        with().timeout(Duration.ofSeconds(30)).await().until(SqlGenerator.vehicleIsPresentInDatabase(vin, vehicleRepository));
        with().timeout(Duration.ofSeconds(30)).await().until(SqlGenerator.testResultIsPresentInDatabase(vin, testResultRepository));

        List<vott.models.dao.TFLView> tflViews = getTFLViewWithVin(vin,tflViewRepository);
        System.out.println(tflViews.get(0));
        assertThat(tflViews.get(0).getPNumber()).isEqualTo("87-1369561");
        assertThat(tflViews.get(0).getModTypeCode()).isEqualTo("p");
        assertThat(tflViews.get(0).getIssueDate()).isEqualTo(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        assertThat(tflViews.get(0).getTestTypeStartTimestamp()).isEqualTo(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        assertThat(tflViews.get(0).getTestExpiryDate()).isEqualTo(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    @Title("CB2-5043 - TFL View reporting Emission standards")
    @Test
    public void tflFeedsEmissionStandards() {

        TechRecordPOST techRecord ;
        CompleteTestResults testResult;

        Map<TestTypeResults.EmissionStandardEnum, String> es = new HashMap<TestTypeResults.EmissionStandardEnum, String>();
        es.put(_0_03_G_KWH_EURO_IV_PM,"01,10,");  //pass   #impacted records 13k
        es.put(_0_10_G_KWH_EURO_3_PM,"01,04,"); //pass     # impacted records 83
        //es.put(_0_16_G_KWH_EURO_3_PM,"01,09,"); //fails   # impacted records 23
        //es.put(_0_32_G_KWH_EURO_II_PM,"UNK");  fails      #impacted records 1
        //es.put(_0_08_G_KWH_EURO_3_PM,"01,09,"); //fao;s   # impacted records 24
        //es.put(GAS_EURO_IV_PM,"01,12,"); // fail         # impacted records 2
        es.put(FULL_ELECTRIC,"UNK");  // pass  #impacted records 15
        es.put(EURO_3,"UNK"); //pass       #impacted records 43
        es.put(EURO_4,"UNK"); //pass       # impacted records 319
        es.put(EURO_6,"UNK"); //pass        # impacted records 50
        es.put(EURO_VI,"UNK"); //pass       # impacted records 73

        TestTypes ts;
        String vin;
        List<vott.models.dao.TFLView> tflViews;

        for (Map.Entry<TestTypeResults.EmissionStandardEnum, String> entry : es.entrySet()) {

            techRecord = loadTechRecord(payloadPath+"technical-records_psv.json");
            testResult = loadTestResults(techRecord,payloadPath+"test-results_psv.json");

            System.out.println(entry.getKey());
            ts = testResult.getTestTypes();
            ts.get(0).setEmissionStandard(entry.getKey());
            testResult.setTestTypes(ts);

            VehiclesAPI.postVehicleTechnicalRecord(techRecord, v1ImplicitTokens.getBearerToken());
            TestResultAPI.postTestResult(testResult, v1ImplicitTokens.getBearerToken());
            vin = testResult.getVin();

            System.out.println("vin " + vin);
            with().timeout(Duration.ofSeconds(30)).await().until(SqlGenerator.vehicleIsPresentInDatabase(vin, vehicleRepository));
            with().timeout(Duration.ofSeconds(30)).await().until(SqlGenerator.testResultIsPresentInDatabase(vin, testResultRepository));

            tflViews = getTFLViewWithVin(vin,tflViewRepository);
            System.out.println(tflViews.get(0));
            assertThat(tflViews.get(0)).isNotNull();
            assertThat(tflViews.get(0).getName_exp_5()).isEqualTo(entry.getValue());
            assertThat(tflViews.get(0).getPNumber()).isEqualTo("87-1369561");
            assertThat(tflViews.get(0).getModTypeCode()).isEqualTo("p");
        }
    }

    private TechRecordPOST loadTechRecord(String fileName){
        return randomizeKeys(readTechRecord(fileName));
    }
    private CompleteTestResults loadTestResults(TechRecordPOST techRecord, String fileName){
        return matchKeys(techRecord, readTestResult(fileName));
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

    private TechRecordPOST randomizeKeys(TechRecordPOST techRecord) {
        String vin = fieldGenerator.randomVin();
        while (SqlGenerator.vehicleIsPresentInDatabase(vin, vehicleRepository).equals(Boolean.TRUE)) {vin = fieldGenerator.randomVin();}
        techRecord.setVin(vin);
        techRecord.setPrimaryVrm(fieldGenerator.randomVrm());
        return techRecord;
    }

    private CompleteTestResults matchKeys(TechRecordPOST techRecord, CompleteTestResults testResult) {
        testResult.setTestResultId(UUID.randomUUID().toString());
        // test result ID is not kept on enquiry-service retrievals: need a way to uniquely identify within test suite
        testResult.setTesterName(UUID.randomUUID().toString());
        testResult.setVin(techRecord.getVin());
        testResult.setVrm(techRecord.getPrimaryVrm());
        return testResult;
    }
}

