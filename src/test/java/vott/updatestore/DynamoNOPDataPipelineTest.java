package vott.updatestore;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import net.serenitybdd.annotations.WithTag;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.annotations.Title;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Rule;
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
import vott.models.dao.EVLView;
import vott.models.dao.TestResult;
import vott.models.dao.VtEVLAdditions;
import vott.models.dao.VtEvlCvsRemoved;
import vott.models.dto.techrecords.TechRecordPOST;
import vott.models.dto.techrecords.TechRecords;
import vott.models.dto.testresults.CompleteTestResults;
import vott.models.dto.testresults.TestTypeResults;
import vott.models.dto.testresults.TestTypes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
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
import static vott.database.sqlgeneration.SqlGenerator.*;
import static vott.models.dto.testresults.TestTypeResults.EmissionStandardEnum.*;

@RunWith(SerenityRunner.class)
public class DynamoNOPDataPipelineTest {
    @Rule
    public RetryRule retryRule = new RetryRule(3);
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
    private VtEVLAdditionsRepository vtEVLAdditionsRepository;
    private VtEvlCvsRemovedRepository vtEvlCvsRemovedRepository;
    private String payloadPath;

    private static final int WAIT_IN_SECONDS = 60;

    @Before
    public void setUp() throws Exception {

        VottConfiguration configuration = VottConfiguration.local();

        gson = GsonInstance.get();

        fieldGenerator = new FieldGenerator();

        v1ImplicitTokens = new TokenService(OAuthVersion.V1, GrantType.IMPLICIT);

        ConnectionFactory connectionFactory = new ConnectionFactory(configuration);

        vehicleRepository = new VehicleRepository(connectionFactory);

        testResultRepository = new TestResultRepository(connectionFactory);

        technicalRecordRepository = new TechnicalRecordRepository(connectionFactory);

        testerRepository = new TesterRepository(connectionFactory);

        preparerRepository = new PreparerRepository(connectionFactory);

        evlViewRepository = new EVLViewRepository(connectionFactory);

        tflViewRepository = new TFLViewRepository(connectionFactory);

        authIntoServicesRepository = new AuthIntoServicesRepository(connectionFactory);

        vtEVLAdditionsRepository = new VtEVLAdditionsRepository(connectionFactory);

        vtEvlCvsRemovedRepository = new VtEvlCvsRemovedRepository(connectionFactory);

        payloadPath = "src/main/resources/payloads/";

    }

    @WithTag("Vott")
    @Title("CB2-7258 - TC1 - testCode value truncation")
    @Test
    public void testCodeTruncation() {
        TechRecordPOST truncationTechRecord = loadTechRecord(payloadPath + "truncation_technical-records.json");
        CompleteTestResults truncationTestResult = loadTestResults(truncationTechRecord, payloadPath + "truncation_test-results.json");

        VehiclesAPI.postVehicleTechnicalRecord(truncationTechRecord, v1ImplicitTokens.getBearerToken());
        TestResultAPI.postTestResult(truncationTestResult, v1ImplicitTokens.getBearerToken());

        String vin = truncationTestResult.getVin();
        //System.out.println("vin " + vin);
        with().timeout(Duration.ofSeconds(WAIT_IN_SECONDS)).await().until(SqlGenerator.vehicleIsPresentInDatabase(vin, vehicleRepository));
        with().timeout(Duration.ofSeconds(WAIT_IN_SECONDS)).await().until(SqlGenerator.testResultIsPresentInDatabase(vin, testResultRepository));

        List<vott.models.dao.TestResult> testResultNOP = getTestResultWithVIN(vin, testResultRepository);
        assertThat(testResultNOP.get(0).getTestCode().length()).isEqualTo(3);
    }

    @WithTag("Vott")
    @Title("CB2-7548 - Remove technical-record stream from edh-marshaller - Verify Tech and test results are updated correctly")
    @Test
    public void testTechUpdate() {
/*
 EDH-MARSHALLER lambda is modified with respect to processing of tech records
 The test loads 10 set of records and verifies that it's all been loaded to NOPS
 When EDH-MARSHALLER is not processing records correctly then the test will fail
 */
        ArrayList<String> vinList = new ArrayList<>();
        TechRecordPOST truncationTechRecord;
        CompleteTestResults truncationTestResult;
        for (int i = 0; i < 20; i++) {
            truncationTechRecord = loadTechRecord(payloadPath + "performance_technical-records.json");
            truncationTestResult = loadTestResults(truncationTechRecord, payloadPath + "performance_test-results.json");
            VehiclesAPI.postVehicleTechnicalRecord(truncationTechRecord, v1ImplicitTokens.getBearerToken());
            TestResultAPI.postTestResult(truncationTestResult, v1ImplicitTokens.getBearerToken());
            String vin = truncationTestResult.getVin();
            vinList.add(vin);
        }
        //System.out.println("vinlist " + vinList);
        for (String vin : vinList) {
            with().timeout(Duration.ofSeconds(WAIT_IN_SECONDS)).await().until(SqlGenerator.vehicleIsPresentInDatabase(vin, vehicleRepository));
            with().timeout(Duration.ofSeconds(WAIT_IN_SECONDS)).await().until(SqlGenerator.testResultIsPresentInDatabase(vin, testResultRepository));
            List<TestResult> testResultNOP = getTestResultWithVIN(vin, testResultRepository);
            assertThat(testResultNOP.get(0).getTestCode().length()).isEqualTo(3);
        }
    }

