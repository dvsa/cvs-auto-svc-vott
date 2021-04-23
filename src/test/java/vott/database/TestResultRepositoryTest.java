package vott.database;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import vott.config.VottConfiguration;
import vott.database.connection.ConnectionFactory;
import vott.models.dao.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestResultRepositoryTest {

    private List<Integer> deleteOnExit;
    private Integer vehiclePK;
    private Integer vehicle2PK;
    private Integer fuelEmissionPK;
    private Integer testStationPK;
    private Integer testerPK;
    private Integer vehicleClassPK;
    private Integer testTypePK;
    private Integer testType2PK;
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
    public void setUp() {
        ConnectionFactory connectionFactory = new ConnectionFactory(
                VottConfiguration.local()
        );

        testResultRepository = new TestResultRepository(connectionFactory);

        vehicleRepository = new VehicleRepository(connectionFactory);
        vehiclePK = vehicleRepository.fullUpsert(newTestVehicle());

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
      
        deleteOnExit = new ArrayList<>();
    }

    @After
    public void tearDown() {
        for (int primaryKey : deleteOnExit) {
            testResultRepository.delete(primaryKey);
        }

        vehicleRepository.delete(vehiclePK);
        if (vehicle2PK != null){
            vehicleRepository.delete(vehicle2PK);
        }
        fuelEmissionRepository.delete(fuelEmissionPK);
        testStationRepository.delete(testStationPK);
        testerRepository.delete(testerPK);
        vehicleClassRepository.delete(vehicleClassPK);
        testTypeRepository.delete(testTypePK);
        if (testType2PK != null){
            testTypeRepository.delete(testType2PK);
        }
        preparerRepository.delete(preparerPK);
        identityRepository.delete(identityPK);
    }

    @Test
    public void upsertingIdenticalDataReturnsSamePk() {
        int primaryKey1 = testResultRepository.fullUpsert(newTestTestResult());
        int primaryKey2 = testResultRepository.fullUpsert(newTestTestResult());

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertEquals(primaryKey1, primaryKey2);
    }

    @Test
    public void upsertingNewVehicleIDReturnsDifferentPk() {
        Vehicle vehicle2 = newTestVehicle();
        vehicle2.setVin("Vin Updated");
        vehicle2PK = vehicleRepository.fullUpsert(vehicle2);

        TestResult tr1 = newTestTestResult();

        TestResult tr2 = newTestTestResult();
        tr2.setVehicleID(String.valueOf(vehicle2PK));

        int primaryKey1 = testResultRepository.fullUpsert(tr1);
        int primaryKey2 = testResultRepository.fullUpsert(tr2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }

    @Test
    public void upsertingNewTestTypeIDReturnsDifferentPk() {
        TestType tt2 = newTestTestType();
        tt2.setTestTypeClassification("Auto Test Type");

        testType2PK = testTypeRepository.partialUpsert(tt2);

        TestResult tr1 = newTestTestResult();

        TestResult tr2 = newTestTestResult();
        tr2.setTestTypeID(String.valueOf(testType2PK));

        int primaryKey1 = testResultRepository.fullUpsert(tr1);
        int primaryKey2 = testResultRepository.fullUpsert(tr2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }

    @Test
    public void upsertingNewCreatedAtReturnsDifferentPk() {
        TestResult tr1 = newTestTestResult();

        TestResult tr2 = newTestTestResult();
        tr2.setCreatedAt("2021-12-31 00:00:00");

        int primaryKey1 = testResultRepository.fullUpsert(tr1);
        int primaryKey2 = testResultRepository.fullUpsert(tr2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }

    @Test
    public void upsertingIdenticalIndexValuesReturnsSamePk() {
        TestResult tr1 = newTestTestResult();

        TestResult tr2 = newTestTestResult();
        tr2.setTestNumber("55555");

        int primaryKey1 = testResultRepository.fullUpsert(tr1);
        int primaryKey2 = testResultRepository.fullUpsert(tr2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertEquals(primaryKey1, primaryKey2);
    }

    private Vehicle newTestVehicle() {
        Vehicle vehicle = new Vehicle();

        vehicle.setSystemNumber("SYSTEM-NUMBER");
        vehicle.setVin("Test VIN");
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
