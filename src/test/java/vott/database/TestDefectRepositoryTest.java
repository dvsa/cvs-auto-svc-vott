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
public class TestDefectRepositoryTest {

    private List<Integer> deleteOnExit;
    private Integer testResultPK;
    private Integer testResult2PK;
    private Integer testDefectPK;
    private Integer testDefect2PK;
    private Integer locationPK;
    private Integer location2PK;
    private Integer vehiclePK;
    private Integer fuelEmissionPK;
    private Integer testStationPK;
    private Integer testerPK;
    private Integer vehicleClassPK;
    private Integer testTypePK;
    private Integer preparerPK;
    private Integer identityPK;

    private TestDefectRepository testDefectRepository;
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


    @Before
    public void setUp() {
        ConnectionFactory connectionFactory = new ConnectionFactory(
                VottConfiguration.local()
        );

        testDefectRepository = new TestDefectRepository(connectionFactory);

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

        testResultRepository = new TestResultRepository(connectionFactory);
        testResultPK = testResultRepository.fullUpsert(SeedData.newTestTestResult(vehiclePK, fuelEmissionPK, testStationPK, testerPK, preparerPK, vehicleClassPK, testTypePK, identityPK));

        defectRepository = new DefectRepository(connectionFactory);
        testDefectPK = defectRepository.partialUpsert(SeedData.newTestDefect());

        locationRepository = new LocationRepository(connectionFactory);
        locationPK = locationRepository.partialUpsert(SeedData.newTestLocation());

        deleteOnExit = new ArrayList<>();
    }

    @After
    public void tearDown() {
        for (int primaryKey : deleteOnExit) {
            testDefectRepository.delete(primaryKey);
        }

        testResultRepository.delete(testResultPK);
        if (testResult2PK != null){
            testResultRepository.delete(testResult2PK);
        }

        defectRepository.delete(testDefectPK);
        if (testDefect2PK != null){
            defectRepository.delete(testDefect2PK);
        }

        locationRepository.delete(locationPK);
        if (location2PK != null){
            locationRepository.delete(location2PK);
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

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC42 - Testing test defect unique index compound key")
    @Test
    public void upsertingIdenticalTestDefectReturnsSamePk() {
        int primaryKey1 = testDefectRepository.fullUpsert(SeedData.newTestTestDefect(testResultPK, testDefectPK,locationPK));
        int primaryKey2 = testDefectRepository.fullUpsert(SeedData.newTestTestDefect(testResultPK, testDefectPK,locationPK));

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertEquals(primaryKey1, primaryKey2);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC43 - Testing test defect unique index compound key")
    @Test
    public void upsertingNewTestResultIDReturnsDifferentPk() {
        TestResult tr2 = SeedData.newTestTestResult(vehiclePK, fuelEmissionPK, testStationPK, testerPK, preparerPK, vehicleClassPK, testTypePK, identityPK);
        tr2.setTestResultId("2222-2222-2222-2222");
        testResult2PK = testResultRepository.fullUpsert(tr2);

        TestDefect td1 = SeedData.newTestTestDefect(testResultPK, testDefectPK,locationPK);
        TestDefect td2 = SeedData.newTestTestDefect(testResult2PK, testDefectPK,locationPK);

        int primaryKey1 = testDefectRepository.fullUpsert(td1);
        int primaryKey2 = testDefectRepository.fullUpsert(td2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC44 - Testing test defect unique index compound key")
    @Test
    public void upsertingNewDefectIDReturnsDifferentPk() {
        Defect defect2 = SeedData.newTestDefect();
        defect2.setImNumber("456");
        testDefect2PK = defectRepository.partialUpsert(defect2);

        TestDefect td1 = SeedData.newTestTestDefect(testResultPK, testDefectPK,locationPK);
        TestDefect td2 = SeedData.newTestTestDefect(testResultPK, testDefect2PK,locationPK);

        int primaryKey1 = testDefectRepository.fullUpsert(td1);
        int primaryKey2 = testDefectRepository.fullUpsert(td2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC45 - Testing test defect unique index compound key")
    @Test
    public void upsertingNewLocationIDReturnsDifferentPk() {
        Location location2 = SeedData.newTestLocation();
        location2.setVertical("Vert");
        location2PK = locationRepository.partialUpsert(location2);

        TestDefect td1 = SeedData.newTestTestDefect(testResultPK, testDefectPK,locationPK);
        TestDefect td2 = SeedData.newTestTestDefect(testResultPK, testDefectPK,location2PK);

        int primaryKey1 = testDefectRepository.fullUpsert(td1);
        int primaryKey2 = testDefectRepository.fullUpsert(td2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC46 - Testing test defect unique index compound key")
    @Test
    public void upsertingNewNonIndexDataSamePk() {
        TestDefect td1 = SeedData.newTestTestDefect(testResultPK, testDefectPK,locationPK);

        TestDefect td2 = SeedData.newTestTestDefect(testResultPK, testDefectPK,locationPK);
        td2.setNotes("Test Notes Updated");

        int primaryKey1 = testDefectRepository.fullUpsert(td1);
        int primaryKey2 = testDefectRepository.fullUpsert(td2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertEquals(primaryKey1, primaryKey2);
    }
}