    @WithTag("Vott")
    @Title("CB2-7553 - Handle test results with no test types in update-store function. Has two test types")
    @Test
    public void twoTestType() {
        /*
         Vehicle test results have two test types of same test type id
         */
        TechRecordPOST techRecord = loadTechRecord(payloadPath + "twotesttype_technical-records.json");
        CompleteTestResults testResult = loadTestResults(techRecord, payloadPath + "twotesttype_test-results.json");

        VehiclesAPI.postVehicleTechnicalRecord(techRecord, v1ImplicitTokens.getBearerToken());
        TestResultAPI.postTestResult(testResult, v1ImplicitTokens.getBearerToken());

        String vin = testResult.getVin();

        with().timeout(Duration.ofSeconds(WAIT_IN_SECONDS)).await().until(SqlGenerator.vehicleIsPresentInDatabase(vin, vehicleRepository));
        with().timeout(Duration.ofSeconds(WAIT_IN_SECONDS)).await().until(SqlGenerator.testResultIsPresentInDatabase(vin, testResultRepository));

        List<vott.models.dao.TestResult> testResultNOP = getTestResultWithVIN(vin, testResultRepository);
        assertThat(testResultNOP.get(0).getTestTypeStartTimestamp()).isNotEqualTo(testResultNOP.get(1).getTestTypeStartTimestamp());
        assertThat(testResultNOP.get(0).getTestTypeEndTimestamp()).isNotEqualTo(testResultNOP.get(1).getTestTypeEndTimestamp());
        assertThat(testResultNOP.get(0).getCertificateNumber()).isNotEqualTo(testResultNOP.get(1).getCertificateNumber());
    }


    @WithTag("Vott")
    @Title("CB2-7553 - numberOfWheelsDriven accepted as string as well as string")
    @Test
    public void wheelsDrivenString() {
        /*
         Tech records and Test Records numberOfWheelsDriven can be considered as string as well as numbers
         */
        TechRecordPOST techRecord = loadTechRecord(payloadPath + "wheelNumber_technical-records.json");

        VehiclesAPI.postVehicleTechnicalRecord(techRecord, v1ImplicitTokens.getBearerToken());

        String vin = techRecord.getVin();

        with().timeout(Duration.ofSeconds(WAIT_IN_SECONDS)).await().until(SqlGenerator.vehicleIsPresentInDatabase(vin, vehicleRepository));

        List<vott.models.dao.TechnicalRecord> techRecordNOP = getVehicleWithVIN(vin, technicalRecordRepository);
        assertThat(techRecordNOP.get(0).getNumberOfWheelsDriven()).isEqualTo("4");
    }

    @WithTag("Vott")
    @Title("CB2-7578 - Values in NOP Schema are truncated for Test Results")
    @Test
    public void testResultsTruncation() {
        /*
         Tech records and Test Records numberOfWheelsDriven can be considered as string as well as numbers
         */
        TechRecordPOST techRecord = loadTechRecord(payloadPath + "testresultTruncation_technical-records.json");
        CompleteTestResults testResult = loadTestResults(techRecord, payloadPath + "testresultTruncation_test-results.json");

        //String token = v1ImplicitTokens.getBearerToken();

        VehiclesAPI.postVehicleTechnicalRecord(techRecord, v1ImplicitTokens.getBearerToken());
        TestResultAPI.postTestResult(testResult, v1ImplicitTokens.getBearerToken());

        String vin = testResult.getVin();

        with().timeout(Duration.ofSeconds(WAIT_IN_SECONDS)).await().until(SqlGenerator.vehicleIsPresentInDatabase(vin, vehicleRepository));
        with().timeout(Duration.ofSeconds(WAIT_IN_SECONDS)).await().until(SqlGenerator.testResultIsPresentInDatabase(vin, testResultRepository));

        List<vott.models.dao.TestResult> testResultNOP = getTestResultWithVIN(vin, testResultRepository);
        List<vott.models.dao.Tester> tester = getTesterDetailsWithVehicleID(testResultNOP.get(0).getVehicleID(), testerRepository);
        List<vott.models.dao.Preparer> preparers = getPreparerDetailsWithVehicleID(testResultNOP.get(0).getVehicleID(), preparerRepository);

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
    public EvlContainer evlFeeds(String testResultFileName) throws RuntimeException {

        TechRecordPOST techRecord = loadTechRecord(payloadPath + "testresultTruncation_technical-records.json");
        CompleteTestResults testResult = loadTestResults(techRecord, payloadPath + testResultFileName);

        TestTypes ts = testResult.getTestTypes();
        OffsetDateTime datetime = OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC);

        ts.get(0).setTestExpiryDate(datetime);
        ts.get(0).setTestTypeStartTimestamp(datetime);
        ts.get(0).setTestTypeEndTimestamp(datetime);
        testResult.setTestTypes(ts);
        testResult.setTestStartTimestamp(datetime);
        testResult.setTestEndTimestamp(datetime);
        testResult.setSystemNumber(techRecord.getSystemNumber());

        //System.out.println(testResult.getVin());
        TestResultAPI.postTestResult(testResult, v1ImplicitTokens.getBearerToken());
        String vin = testResult.getVin();

        with().timeout(Duration.ofSeconds(WAIT_IN_SECONDS)).await().until(SqlGenerator.testResultIsPresentInDatabase(vin, testResultRepository));

        List<vott.models.dao.TestResult> testResultNOP = getTestResultWithVIN(vin, testResultRepository);
        List<vott.models.dao.EVLView> evlViews = getEVLViewWithCertificateNumberAndVrm(testResultNOP.get(0).getCertificateNumber(), techRecord.getPrimaryVrm(), evlViewRepository);

        EvlContainer container = new EvlContainer();
        container.evlView = evlViews.get(0);
        container.techRecord = techRecord;
        container.testResult = testResultNOP.get(0);

        return container;
    }

    @WithTag("Vott")
    @Title("CB2-8008 - Add VT Data to EVL SQL View ")
    @Test
    public void vt_evl_test_same_as_evl_view() throws SQLException, RuntimeException {
        /*
         values are updated to et_evl_additions table and expect the EVL_VIEW to relevant results
         */
        EvlContainer evl = evlFeeds("EvlView_test-results-pass.json");
        EVLView evlViews = evl.evlView;

        VtEVLAdditions vt = new VtEVLAdditions();
        vt.setVrmTrmID(evlViews.getVrmTrm());
        vt.setCertificateNumber(evlViews.getCertificateNumber());
        vt.setTestExpiryDate(evlViews.getTestExpiryDate());

        upsertVTEVLADDITIONS(vtEVLAdditionsRepository, vt);

        List<vott.models.dao.EVLView> evloutput = getEVLViewWithCertificateNumberAndVrm(
                evlViews.getCertificateNumber(), evlViews.getVrmTrm(), evlViewRepository);
        assertThat(evloutput.size()).isEqualTo(1);
    }


