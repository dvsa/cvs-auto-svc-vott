package vott.database;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Title;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import vott.config.VottConfiguration;
import vott.database.connection.ConnectionFactory;
import vott.models.dao.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotEquals;

@RunWith(SerenityRunner.class)
public class CustomDefectRepositoryTest {

    private List<Integer> deleteOnExit;
    private Integer testResultPK;
    private Integer testResult2PK;
    private Integer vehiclePK;
    private Integer fuelEmissionPK;
    private Integer testStationPK;
    private Integer testerPK;
    private Integer vehicleClassPK;
    private Integer testTypePK;
    private Integer preparerPK;
    private Integer identityPK;

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

    @Before
    public void setUp() {
        ConnectionFactory connectionFactory = new ConnectionFactory(
                VottConfiguration.local()
        );

        customDefectRepository = new CustomDefectRepository(connectionFactory);

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

        testResultRepository = new TestResultRepository(connectionFactory);
        testResultPK = testResultRepository.fullUpsert(newTestTestResult());

        deleteOnExit = new ArrayList<>();
    }

    @After
    public void tearDown() {
        for (int primaryKey : deleteOnExit) {
            customDefectRepository.delete(primaryKey);
        }

        testResultRepository.delete(testResultPK);
        if (testResult2PK != null){
            testResultRepository.delete(testResult2PK);
        }

        vehicleRepository.delete(vehiclePK);
        fuelEmissionRepository.delete(fuelEmissionPK);
        testStationRepository.delete(testStationPK);
        testerRepository.delete(testerPK);
        vehicleClassRepository.delete(vehicleClassPK);
        testTypeRepository.delete(testTypePK);
        preparerRepository.delete(preparerPK);
        identityRepository.delete(identityPK);
    }
    @Title("VOTT-8 - AC1 - TC12 - Testing custom defect unique index compound key")
    @Test
    public void upsertingIdenticalCustomDefectReturnsDifferentPk() {
        int primaryKey1 = customDefectRepository.fullUpsert(newTestCustomDefect());
        int primaryKey2 = customDefectRepository.fullUpsert(newTestCustomDefect());

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }

    @Title("VOTT-8 - AC1 - TC13 - Testing custom defect unique index compound key")
    @Test
    public void upsertingNewTestResultIDReturnsDifferentPk() {
        TestResult tr2 = newTestTestResult();
        tr2.setCreatedAt("2022-01-01 00:00:00");
        testResult2PK = testResultRepository.fullUpsert(tr2);

        CustomDefect cd1 = newTestCustomDefect();

        CustomDefect cd2 = newTestCustomDefect();
        cd2.setTestResultID(String.valueOf(testResult2PK));

        int primaryKey1 = customDefectRepository.fullUpsert(cd1);
        int primaryKey2 = customDefectRepository.fullUpsert(cd2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }

    @Title("VOTT-8 - AC1 - TC14 - Testing custom defect unique index compound key")
    @Test
    public void upsertingIdenticalIndexReturnsSamePk() {
        CustomDefect cd1 = newTestCustomDefect();

        CustomDefect cd2 = newTestCustomDefect();
        cd2.setReferenceNumber("555555");

        int primaryKey1 = customDefectRepository.fullUpsert(cd1);
        int primaryKey2 = customDefectRepository.fullUpsert(cd2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }

    private CustomDefect newTestCustomDefect() {
        CustomDefect cd = new CustomDefect();

        cd.setTestResultID(String.valueOf(testResultPK));
        cd.setReferenceNumber("444444");
        cd.setDefectName("Test Custom Defect");
        cd.setDefectNotes("Test Custom Defect Notes");

        return cd;
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
