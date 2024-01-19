package vott.database;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.annotations.WithTag;
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

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC12 - Testing custom defect unique index compound key")
    @Test
    public void upsertingIdenticalCustomDefectReturnsDifferentPk() {
        int primaryKey1 = customDefectRepository.fullUpsert(SeedData.newTestCustomDefect(testResultPK));
        int primaryKey2 = customDefectRepository.fullUpsert(SeedData.newTestCustomDefect(testResultPK));

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC13 - Testing custom defect unique index compound key")
    @Test
    public void upsertingNewTestResultIDReturnsDifferentPk() {
        TestResult tr2 = SeedData.newTestTestResult(vehiclePK, fuelEmissionPK, testStationPK, testerPK, preparerPK, vehicleClassPK, testTypePK, identityPK);
        tr2.setCreatedAt("2022-01-01 00:00:00");
        testResult2PK = testResultRepository.fullUpsert(tr2);

        CustomDefect cd1 = SeedData.newTestCustomDefect(testResultPK);
        CustomDefect cd2 = SeedData.newTestCustomDefect(testResult2PK);

        int primaryKey1 = customDefectRepository.fullUpsert(cd1);
        int primaryKey2 = customDefectRepository.fullUpsert(cd2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC14 - Testing custom defect unique index compound key")
    @Test
    public void upsertingIdenticalIndexReturnsSamePk() {
        CustomDefect cd1 = SeedData.newTestCustomDefect(testResultPK);

        CustomDefect cd2 = SeedData.newTestCustomDefect(testResultPK);
        cd2.setReferenceNumber("555555");

        int primaryKey1 = customDefectRepository.fullUpsert(cd1);
        int primaryKey2 = customDefectRepository.fullUpsert(cd2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }
}