    @WithTag("Vott")
    @Title("CB2-8008 - Add VT Data to EVL SQL View ")
    @Test
    public void vt_evl_test_certificate_differs() throws SQLException, RuntimeException {
        /*
         values are updated to et_evl_additions table and expect the EVL_VIEW to relevant results
         */

        EvlContainer evl = evlFeeds("EvlView_test-results-pass.json");
        EVLView evlViews = evl.evlView;

        VtEVLAdditions vt = new VtEVLAdditions();
        vt.setVrmTrmID(evlViews.getVrmTrm());
        vt.setCertificateNumber(evlViews.getCertificateNumber() + "11");
        vt.setTestExpiryDate(evlViews.getTestExpiryDate());

        upsertVTEVLADDITIONS(vtEVLAdditionsRepository, vt);

        List<vott.models.dao.EVLView> evloutput = getEVLViewWithCertificateNumberAndVrm(
                evlViews.getCertificateNumber(), evlViews.getVrmTrm(), evlViewRepository);
        assertThat(evloutput.get(0)).isNotNull();

        evloutput = getEVLViewWithCertificateNumberAndVrm(
                evlViews.getCertificateNumber() + "11", evlViews.getVrmTrm(), evlViewRepository);
        assertThat(evloutput.get(0)).isNotNull();
    }

    @WithTag("Vott")
    @Title("CB2-8008 - Add VT Data to EVL SQL View ")
    @Test
    public void vt_evl_test_vrm_differs() throws SQLException, RuntimeException {
        /*
         values are updated to et_evl_additions table and expect the EVL_VIEW to relevant results
         */

        EvlContainer evl = evlFeeds("EvlView_test-results-pass.json");
        EVLView evlViews = evl.evlView;

        VtEVLAdditions vt = new VtEVLAdditions();
        vt.setVrmTrmID(evlViews.getVrmTrm() + "1");
        vt.setCertificateNumber(evlViews.getCertificateNumber());
        vt.setTestExpiryDate(evlViews.getTestExpiryDate());

        upsertVTEVLADDITIONS(vtEVLAdditionsRepository, vt);

        List<vott.models.dao.EVLView> evloutput = getEVLViewWithCertificateNumberAndVrm(
                evlViews.getCertificateNumber(), evlViews.getVrmTrm(), evlViewRepository);
        assertThat(evloutput.get(0)).isNotNull();

        evloutput = getEVLViewWithCertificateNumberAndVrm(
                evlViews.getCertificateNumber(), evlViews.getVrmTrm() + "1", evlViewRepository);
        assertThat(evloutput.get(0)).isNotNull();
    }

    @WithTag("Vott")
    @Title("CB2-8008 - Add VT Data to EVL SQL View ")
    @Test
    public void vt_evl_test_expiry_date_differs() throws SQLException, RuntimeException {
        /*
         values are updated to et_evl_additions table and expect the EVL_VIEW to relevant results
         */
        EvlContainer evl = evlFeeds("EvlView_test-results-pass.json");
        EVLView evlViews = evl.evlView;
        VtEVLAdditions vt = new VtEVLAdditions();
        vt.setVrmTrmID(evlViews.getVrmTrm());
        vt.setCertificateNumber(evlViews.getCertificateNumber());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ld = LocalDateTime.parse(evlViews.getTestExpiryDate(), df);
        String replace_date = ld.plusDays(1).toString();
        vt.setTestExpiryDate(replace_date);

        upsertVTEVLADDITIONS(vtEVLAdditionsRepository, vt);

        List<vott.models.dao.EVLView> evloutput = getEVLViewWithCertificateNumberAndVrm(
                evlViews.getCertificateNumber(), evlViews.getVrmTrm(), evlViewRepository);
        assertThat(evloutput.size()).isEqualTo(1);
        assertThat(evloutput.get(0).getTestExpiryDate().substring(0, 10)).isEqualTo(replace_date.substring(0, 10));
    }

    @WithTag("Vott")
    @Title("CB2-8008 - Add VT Data to EVL SQL View ")
    @Test
    public void vt_evl_test_expiry_date_differs_variation1() throws SQLException, RuntimeException {
        /*
         values are updated to et_evl_additions table and expect the EVL_VIEW to relevant results
         */
        EvlContainer evl = evlFeeds("EvlView_test-results-pass.json");
        EVLView evlViews = evl.evlView;

        VtEVLAdditions vt = new VtEVLAdditions();
        vt.setVrmTrmID(evlViews.getVrmTrm());
        vt.setCertificateNumber(evlViews.getCertificateNumber());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ld = LocalDateTime.parse(evlViews.getTestExpiryDate(), df);
        String replace_date = ld.minusDays(1).toString();
        vt.setTestExpiryDate(replace_date);

        upsertVTEVLADDITIONS(vtEVLAdditionsRepository, vt);

        List<vott.models.dao.EVLView> evloutput = getEVLViewWithCertificateNumberAndVrm(
                evlViews.getCertificateNumber(), evlViews.getVrmTrm(), evlViewRepository);
        assertThat(evloutput.size()).isEqualTo(1);
        assertThat(evloutput.get(0).getTestExpiryDate().substring(0, 10)).isEqualTo(evlViews.getTestExpiryDate().substring(0, 10));
    }

