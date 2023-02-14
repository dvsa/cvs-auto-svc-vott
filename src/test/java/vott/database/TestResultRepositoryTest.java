package vott.database;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Title;
import net.thucydides.core.annotations.WithTag;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import vott.config.VottConfiguration;
import vott.database.connection.ConnectionFactory;
import vott.database.seeddata.SeedData;
import vott.models.dao.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SerenityRunner.class)
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
        vehiclePK = vehicleRepository.fullUpsert(SeedData.newTestVehicle());

        fuelEmissionRepository = new FuelEmissionRepository(connectionFactory);
        fuelEmissionPK = fuelEmissionRepository.partialUpsert(SeedData.newTestFuelEmission());

        testStationRepository = new TestStationRepository(connectionFactory);
        testStationPK = testStationRepository.partialUpsert(SeedData.newTestTestStation());

        testerRepository = new TesterRepository(connectionFactory);
        testerPK = testerRepository.partialUpsert(SeedData.newTestTester());

        vehicleClassRepository = new VehicleClassRepository(connectionFactory);
        vehicleClassPK = vehicleClassRepository.partialUpsert(SeedData.newTestVehicleClass());

        testTypeRepository = new TestTypeRepository(connectionFactory);
        testTypePK = testTypeRepository.partialUpsert(SeedData.newTestTestType());

        preparerRepository = new PreparerRepository(connectionFactory);
        preparerPK = preparerRepository.partialUpsert(SeedData.newTestPreparer());

        identityRepository = new IdentityRepository(connectionFactory);
        identityPK = identityRepository.partialUpsert(SeedData.newTestIdentity());
      
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

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC49 - Testing test result unique index compound key")
    @Test
    public void upsertingIdenticalTestResultReturnsSamePk() {
        int primaryKey1 = testResultRepository.fullUpsert(SeedData.newTestTestResult(vehiclePK,fuelEmissionPK, testStationPK, testerPK,preparerPK, vehicleClassPK, testTypePK, identityPK));
        int primaryKey2 = testResultRepository.fullUpsert(SeedData.newTestTestResult(vehiclePK,fuelEmissionPK, testStationPK, testerPK,preparerPK, vehicleClassPK, testTypePK, identityPK));

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertEquals(primaryKey1, primaryKey2);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC50 - Testing test result unique index compound key")
    @Test
    public void upsertingNewVehicleIDReturnsDifferentPk() {
        Vehicle vehicle2 = SeedData.newTestVehicle();
        vehicle2.setVin("Vin Updated");
        vehicle2PK = vehicleRepository.fullUpsert(vehicle2);

        TestResult tr1 = SeedData.newTestTestResult(vehiclePK,fuelEmissionPK, testStationPK, testerPK,preparerPK, vehicleClassPK, testTypePK, identityPK);
        TestResult tr2 = SeedData.newTestTestResult(vehicle2PK,fuelEmissionPK, testStationPK, testerPK,preparerPK, vehicleClassPK, testTypePK, identityPK);

        int primaryKey1 = testResultRepository.fullUpsert(tr1);
        int primaryKey2 = testResultRepository.fullUpsert(tr2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC51 - Testing test result unique index compound key")
    @Test
    public void upsertingNewTestNumberReturnsDifferentPk() {
        TestResult tr1 = SeedData.newTestTestResult(vehiclePK,fuelEmissionPK, testStationPK, testerPK,preparerPK, vehicleClassPK, testTypePK, identityPK);
        TestResult tr2 = SeedData.newTestTestResult(vehiclePK,fuelEmissionPK, testStationPK, testerPK,preparerPK, vehicleClassPK, testTypePK, identityPK);
        tr2.setTestNumber("B222A111");

        int primaryKey1 = testResultRepository.fullUpsert(tr1);
        int primaryKey2 = testResultRepository.fullUpsert(tr2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC52 - Testing test result unique index compound key")
    @Test
    public void upsertingNewTestResultIdReturnsDifferentPk() {
        TestResult tr1 = SeedData.newTestTestResult(vehiclePK,fuelEmissionPK, testStationPK, testerPK,preparerPK, vehicleClassPK, testTypePK, identityPK);
        TestResult tr2 = SeedData.newTestTestResult(vehiclePK,fuelEmissionPK, testStationPK, testerPK,preparerPK, vehicleClassPK, testTypePK, identityPK);
        tr2.setTestResultId("2222-2222-2222-2222");

        int primaryKey1 = testResultRepository.fullUpsert(tr1);
        int primaryKey2 = testResultRepository.fullUpsert(tr2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC53 - Testing test result unique index compound key")
    @Test
    public void upsertingNewTestTypeEndTimestampReturnsDifferentPk() {
        TestResult tr1 = SeedData.newTestTestResult(vehiclePK,fuelEmissionPK, testStationPK, testerPK,preparerPK, vehicleClassPK, testTypePK, identityPK);
        TestResult tr2 = SeedData.newTestTestResult(vehiclePK,fuelEmissionPK, testStationPK, testerPK,preparerPK, vehicleClassPK, testTypePK, identityPK);
        tr2.setTestTypeEndTimestamp("2022-01-02 00:00:00");

        int primaryKey1 = testResultRepository.fullUpsert(tr1);
        int primaryKey2 = testResultRepository.fullUpsert(tr2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC54 - Testing test result unique index compound key")
    @Test
    public void upsertingIdenticalIndexValuesReturnsSamePk() {
        TestResult tr1 = SeedData.newTestTestResult(vehiclePK,fuelEmissionPK, testStationPK, testerPK,preparerPK, vehicleClassPK, testTypePK, identityPK);
        TestResult tr2 = SeedData.newTestTestResult(vehiclePK,fuelEmissionPK, testStationPK, testerPK,preparerPK, vehicleClassPK, testTypePK, identityPK);
        tr2.setTestResult("Test Fail");

        int primaryKey1 = testResultRepository.fullUpsert(tr1);
        int primaryKey2 = testResultRepository.fullUpsert(tr2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertEquals(primaryKey1, primaryKey2);
    }
}
