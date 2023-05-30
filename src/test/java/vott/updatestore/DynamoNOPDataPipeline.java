package vott.updatestore;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Title;
import org.assertj.core.api.SoftAssertions;
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
    private VtEVLAdditionsRepository vtEVLAdditionsRepository;
    private VtEvlCvsRemovedRepository vtEvlCvsRemovedRepository;
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

        vtEVLAdditionsRepository = new VtEVLAdditionsRepository(connectionFactory);

        vtEvlCvsRemovedRepository = new VtEvlCvsRemovedRepository(connectionFactory);

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

    class EvlContainer{
        EVLView evlView;
        TestResult testResult;
        TechRecordPOST techRecord;
    }

    @Title("CB2-7578 - Values in NOP Schema are truncated for Test Results")
    public EvlContainer evlFeeds(String testResultFileName) throws SQLException,RuntimeException {

        TechRecordPOST techRecord = loadTechRecord(payloadPath+"testresultTruncation_technical-records.json");
        CompleteTestResults testResult = loadTestResults(techRecord,payloadPath+testResultFileName);

        TestTypes ts = testResult.getTestTypes();
        LocalDate ld = LocalDate.now();
        OffsetDateTime datetime = OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC);

        ts.get(0).setTestExpiryDate(ld);
        ts.get(0).setTestTypeStartTimestamp(datetime);
        ts.get(0).setTestTypeEndTimestamp(datetime);
        testResult.setTestTypes(ts);
        testResult.setTestStartTimestamp(datetime);
        testResult.setTestEndTimestamp(datetime);
        testResult.setSystemNumber(techRecord.getSystemNumber());

        System.out.println(testResult.getVin());
        //VehiclesAPI.postVehicleTechnicalRecord(techRecord, v1ImplicitTokens.getBearerToken());
        TestResultAPI.postTestResult(testResult, v1ImplicitTokens.getBearerToken());
        String vin = testResult.getVin();

        //with().timeout(Duration.ofSeconds(30)).await().until(SqlGenerator.vehicleIsPresentInDatabase(vin, vehicleRepository));
        with().timeout(Duration.ofSeconds(30)).await().until(SqlGenerator.testResultIsPresentInDatabase(vin, testResultRepository));

        List<vott.models.dao.TestResult> testResultNOP = getTestResultWithVIN(vin, testResultRepository);
        List<vott.models.dao.EVLView> evlViews = getEVLViewWithCertificateNumberAndVrm(testResultNOP.get(0).getCertificateNumber(),techRecord.getPrimaryVrm(),evlViewRepository);
        //assertThat(evlViews.get(0)).isNotNull();

        EvlContainer container = new EvlContainer();
        container.evlView= evlViews.get(0);
        container.techRecord = techRecord;
        container.testResult= testResultNOP.get(0);

        return container;
    }

    @Title("CB2-7578 - Values in NOP Schema are truncated for Test Results")
    @Test
    public void evlFeedsTest()throws SQLException, RuntimeException{
        /***
         * EVL View is generated correctly
         */
        EvlContainer evl = evlFeeds("EvlView_test-results-pass.json");
    }

    @Title("CB2-8008 - Add VT Data to EVL SQL View ")
    @Test
    public void vt_evl_test_same_as_evl_view() throws SQLException, RuntimeException{
        /***
         * values are updated to et_evl_additions table and expect the EVL_VIEW to relevent results
         */
        EvlContainer evl = evlFeeds("EvlView_test-results-pass.json");
        EVLView evlViews = evl.evlView;

        VtEVLAdditions vt = new VtEVLAdditions();
        vt.setVrmTrmID(evlViews.getVrmTrm());
        vt.setCertificateNumber(evlViews.getCertificateNumber());
        vt.setTestExpiryDate(evlViews.getTestExpiryDate());

        upsertVTEVLADDITIONS(vtEVLAdditionsRepository,vt);

        List<vott.models.dao.EVLView> evloutput = getEVLViewWithCertificateNumberAndVrm(
                evlViews.getCertificateNumber(),evlViews.getVrmTrm(),evlViewRepository);
        assertThat(evloutput.size()).isEqualTo(1);
    }

    @Title("CB2-8008 - Add VT Data to EVL SQL View ")
    @Test
    public void vt_evl_test_certificate_differs() throws SQLException, RuntimeException{
        /***
         * values are updated to et_evl_additions table and expect the EVL_VIEW to relevent results
         */

        EvlContainer evl = evlFeeds("EvlView_test-results-pass.json");
        EVLView evlViews = evl.evlView;

        VtEVLAdditions vt = new VtEVLAdditions();
        vt.setVrmTrmID(evlViews.getVrmTrm());
        vt.setCertificateNumber(evlViews.getCertificateNumber()+"11");
        vt.setTestExpiryDate(evlViews.getTestExpiryDate());

        upsertVTEVLADDITIONS(vtEVLAdditionsRepository,vt);

        List<vott.models.dao.EVLView> evloutput = getEVLViewWithCertificateNumberAndVrm(
                evlViews.getCertificateNumber(),evlViews.getVrmTrm(),evlViewRepository);
        assertThat(evloutput.get(0)).isNotNull();

        evloutput = getEVLViewWithCertificateNumberAndVrm(
                evlViews.getCertificateNumber()+"11",evlViews.getVrmTrm(),evlViewRepository);
        assertThat(evloutput.get(0)).isNotNull();
    }


    @Title("CB2-8008 - Add VT Data to EVL SQL View ")
    @Test
    public void vt_evl_test_vrm_differs() throws SQLException, RuntimeException{
        /***
         * values are updated to et_evl_additions table and expect the EVL_VIEW to relevent results
         */

        EvlContainer evl = evlFeeds("EvlView_test-results-pass.json");
        EVLView evlViews = evl.evlView;

        VtEVLAdditions vt = new VtEVLAdditions();
        vt.setVrmTrmID(evlViews.getVrmTrm()+"1");
        vt.setCertificateNumber(evlViews.getCertificateNumber());
        vt.setTestExpiryDate(evlViews.getTestExpiryDate());

        upsertVTEVLADDITIONS(vtEVLAdditionsRepository,vt);

        List<vott.models.dao.EVLView> evloutput = getEVLViewWithCertificateNumberAndVrm(
                evlViews.getCertificateNumber(),evlViews.getVrmTrm(),evlViewRepository);
        assertThat(evloutput.get(0)).isNotNull();

        evloutput = getEVLViewWithCertificateNumberAndVrm(
                evlViews.getCertificateNumber(),evlViews.getVrmTrm()+"1",evlViewRepository);
        assertThat(evloutput.get(0)).isNotNull();
    }


    @Title("CB2-8008 - Add VT Data to EVL SQL View ")
    @Test
    public void vt_evl_test_expiry_date_differs() throws SQLException, RuntimeException{
        /***
         * values are updated to et_evl_additions table and expect the EVL_VIEW to relevent results
         */
        EvlContainer evl = evlFeeds("EvlView_test-results-pass.json");
        EVLView evlViews = evl.evlView;
        VtEVLAdditions vt = new VtEVLAdditions();
        vt.setVrmTrmID(evlViews.getVrmTrm());
        vt.setCertificateNumber(evlViews.getCertificateNumber());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ld =  LocalDateTime.parse(evlViews.getTestExpiryDate(),df);
        String replace_date= ld.plusDays(1).toString();
        vt.setTestExpiryDate(replace_date.toString());

        upsertVTEVLADDITIONS(vtEVLAdditionsRepository,vt);

        List<vott.models.dao.EVLView> evloutput = getEVLViewWithCertificateNumberAndVrm(
                evlViews.getCertificateNumber(),evlViews.getVrmTrm(),evlViewRepository);
        assertThat(evloutput.size()).isEqualTo(1);
        assertThat(evloutput.get(0).getTestExpiryDate().substring(0,10)).isEqualTo(replace_date.toString().substring(0,10));
    }

    @Title("CB2-8008 - Add VT Data to EVL SQL View ")
    @Test
    public void vt_evl_test_expiry_date_differs_variation1() throws SQLException, RuntimeException{
        /***
         * values are updated to et_evl_additions table and expect the EVL_VIEW to relevent results
         */
        EvlContainer evl = evlFeeds("EvlView_test-results-pass.json");
        EVLView evlViews = evl.evlView;

        VtEVLAdditions vt = new VtEVLAdditions();
        vt.setVrmTrmID(evlViews.getVrmTrm());
        vt.setCertificateNumber(evlViews.getCertificateNumber());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ld =  LocalDateTime.parse(evlViews.getTestExpiryDate(),df);
        String replace_date= ld.minusDays(1).toString();
        vt.setTestExpiryDate(replace_date.toString());

        upsertVTEVLADDITIONS(vtEVLAdditionsRepository,vt);

        List<vott.models.dao.EVLView> evloutput = getEVLViewWithCertificateNumberAndVrm(
                evlViews.getCertificateNumber(),evlViews.getVrmTrm(),evlViewRepository);
        assertThat(evloutput.size()).isEqualTo(1);
        assertThat(evloutput.get(0).getTestExpiryDate().substring(0,10)).isEqualTo(evlViews.getTestExpiryDate().substring(0,10));
    }

    @Title("CB2-8008 - Add VT Data to EVL SQL View ")
    @Test
    public void vt_evl_test_expiry_date_cert_differs() throws SQLException, RuntimeException{
        /***
         * values are updated to et_evl_additions table and expect the EVL_VIEW to relevent results
         */
        EvlContainer evl = evlFeeds("EvlView_test-results-pass.json");
        EVLView evlViews = evl.evlView;
        VtEVLAdditions vt = new VtEVLAdditions();
        vt.setVrmTrmID(evlViews.getVrmTrm());
        vt.setCertificateNumber(evlViews.getCertificateNumber()+"11");
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ld =  LocalDateTime.parse(evlViews.getTestExpiryDate(),df);
        String replace_date= ld.plusDays(1).toString();
        vt.setTestExpiryDate(replace_date.toString());

        upsertVTEVLADDITIONS(vtEVLAdditionsRepository,vt);

        List<vott.models.dao.EVLView> evloutput = getEVLViewWithCertificateNumberAndVrm(
                evlViews.getCertificateNumber()+"11",evlViews.getVrmTrm(),evlViewRepository);
        assertThat(evloutput.size()).isEqualTo(1);
        assertThat(evloutput.get(0).getTestExpiryDate().substring(0,10)).isEqualTo(replace_date.toString().substring(0,10));
        evloutput = getEVLViewWithCertificateNumberAndVrm(
                evlViews.getCertificateNumber(),evlViews.getVrmTrm(),evlViewRepository);
        assertThat(evloutput.size()).isEqualTo(1);
    }

    @Title("CB2-8008 - Add VT Data to EVL SQL View ")
    @Test
    public void evl_vt_pass_nops_fail_vt_recent_date() throws SQLException, RuntimeException{

        /***
         * VT has passed test with recent dates
         * NOPS has failed test with old dates
         * with different certificate numbers and same system number
         * Output
         * EVL View reports the VT test
         */

        EvlContainer evl = evlFeeds("EvlView_test-results-fail.json");
        EVLView evlViews = evl.evlView;
        TechRecordPOST techRecord = evl.techRecord;
        String vin = techRecord.getVin();

        VtEvlCvsRemoved vtr = new VtEvlCvsRemoved();
        vtr.setVin(vin);
        vtr.setVrm(techRecord.getPrimaryVrm());
        vtr.setCertificateNumber(evlViews.getCertificateNumber()+"11");
        vtr.setSystemNumber(techRecord.getSystemNumber());

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ld =  LocalDateTime.parse(evlViews.getTestExpiryDate(),df);
        String replace_date= ld.plusDays(1).toString();
        vtr.setTestStartDate(replace_date);
        replace_date= ld.plusYears(1).toString(); // no significance
        vtr.setTestExpiryDate(replace_date);
        vtr.setVrmTestRecord("test-record");

        upsertVtEvlCvsRemoved(vtEvlCvsRemovedRepository,vtr);

        List<vott.models.dao.VtEvlCvsRemoved> rs = getVTEVLRecordsWithVin(vin,vtEvlCvsRemovedRepository);
        System.out.println(rs);
        assertThat(rs.size()).isEqualTo(1);

        VtEVLAdditions vt = new VtEVLAdditions();
        vt.setVrmTrmID(evlViews.getVrmTrm());
        vt.setCertificateNumber(evlViews.getCertificateNumber()+"11");
        vt.setTestExpiryDate(replace_date);

        upsertVTEVLADDITIONS(vtEVLAdditionsRepository,vt);

        List<vott.models.dao.EVLView> evloutput = getEVLViewWithCertificateNumberAndVrm(
                evlViews.getCertificateNumber(),evlViews.getVrmTrm(),evlViewRepository);

        assertThat(evloutput.size()).isEqualTo(1);

        evloutput = getEVLViewWithCertificateNumberAndVrm(
                evlViews.getCertificateNumber()+"11",evlViews.getVrmTrm(),evlViewRepository);
        assertThat(evloutput.size()).isEqualTo(1);

        evloutput = getEVLViewWithVrm(evlViews.getVrmTrm(),evlViewRepository);
        assertThat(evloutput.size()).isEqualTo(2);
        assertThat(evloutput.get(0).getTestExpiryDate().substring(0,10)).isEqualTo(replace_date.substring(0,10));
        assertThat(evloutput.get(0).getCertificateNumber()).isEqualTo(evlViews.getCertificateNumber()+"11");
    }


    @Title("CB2-8008 - Add VT Data to EVL SQL View ")
    @Test
    public void evl_vt_pass_nops_fail_vt_recent_date_same_cert() throws SQLException, RuntimeException{

        /***
         * VT has passed test with recent dates
         * NOPS has failed test with old dates
         * with same certificate numbers and same system number
         * Output
         * EVL View reports the VT test
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
        LocalDateTime ld =  LocalDateTime.parse(evlViews.getTestExpiryDate(),df);
        String replace_date= ld.plusDays(1).toString();
        vtr.setTestStartDate(replace_date);
        replace_date= ld.plusYears(1).toString(); // no significance
        vtr.setTestExpiryDate(replace_date);
        vtr.setVrmTestRecord("test-record");

        upsertVtEvlCvsRemoved(vtEvlCvsRemovedRepository,vtr);

        List<vott.models.dao.VtEvlCvsRemoved> rs = getVTEVLRecordsWithVin(vin,vtEvlCvsRemovedRepository);
        System.out.println(rs);
        assertThat(rs.size()).isEqualTo(1);

        VtEVLAdditions vt = new VtEVLAdditions();
        vt.setVrmTrmID(evlViews.getVrmTrm());
        vt.setCertificateNumber(evlViews.getCertificateNumber());
        vt.setTestExpiryDate(replace_date);

        upsertVTEVLADDITIONS(vtEVLAdditionsRepository,vt);

        List<vott.models.dao.EVLView> evloutput = getEVLViewWithCertificateNumberAndVrm(
                evlViews.getCertificateNumber(),evlViews.getVrmTrm(),evlViewRepository);

        assertThat(evloutput.size()).isEqualTo(1);
        assertThat(evloutput.get(0).getTestExpiryDate().substring(0,10)).isEqualTo(replace_date.substring(0,10));
        assertThat(evloutput.get(0).getCertificateNumber()).isEqualTo(evlViews.getCertificateNumber());
    }


    @Title("CB2-8008 - Add VT Data to EVL SQL View ")
    @Test
    public void evl_vt_pass_nops_fail_vt_old_date() throws SQLException, RuntimeException{

        /***
         * values are updated to et_evl_additions table and expect the EVL_VIEW to relevent results
         */

        EvlContainer evl = evlFeeds("EvlView_test-results-fail.json");
        EVLView evlViews = evl.evlView;
        TechRecordPOST techRecord = evl.techRecord;
        String vin = techRecord.getVin();


        VtEvlCvsRemoved vtr = new VtEvlCvsRemoved();
        vtr.setVin(vin);
        vtr.setVrm(techRecord.getPrimaryVrm());
        vtr.setCertificateNumber(evlViews.getCertificateNumber()+"11");
        vtr.setSystemNumber(techRecord.getSystemNumber());

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ld =  LocalDateTime.parse(evlViews.getTestExpiryDate(),df);
        String replace_date= ld.minusDays(1).toString();
        vtr.setTestStartDate(replace_date);
        replace_date= ld.minusYears(1).toString(); // no significance
        vtr.setTestExpiryDate(replace_date);
        vtr.setVrmTestRecord("test-record");

        upsertVtEvlCvsRemoved(vtEvlCvsRemovedRepository,vtr);

        List<vott.models.dao.VtEvlCvsRemoved> rs = getVTEVLRecordsWithVin(vin,vtEvlCvsRemovedRepository);
        System.out.println(rs);
        assertThat(rs.size()).isEqualTo(0);

        List<vott.models.dao.EVLView> evloutput = getEVLViewWithCertificateNumberAndVrm(
                evlViews.getCertificateNumber(),evlViews.getVrmTrm(),evlViewRepository);

        assertThat(evloutput.size()).isEqualTo(1);

        evloutput = getEVLViewWithCertificateNumberAndVrm(
                evlViews.getCertificateNumber()+"11",evlViews.getVrmTrm(),evlViewRepository);
        assertThat(evloutput.size()).isEqualTo(0);

    }


    @Title("CB2-8008 - Add VT Data to EVL SQL View ")
    @Test
    public void evl_vt_pass_nops_fail_vt_same_date() throws SQLException, RuntimeException{

        /***
         * values are updated to et_evl_additions table and expect the EVL_VIEW to relevent results
         */

        EvlContainer evl = evlFeeds("EvlView_test-results-fail.json");
        EVLView evlViews = evl.evlView;
        TechRecordPOST techRecord = evl.techRecord;
        String vin = techRecord.getVin();

        VtEvlCvsRemoved vtr = new VtEvlCvsRemoved();
        vtr.setVin(vin);
        vtr.setVrm(techRecord.getPrimaryVrm());
        vtr.setCertificateNumber(evlViews.getCertificateNumber()+"11");
        vtr.setSystemNumber(techRecord.getSystemNumber());
        vtr.setTestStartDate(evlViews.getTestExpiryDate());
        vtr.setTestExpiryDate(evlViews.getTestExpiryDate());
        vtr.setVrmTestRecord("test-record");

        upsertVtEvlCvsRemoved(vtEvlCvsRemovedRepository,vtr);

        List<vott.models.dao.VtEvlCvsRemoved> rs = getVTEVLRecordsWithVin(vin,vtEvlCvsRemovedRepository);
        System.out.println(rs);
        assertThat(rs.size()).isEqualTo(0);

        VtEVLAdditions vt = new VtEVLAdditions();
        vt.setVrmTrmID(evlViews.getVrmTrm());
        vt.setCertificateNumber(evlViews.getCertificateNumber()+"11");
        vt.setTestExpiryDate(evlViews.getTestExpiryDate());

        upsertVTEVLADDITIONS(vtEVLAdditionsRepository,vt);

        List<vott.models.dao.EVLView> evloutput = getEVLViewWithCertificateNumberAndVrm(
                evlViews.getCertificateNumber(),evlViews.getVrmTrm(),evlViewRepository);

        assertThat(evloutput.size()).isEqualTo(1);

        evloutput = getEVLViewWithCertificateNumberAndVrm(
                evlViews.getCertificateNumber()+"11",evlViews.getVrmTrm(),evlViewRepository);
        assertThat(evloutput.size()).isEqualTo(1);

        evloutput = getEVLViewWithVrm(evlViews.getVrmTrm(),evlViewRepository);
        assertThat(evloutput.size()).isEqualTo(2);
        assertThat(evloutput.get(0).getTestExpiryDate().substring(0,10)).isEqualTo(evlViews.getTestExpiryDate().substring(0,10));
        assertThat(evloutput.get(0).getCertificateNumber()).isEqualTo(evlViews.getCertificateNumber());
    }



    @Title("CB2-8008 - Add VT Data to EVL SQL View ")
    @Test
    public void evl_only_vt() throws SQLException, RuntimeException{

        /***
         * values are updated to et_evl_additions table and expect the EVL_VIEW to relevent results
         */

        EvlContainer evl = evlFeeds("EvlView_test-results-fail.json");
        EVLView evlViews = evl.evlView;
        TechRecordPOST techRecord = evl.techRecord;
        String vin = techRecord.getVin();

        VtEvlCvsRemoved vtr = new VtEvlCvsRemoved();
        vtr.setVin(vin);
        vtr.setVrm(techRecord.getPrimaryVrm()+"V");
        vtr.setCertificateNumber(evlViews.getCertificateNumber()+"11");
        vtr.setSystemNumber(techRecord.getSystemNumber()+"1");
        vtr.setTestStartDate(evlViews.getTestExpiryDate());
        vtr.setTestExpiryDate(evlViews.getTestExpiryDate());
        vtr.setVrmTestRecord("test-record");

        upsertVtEvlCvsRemoved(vtEvlCvsRemovedRepository,vtr);

        List<vott.models.dao.VtEvlCvsRemoved> rs = getVTEVLRecordsWithVin(vin,vtEvlCvsRemovedRepository);
        System.out.println(rs);
        assertThat(rs.get(0).getVrm()).isEqualTo(evlViews.getVrmTrm()+"V");

        VtEVLAdditions vt = new VtEVLAdditions();
        vt.setVrmTrmID(evlViews.getVrmTrm()+"V");
        vt.setCertificateNumber(evlViews.getCertificateNumber()+"11");
        vt.setTestExpiryDate(evlViews.getTestExpiryDate());

        upsertVTEVLADDITIONS(vtEVLAdditionsRepository,vt);

        List<vott.models.dao.EVLView> evloutput = getEVLViewWithCertificateNumberAndVrm(
                evlViews.getCertificateNumber(),evlViews.getVrmTrm(),evlViewRepository);

        assertThat(evloutput.size()).isEqualTo(1);

        evloutput = getEVLViewWithCertificateNumberAndVrm(
                evlViews.getCertificateNumber()+"11",evlViews.getVrmTrm()+"V",evlViewRepository);
        assertThat(evloutput.size()).isEqualTo(1);

    }


    @Title("CB2-7578 - Values in NOP Schema are truncated for Test Results")
    @Test
    public void evlFeedsRekey() {
        /***
         * values are updated to rekey to dynamoDB table and expect the EVL_VIEW to show results once
         */

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
        SoftAssertions softly = new SoftAssertions();
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
            softly.assertThat(nopsTechRecord.get(0).getApprovalType()).isEqualTo(appList.getValue());
            System.out.println("Expected : " + appList.getValue() + "  Actual : " + nopsTechRecord.get(0).getApprovalType());
        }
    }

    @Title("CB2-5043 - TFL View reporting ")
    @Test
    public void tflFeeds() {
        /***
         * TFL View is generated when test type is LEC
         */

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
        assertThat(tflViews.get(0).getTestExpiryDate()).isNotNull();
    }


    @Title("CB2-5043 - TFL View reporting ")
    @Test
    public void tflFeedsLFCertificate() {

        /***
         * TFL View based on emission standards
         */
        TechRecordPOST techRecord ;
        CompleteTestResults testResult;

        Map<TestTypeResults.EmissionStandardEnum, String> es = new HashMap<TestTypeResults.EmissionStandardEnum, String>();
        es.put(_0_03_G_KWH_EURO_IV_PM,"10");  //pass   #impacted records 13k
        es.put(_0_10_G_KWH_EURO_3_PM,"4"); //pass     # impacted records 83
        es.put(FULL_ELECTRIC,"13");  // pass  #impacted records 15
        es.put(Euro_3,"4"); //pass       #impacted records 43
        es.put(EURO_4,"5"); //pass       # impacted records 319
        es.put(EURO_6,"13"); //pass        # impacted records 50
        es.put(EURO_VI,"13"); //pass       # impacted records 73

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
            ts.get(0).setCertificateNumber("LF123456789");

            VehiclesAPI.postVehicleTechnicalRecord(techRecord, v1ImplicitTokens.getBearerToken());
            TestResultAPI.postTestResult(testResult, v1ImplicitTokens.getBearerToken());
            vin = testResult.getVin();

            System.out.println("vin " + vin);
            with().timeout(Duration.ofSeconds(30)).await().until(SqlGenerator.vehicleIsPresentInDatabase(vin, vehicleRepository));
            with().timeout(Duration.ofSeconds(30)).await().until(SqlGenerator.testResultIsPresentInDatabase(vin, testResultRepository));

            tflViews = getTFLViewWithVin(vin,tflViewRepository);
            System.out.println(tflViews.get(0));
            assertThat(tflViews.get(0)).isNotNull();
            assertThat(tflViews.get(0).getEmissionClassificationCode()).isEqualTo(entry.getValue());
            assertThat(tflViews.get(0).getTestStatus()).isEqualTo("2");
            assertThat(tflViews.get(0).getPNumber()).isEqualTo("87-1369561");
            assertThat(tflViews.get(0).getModTypeCode()).isEqualTo("p");
        }
    }

    @Title("CB2-5043 - TFL View reporting Emission standards")
    @Test
    public void tflFeedsEmissionStandards() {
        /***
         * TFL View based on emission standards
         */
        TechRecordPOST techRecord ;
        CompleteTestResults testResult;

        Map<TestTypeResults.EmissionStandardEnum, String> es = new HashMap<TestTypeResults.EmissionStandardEnum, String>();
        es.put(_0_03_G_KWH_EURO_IV_PM,"10");  //pass   #impacted records 13k
        es.put(_0_10_G_KWH_EURO_3_PM,"4"); //pass     # impacted records 83
        es.put(FULL_ELECTRIC,"13");  // pass  #impacted records 15
        es.put(EURO_4,"5"); //pass       # impacted records 319
        es.put(EURO_6,"13"); //pass        # impacted records 50
        es.put(EURO_VI,"13"); //pass       # impacted records 73

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
            assertThat(tflViews.get(0).getTestStatus()).isEqualTo("1");
            assertThat(tflViews.get(0).getEmissionClassificationCode()).isEqualTo(entry.getValue());
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