    @WithTag("Vott")
    @Title("CB2-8008 - Add VT Data to EVL SQL View ")
    @Test
    public void vt_evl_test_expiry_date_cert_differs() throws SQLException, RuntimeException {
        /*
         values are updated to et_evl_additions table and expect the EVL_VIEW to relevant results
         */
        EvlContainer evl = evlFeeds("EvlView_test-results-pass.json");
        EVLView evlViews = evl.evlView;
        VtEVLAdditions vt = new VtEVLAdditions();
        vt.setVrmTrmID(evlViews.getVrmTrm());
        vt.setCertificateNumber(evlViews.getCertificateNumber() + "11");
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ld = LocalDateTime.parse(evlViews.getTestExpiryDate(), df);
        String replace_date = ld.plusDays(1).toString();
        vt.setTestExpiryDate(replace_date);

        upsertVTEVLADDITIONS(vtEVLAdditionsRepository, vt);

        List<vott.models.dao.EVLView> evloutput = getEVLViewWithCertificateNumberAndVrm(
                evlViews.getCertificateNumber() + "11", evlViews.getVrmTrm(), evlViewRepository);
        assertThat(evloutput.size()).isEqualTo(1);
        assertThat(evloutput.get(0).getTestExpiryDate().substring(0, 10)).isEqualTo(replace_date.substring(0, 10));
        evloutput = getEVLViewWithCertificateNumberAndVrm(
                evlViews.getCertificateNumber(), evlViews.getVrmTrm(), evlViewRepository);
        assertThat(evloutput.size()).isEqualTo(1);
    }

    @WithTag("Vott")
    @Title("CB2-8008 - Add VT Data to EVL SQL View ")
    @Test
    public void evl_vt_pass_nops_fail_vt_recent_date() throws SQLException, RuntimeException {

        /*
         VT has passed test with recent dates
         NOPS has failed test with old dates
         with different certificate numbers and same system number
         Output
         EVL View reports the VT test
         */

        EvlContainer evl = evlFeeds("EvlView_test-results-fail.json");
        EVLView evlViews = evl.evlView;
        TechRecordPOST techRecord = evl.techRecord;
        String vin = techRecord.getVin();

        VtEvlCvsRemoved vtr = new VtEvlCvsRemoved();
        vtr.setVin(vin);
        vtr.setVrm(techRecord.getPrimaryVrm());
        vtr.setCertificateNumber(evlViews.getCertificateNumber() + "11");
        vtr.setSystemNumber(techRecord.getSystemNumber());

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ld = LocalDateTime.parse(evlViews.getTestExpiryDate(), df);
        String replace_date = ld.plusDays(1).toString();
        vtr.setTestStartDate(replace_date);
        replace_date = ld.plusYears(1).toString(); // no significance
        vtr.setTestExpiryDate(replace_date);
        vtr.setVrmTestRecord("test-record");

        upsertVtEvlCvsRemoved(vtEvlCvsRemovedRepository, vtr);

        List<vott.models.dao.VtEvlCvsRemoved> rs = getVTEVLRecordsWithVin(vin, vtEvlCvsRemovedRepository);
        //System.out.println(rs);
        assertThat(rs.size()).isEqualTo(1);

        VtEVLAdditions vt = new VtEVLAdditions();
        vt.setVrmTrmID(evlViews.getVrmTrm());
        vt.setCertificateNumber(evlViews.getCertificateNumber() + "11");
        vt.setTestExpiryDate(replace_date);

        upsertVTEVLADDITIONS(vtEVLAdditionsRepository, vt);

        List<vott.models.dao.EVLView> evloutput = getEVLViewWithCertificateNumberAndVrm(
                evlViews.getCertificateNumber(), evlViews.getVrmTrm(), evlViewRepository);

        assertThat(evloutput.size()).isEqualTo(1);

        evloutput = getEVLViewWithCertificateNumberAndVrm(
                evlViews.getCertificateNumber() + "11", evlViews.getVrmTrm(), evlViewRepository);
        assertThat(evloutput.size()).isEqualTo(1);

        evloutput = getEVLViewWithVrm(evlViews.getVrmTrm(), evlViewRepository);
        assertThat(evloutput.size()).isEqualTo(2);
        assertThat(evloutput.get(0).getTestExpiryDate().substring(0, 10)).isEqualTo(replace_date.substring(0, 10));
        assertThat(evloutput.get(0).getCertificateNumber()).isEqualTo(evlViews.getCertificateNumber() + "11");
    }

    @WithTag("Vott")
    @Title("CB2-8008 - Add VT Data to EVL SQL View ")
    @Test
    public void evl_vt_pass_nops_fail_vt_recent_date_same_cert() throws SQLException, RuntimeException {

        /*
         VT has passed test with recent dates
         NOPS has failed test with old dates
         with same certificate numbers and same system number
         Output
         EVL View reports the VT test
         */

        EvlContainer evl = evlFeeds("EvlView_test-results-fail.json");
        EVLView evlViews = evl.evlView;
        TechRecordPOST techRecord = evl.techRecord;
        String vin = techRecord.getVin();

        VtEvlCvsRemoved vtr = new VtEvlCvsRemoved();
        vtr.setVin(vin);
        vtr.setVrm(techRecord.getPrimaryVrm());
        vtr.setCertificateNumber(evlViews.getCertificateNumber());
        vtr.setSystemNumber(techRecord.getSystemNumber());

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ld = LocalDateTime.parse(evlViews.getTestExpiryDate(), df);
        String replace_date = ld.plusDays(1).toString();
        vtr.setTestStartDate(replace_date);
        replace_date = ld.plusYears(1).toString(); // no significance
        vtr.setTestExpiryDate(replace_date);
        vtr.setVrmTestRecord("test-record");

        upsertVtEvlCvsRemoved(vtEvlCvsRemovedRepository, vtr);

        List<vott.models.dao.VtEvlCvsRemoved> rs = getVTEVLRecordsWithVin(vin, vtEvlCvsRemovedRepository);
        //.println(rs);
        assertThat(rs.size()).isEqualTo(1);

        VtEVLAdditions vt = new VtEVLAdditions();
        vt.setVrmTrmID(evlViews.getVrmTrm());
        vt.setCertificateNumber(evlViews.getCertificateNumber());
        vt.setTestExpiryDate(replace_date);

        upsertVTEVLADDITIONS(vtEVLAdditionsRepository, vt);

        List<vott.models.dao.EVLView> evloutput = getEVLViewWithCertificateNumberAndVrm(
                evlViews.getCertificateNumber(), evlViews.getVrmTrm(), evlViewRepository);

        assertThat(evloutput.size()).isEqualTo(1);
        assertThat(evloutput.get(0).getTestExpiryDate().substring(0, 10)).isEqualTo(replace_date.substring(0, 10));
        assertThat(evloutput.get(0).getCertificateNumber()).isEqualTo(evlViews.getCertificateNumber());
    }

    @WithTag("Vott")
    @Title("CB2-8008 - Add VT Data to EVL SQL View ")
    @Test
    public void evl_vt_pass_nops_fail_vt_old_date() throws SQLException, RuntimeException {

        /*
         values are updated to et_evl_additions table and expect the EVL_VIEW to relevant results
         */

        EvlContainer evl = evlFeeds("EvlView_test-results-fail.json");
        EVLView evlViews = evl.evlView;
        TechRecordPOST techRecord = evl.techRecord;
        String vin = techRecord.getVin();


        VtEvlCvsRemoved vtr = new VtEvlCvsRemoved();
        vtr.setVin(vin);
        vtr.setVrm(techRecord.getPrimaryVrm());
        vtr.setCertificateNumber(evlViews.getCertificateNumber() + "11");
        vtr.setSystemNumber(techRecord.getSystemNumber());

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ld = LocalDateTime.parse(evlViews.getTestExpiryDate(), df);
        String replace_date = ld.minusDays(1).toString();
        vtr.setTestStartDate(replace_date);
        replace_date = ld.minusYears(1).toString(); // no significance
        vtr.setTestExpiryDate(replace_date);
        vtr.setVrmTestRecord("test-record");

        upsertVtEvlCvsRemoved(vtEvlCvsRemovedRepository, vtr);

        List<vott.models.dao.VtEvlCvsRemoved> rs = getVTEVLRecordsWithVin(vin, vtEvlCvsRemovedRepository);
        //System.out.println(rs);
        assertThat(rs.size()).isEqualTo(0);

        List<vott.models.dao.EVLView> evloutput = getEVLViewWithCertificateNumberAndVrm(
                evlViews.getCertificateNumber(), evlViews.getVrmTrm(), evlViewRepository);

        assertThat(evloutput.size()).isEqualTo(1);

        evloutput = getEVLViewWithCertificateNumberAndVrm(
                evlViews.getCertificateNumber() + "11", evlViews.getVrmTrm(), evlViewRepository);
        assertThat(evloutput.size()).isEqualTo(0);

    }

    @WithTag("Vott")
    @Title("CB2-8008 - Add VT Data to EVL SQL View ")
    @Test
    public void evl_vt_pass_nops_fail_vt_same_date() throws SQLException, RuntimeException {

        /*
         values are updated to et_evl_additions table and expect the EVL_VIEW to relevant results
         */

        EvlContainer evl = evlFeeds("EvlView_test-results-fail.json");
        EVLView evlViews = evl.evlView;
        TechRecordPOST techRecord = evl.techRecord;
        String vin = techRecord.getVin();

        VtEvlCvsRemoved vtr = new VtEvlCvsRemoved();
        vtr.setVin(vin);
        vtr.setVrm(techRecord.getPrimaryVrm());
        vtr.setCertificateNumber(evlViews.getCertificateNumber() + "11");
        vtr.setSystemNumber(techRecord.getSystemNumber());
        vtr.setTestStartDate(evlViews.getTestExpiryDate());
        vtr.setTestExpiryDate(evlViews.getTestExpiryDate());
        vtr.setVrmTestRecord("test-record");

        upsertVtEvlCvsRemoved(vtEvlCvsRemovedRepository, vtr);

        List<vott.models.dao.VtEvlCvsRemoved> rs = getVTEVLRecordsWithVin(vin, vtEvlCvsRemovedRepository);
        //System.out.println(rs);
        assertThat(rs.size()).isEqualTo(0);

        VtEVLAdditions vt = new VtEVLAdditions();
        vt.setVrmTrmID(evlViews.getVrmTrm());
        vt.setCertificateNumber(evlViews.getCertificateNumber() + "11");
        vt.setTestExpiryDate(evlViews.getTestExpiryDate());

        upsertVTEVLADDITIONS(vtEVLAdditionsRepository, vt);

        List<vott.models.dao.EVLView> evloutput = getEVLViewWithCertificateNumberAndVrm(
                evlViews.getCertificateNumber(), evlViews.getVrmTrm(), evlViewRepository);

        assertThat(evloutput.size()).isEqualTo(1);

        evloutput = getEVLViewWithCertificateNumberAndVrm(
                evlViews.getCertificateNumber() + "11", evlViews.getVrmTrm(), evlViewRepository);
        assertThat(evloutput.size()).isEqualTo(1);

        evloutput = getEVLViewWithVrm(evlViews.getVrmTrm(), evlViewRepository);
        assertThat(evloutput.size()).isEqualTo(2);
        assertThat(evloutput.get(0).getTestExpiryDate().substring(0, 10)).isEqualTo(evlViews.getTestExpiryDate().substring(0, 10));
        assertThat(evloutput.get(0).getCertificateNumber()).isEqualTo(evlViews.getCertificateNumber());
    }

    @WithTag("Vott")
    @Title("CB2-8008 - Add VT Data to EVL SQL View ")
    @Test
    public void evl_only_vt() throws SQLException, RuntimeException {

        /*
         values are updated to et_evl_additions table and expect the EVL_VIEW to relevant results
         */

        EvlContainer evl = evlFeeds("EvlView_test-results-fail.json");
        EVLView evlViews = evl.evlView;
        TechRecordPOST techRecord = evl.techRecord;
        String vin = techRecord.getVin();

        VtEvlCvsRemoved vtr = new VtEvlCvsRemoved();
        vtr.setVin(vin);
        vtr.setVrm(techRecord.getPrimaryVrm() + "V");
        vtr.setCertificateNumber(evlViews.getCertificateNumber() + "11");
        vtr.setSystemNumber(techRecord.getSystemNumber() + "1");
        vtr.setTestStartDate(evlViews.getTestExpiryDate());
        vtr.setTestExpiryDate(evlViews.getTestExpiryDate());
        vtr.setVrmTestRecord("test-record");

        upsertVtEvlCvsRemoved(vtEvlCvsRemovedRepository, vtr);

        List<vott.models.dao.VtEvlCvsRemoved> rs = getVTEVLRecordsWithVin(vin, vtEvlCvsRemovedRepository);
        //System.out.println(rs);
        assertThat(rs.get(0).getVrm()).isEqualTo(evlViews.getVrmTrm() + "V");

        VtEVLAdditions vt = new VtEVLAdditions();
        vt.setVrmTrmID(evlViews.getVrmTrm() + "V");
        vt.setCertificateNumber(evlViews.getCertificateNumber() + "11");
        vt.setTestExpiryDate(evlViews.getTestExpiryDate());

        upsertVTEVLADDITIONS(vtEVLAdditionsRepository, vt);

        List<vott.models.dao.EVLView> evloutput = getEVLViewWithCertificateNumberAndVrm(
                evlViews.getCertificateNumber(), evlViews.getVrmTrm(), evlViewRepository);

        assertThat(evloutput.size()).isEqualTo(1);

        evloutput = getEVLViewWithCertificateNumberAndVrm(
                evlViews.getCertificateNumber() + "11", evlViews.getVrmTrm() + "V", evlViewRepository);
        assertThat(evloutput.size()).isEqualTo(1);

    }

    @WithTag("Vott")
    @Title("CB2-7578 - Values in NOP Schema are truncated for Test Results")
    @Test
    public void evlFeedsRekey() {
        /*
         values are updated to rekey to dynamoDB table and expect the EVL_VIEW to show results once
         */

        TechRecordPOST techRecord = loadTechRecord(payloadPath + "testresultTruncation_technical-records.json");
        CompleteTestResults testResult = loadTestResults(techRecord, payloadPath + "testresultTruncation_test-results.json");

        TestTypes ts = testResult.getTestTypes();
        OffsetDateTime datetime = OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC);

        ts.get(0).setTestExpiryDate(datetime);
        ts.get(0).setTestTypeStartTimestamp(datetime);
        ts.get(0).setTestTypeEndTimestamp(datetime);
        testResult.setTestTypes(ts);
        testResult.setTestStartTimestamp(datetime);
        testResult.setTestEndTimestamp(datetime);

        VehiclesAPI.postVehicleTechnicalRecord(techRecord, v1ImplicitTokens.getBearerToken());
        TestResultAPI.postTestResult(testResult, v1ImplicitTokens.getBearerToken());

        String vin = testResult.getVin();

        with().timeout(Duration.ofSeconds(WAIT_IN_SECONDS)).await().until(SqlGenerator.vehicleIsPresentInDatabase(vin, vehicleRepository));
        with().timeout(Duration.ofSeconds(WAIT_IN_SECONDS)).await().until(SqlGenerator.testResultIsPresentInDatabase(vin, testResultRepository));

        //add rekey records
        testResult.setTestStartTimestamp(testResult.getTestStartTimestamp().minusNanos(testResult.getTestStartTimestamp().getNano()));
        testResult.setTestEndTimestamp(testResult.getTestEndTimestamp().minusNanos(testResult.getTestEndTimestamp().getNano()));
        testResult.setTestResultId(testResult.getTestResultId() + "-1");

        //post rekey records to dynamodb
        TestResultAPI.postTestResult(testResult, v1ImplicitTokens.getBearerToken());

        with().timeout(Duration.ofSeconds(WAIT_IN_SECONDS)).await().until(SqlGenerator.vehicleIsPresentInDatabase(vin, vehicleRepository));
        with().timeout(Duration.ofSeconds(WAIT_IN_SECONDS)).await().until(SqlGenerator.testResultIsPresentInDatabase(vin, testResultRepository));

        List<vott.models.dao.TestResult> testResultNOP = getTestResultWithVIN(vin, testResultRepository);
        List<vott.models.dao.EVLView> evlViews = getEVLViewWithCertificateNumberAndVrm(testResultNOP.get(0).getCertificateNumber(), techRecord.getPrimaryVrm(), evlViewRepository);
        assertThat(evlViews.get(0)).isNotNull();
    }

    @WithTag("Vott")
    @Title("CB2-7792 - Add Authorisation Into Service data to NOP")
    @Test
    public void trailerOCO() {

        TechRecordPOST techRecord = loadTechRecord(payloadPath + "technical-records_trl_oco.json");
        VehiclesAPI.postVehicleTechnicalRecord(techRecord, v1ImplicitTokens.getBearerToken());
        String vin = techRecord.getVin();
        with().timeout(Duration.ofSeconds(WAIT_IN_SECONDS)).await().until(SqlGenerator.vehicleIsPresentInDatabase(vin, vehicleRepository));
        List<vott.models.dao.AuthIntoServices> ais = getAuthIntoServices(vin, authIntoServicesRepository);
        assertThat(ais.get(0)).isNotNull();
    }

    @WithTag("Vott")
    @Title("CB2-7793 - Add Type Approval data to NOP")
    @Test
    public void trailerApprovalType() {
        TechRecordPOST techRecord;
        TechRecords trs;
        SoftAssertions softly = new SoftAssertions();
        List<ApprovalTypeEnum> approvalList = new ArrayList<>(EnumSet.allOf(ApprovalTypeEnum.class));
        for (ApprovalTypeEnum appList : approvalList) {
            techRecord = loadTechRecord(payloadPath + "technical-records_trl_oco.json");
            trs = techRecord.getTechRecord();
            trs.get(0).setApprovalType(appList);
            techRecord.setTechRecord(trs);
            VehiclesAPI.postVehicleTechnicalRecord(techRecord, v1ImplicitTokens.getBearerToken());
            String vin = techRecord.getVin();
            //System.out.println("vin :" + vin);
            with().timeout(Duration.ofSeconds(WAIT_IN_SECONDS)).await().until(SqlGenerator.vehicleIsPresentInDatabase(vin, vehicleRepository));
            List<vott.models.dao.TechnicalRecord> nopsTechRecord = getVehicleWithVIN(vin, technicalRecordRepository);
            softly.assertThat(nopsTechRecord.get(0).getApprovalType()).isEqualTo(appList.getValue());
            //System.out.println("Expected : " + appList.getValue() + "  Actual : " + nopsTechRecord.get(0).getApprovalType());
        }
    }

    @WithTag("Vott")
    @Title("CB2-8786 - Update TFL extract to include LNV and LNZ tests. ")
    @Test
    public void tflFeedsLbp() {
        /*
         Given a passed low emissions test is created with a test code of lbp
         When TFL view is queried
         Then test is returned in view results
         And serial number of certificate matches input
         And certification modification type matches input
         And test status = 1
         And valid from and issue date = date test was created
         And expiry date is not blank

         */
        TechRecordPOST techRecord = loadTechRecord(payloadPath + "TflView_technical-records_psv_lbp.json");
        CompleteTestResults testResult = loadTestResults(techRecord, payloadPath + "TflView_test-results_psv_lbp.json");
        checkTestResultOnTFLView(techRecord, testResult);
    }

    @WithTag("Vott")
    @Title("CB2-8786 - Update TFL extract to include LNV and LNZ tests. ")
    @Test
    public void tflFeedsLcp() {
        /*
         Given a passed low emissions test is created with a test code of lcp
         When TFL view is queried
         Then test is returned in view results
         */
        TechRecordPOST techRecord = loadTechRecord(payloadPath + "TflView_technical-records_psv_lcp.json");
        CompleteTestResults testResult = loadTestResults(techRecord, payloadPath + "TflView_test-results_psv_lcp.json");
        checkTestResultOnTFLView(techRecord, testResult);
    }

    @WithTag("Vott")
    @Title("CB2-8786 - Update TFL extract to include LNV and LNZ tests. ")
    @Test
    public void tflFeedsLdv() {
        /*
         Given a passed low emissions test is created with a test code of ldv
         When TFL view is queried
         Then test is returned in view results
         */
        TechRecordPOST techRecord = loadTechRecord(payloadPath + "TflView_technical-records_hgv_ldv.json");
        CompleteTestResults testResult = loadTestResults(techRecord, payloadPath + "TflView_test-results_hgv_ldv.json");
        checkTestResultOnTFLView(techRecord, testResult);
    }

    @WithTag("Vott")
    @Title("CB2-8786 - Update TFL extract to include LNV and LNZ tests. ")
    @Test
    public void tflFeedsLev() {
        /*
         Given a passed low emissions test is created with a test code of Lev
         When TFL view is queried
         Then test is returned in view results
         */
        TechRecordPOST techRecord = loadTechRecord(payloadPath + "TflView_technical-records_hgv_lev.json");
        CompleteTestResults testResult = loadTestResults(techRecord, payloadPath + "TflView_test-results_hgv_lev.json");
        checkTestResultOnTFLView(techRecord, testResult);
    }

    @WithTag("Vott")
    @Title("CB2-8786 - Update TFL extract to include LNV and LNZ tests. ")
    @Test
    public void tflFeedsLnp() {
        /*
         Given a passed low emissions test is created with a test code of lnp
         When TFL view is queried
         Then test is returned in view results
         */
        TechRecordPOST techRecord = loadTechRecord(payloadPath + "TflView_technical-records_psv_lnp.json");
        CompleteTestResults testResult = loadTestResults(techRecord, payloadPath + "TflView_test-results_psv_lnp.json");
        checkTestResultOnTFLView(techRecord, testResult);
    }

    @WithTag("Vott")
    @Title("CB2-8786 - Update TFL extract to include LNV and LNZ tests. ")
    @Test
    public void tflFeedsLnv() {
        /*
         Given a passed low emissions test is created with a test code of Lnv
         When TFL view is queried
         Then test is returned in view results
         */
        TechRecordPOST techRecord = loadTechRecord(payloadPath + "TflView_technical-records_hgv_lnv.json");
        CompleteTestResults testResult = loadTestResults(techRecord, payloadPath + "TflView_test-results_hgv_lnv.json");
        checkTestResultOnTFLView(techRecord, testResult);
    }

    @WithTag("Vott")
    @Title("CB2-8786 - Update TFL extract to include LNV and LNZ tests. ")
    @Test
    public void tflFeedsLnz() {
        /*
         Given a passed low emissions test is created with a test code of Lnz
         When TFL view is queried
         Then test is returned in view results
         */
        TechRecordPOST techRecord = loadTechRecord(payloadPath + "TflView_technical-records_lgv_lnz.json");
        CompleteTestResults testResult = loadTestResults(techRecord, payloadPath + "TfLView_test-results_lgv_lnz.json");
        checkTestResultOnTFLView(techRecord, testResult);
    }

    @WithTag("Vott")
    @Title("CB2-8967 - Update TfL view logic to handle NULL Mod Type Codes. ")
    @Test
    public void tflFeedsDeskBasedNoModTypeCode() {
        /*
         Given a low emissions test has a value of NULL in field modTypeCode on fuel emissions table
         When TFL view is queried
         Then test has a CertificationModificationType defaulted to p
         */
        TechRecordPOST techRecord = loadTechRecord(payloadPath + "TflView_technical-records_desk_based.json");
        CompleteTestResults testResult = loadTestResults(techRecord, payloadPath + "TfLView_test-results_desk_based.json");
        checkTestResultOnTFLView(techRecord, testResult);
    }

    @WithTag("Vott")
    @Title("CB2-8967 - Update TfL view logic to handle NULL Mod Type Codes. ")
    @Test
    public void tflFeedsCertDoesNotStartLP() {
        /*
         Given a low emissions test is created with a certificate number that does not start LP
         When TFL view is queried
         Then test will not be on view
         */
        TechRecordPOST techRecord = loadTechRecord(payloadPath + "TflView_technical-records_hgv_ldv.json");
        CompleteTestResults testResult = loadTestResults(techRecord, payloadPath + "TfLView_test-results_hgv_ldv_cert_not_LP.json");
        checkTestResultNotOnTFL(techRecord, testResult);
    }


    private List<vott.models.dao.TFLView> insertDataViaApiAndGetTFLViewByVIN(TechRecordPOST techRecord, CompleteTestResults testResult) {
        TestTypes ts = testResult.getTestTypes();
        OffsetDateTime datetime = OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC);

        ts.get(0).setTestExpiryDate(datetime);
        ts.get(0).setTestTypeStartTimestamp(datetime);
        ts.get(0).setTestTypeEndTimestamp(datetime);
        testResult.setTestTypes(ts);
        testResult.setTestStartTimestamp(datetime);
        testResult.setTestEndTimestamp(datetime);

        VehiclesAPI.postVehicleTechnicalRecord(techRecord, v1ImplicitTokens.getBearerToken());
        TestResultAPI.postTestResult(testResult, v1ImplicitTokens.getBearerToken());
        String vin = testResult.getVin();

        with().timeout(Duration.ofSeconds(WAIT_IN_SECONDS)).await().until(SqlGenerator.vehicleIsPresentInDatabase(vin, vehicleRepository));
        with().timeout(Duration.ofSeconds(WAIT_IN_SECONDS)).await().until(SqlGenerator.testResultIsPresentInDatabase(vin, testResultRepository));

        return getTFLViewWithVin(vin, tflViewRepository);

    }


    private void checkTestResultOnTFLView(TechRecordPOST techRecord, CompleteTestResults testResult) {

        List<vott.models.dao.TFLView> tflViews = insertDataViaApiAndGetTFLViewByVIN(techRecord, testResult);
        //System.out.println(tflViews.get(0));
        assertThat(tflViews.size()).isGreaterThan(0);
        //VRM
        assertThat(tflViews.get(0).getVrm()).isEqualTo(testResult.getVrm());
        //VIN
        assertThat(tflViews.get(0).getVin()).isEqualTo(testResult.getVin());
        //serial number of certificate
        assertThat(tflViews.get(0).getSerialNumberOfCertificate()).isEqualTo(testResult.getTestTypes().get(0).getCertificateNumber());
        //certification modification type
        //mod type code can be null in which case TFL view will default to value of p
        String expectedModTypeCode = testResult.getTestTypes().get(0).getModType() == null ? "p" : testResult.getTestTypes().get(0).getModType().getCode().toString();
        assertThat(tflViews.get(0).getCertificationModificationType()).isEqualTo(expectedModTypeCode.replace("\"", ""));
        //test status
        assertThat(tflViews.get(0).getTestStatus()).isEqualTo("1");
        //valid from date
        assertThat(tflViews.get(0).getTestValidFromDate()).isEqualTo(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        //expiry date
        assertThat(tflViews.get(0).getTestExpiryDate()).isNotNull();
        //issued by
        assertThat(tflViews.get(0).getIssuedBy()).isEqualTo("87-1369561");
        //issue date
        assertThat(tflViews.get(0).getIssueDate()).isEqualTo(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        //issue date time
        assertThat(tflViews.get(0).getIssueDateTime()).isNotNull();
    }

    private void checkTestResultNotOnTFL(TechRecordPOST techRecord, CompleteTestResults testResult) {

        List<vott.models.dao.TFLView> tflViews = insertDataViaApiAndGetTFLViewByVIN(techRecord, testResult);
        assertThat(tflViews.size()).isEqualTo(0);
    }

    @WithTag("Vott")
    @Title("CB2-5043 - TFL View reporting Emission standards")
    @Test
    public void tflFeedsEmissionStandards() {

        /*
         TFL View based on emission standards
         */
        TechRecordPOST techRecord;
        CompleteTestResults testResult;

        Map<TestTypeResults.EmissionStandardEnum, String> es = new HashMap<>();
        //possible values allowed by test result service
        es.put(_0_10_G_KWH_EURO_3_PM, "4");
        es.put(_0_03_G_KWH_EURO_IV_PM, "10");
        es.put(Euro_3, "4");
        es.put(EURO_4, "5");
        es.put(EURO_5, "6");
        es.put(EURO_6, "13");
        es.put(EURO_V, "11");
        es.put(EURO_VI, "13");
        es.put(FULL_ELECTRIC, "13");

        for (Map.Entry<TestTypeResults.EmissionStandardEnum, String> entry : es.entrySet()) {

            techRecord = loadTechRecord(payloadPath + "technical-records_psv.json");
            testResult = loadTestResults(techRecord, payloadPath + "test-results_psv.json");

            //System.out.println(entry.getKey());
            TestTypes ts = testResult.getTestTypes();
            ts.get(0).setEmissionStandard(entry.getKey());
            testResult.setTestTypes(ts);

            VehiclesAPI.postVehicleTechnicalRecord(techRecord, v1ImplicitTokens.getBearerToken());
            TestResultAPI.postTestResult(testResult, v1ImplicitTokens.getBearerToken());
            String vin = testResult.getVin();

            //System.out.println("vin " + vin);
            with().timeout(Duration.ofSeconds(WAIT_IN_SECONDS)).await().until(SqlGenerator.vehicleIsPresentInDatabase(vin, vehicleRepository));
            with().timeout(Duration.ofSeconds(WAIT_IN_SECONDS)).await().until(SqlGenerator.testResultIsPresentInDatabase(vin, testResultRepository));

            List<vott.models.dao.TFLView> tflViews = getTFLViewWithVin(vin, tflViewRepository);
            assertThat(tflViews.size()).isGreaterThan(0);
            //System.out.println(tflViews.get(0));
            assertThat(tflViews.get(0)).isNotNull();
            assertThat(tflViews.get(0).getEmissionClassificationCode()).isEqualTo(entry.getValue());
        }
    }

    private TechRecordPOST loadTechRecord(String fileName) {
        return randomizeKeys(readTechRecord(fileName));
    }

    private CompleteTestResults loadTestResults(TechRecordPOST techRecord, String fileName) {
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
        while (SqlGenerator.vehicleIsPresentInDatabase(vin, vehicleRepository).toString().equals("true")) {
            vin = fieldGenerator.randomVin();
        }
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

    static class EvlContainer {
        EVLView evlView;
        TestResult testResult;
        TechRecordPOST techRecord;
    }
}


