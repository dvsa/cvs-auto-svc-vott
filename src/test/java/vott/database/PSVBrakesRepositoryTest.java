package vott.database;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Title;
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
public class PSVBrakesRepositoryTest {

    private List<Integer> deleteOnExit;
    private Integer vehiclePK;
    private Integer makeModelPK;
    private Integer identityPK;
    private Integer contactDetailsPK;
    private Integer vehicleClassPK;
    private Integer technicalRecordPK;
    private Integer technicalRecord2PK;

    private PSVBrakesRepository psvBrakesRepository;
    private TechnicalRecordRepository technicalRecordRepository;
    private VehicleRepository vehicleRepository;
    private MakeModelRepository makeModelRepository;
    private IdentityRepository identityRepository;
    private ContactDetailsRepository contactDetailsRepository;
    private VehicleClassRepository vehicleClassRepository;

    @Before
    public void setUp() {
        ConnectionFactory connectionFactory = new ConnectionFactory(
                VottConfiguration.local()
        );

        psvBrakesRepository = new PSVBrakesRepository(connectionFactory);

        vehicleRepository = new VehicleRepository(connectionFactory);
        vehiclePK = vehicleRepository.fullUpsert(SeedData.newTestVehicle());

        makeModelRepository = new MakeModelRepository(connectionFactory);
        makeModelPK = makeModelRepository.partialUpsert(SeedData.newTestMakeModel());

        identityRepository = new IdentityRepository(connectionFactory);
        identityPK = identityRepository.partialUpsert(SeedData.newTestIdentity());

        contactDetailsRepository = new ContactDetailsRepository(connectionFactory);
        contactDetailsPK = contactDetailsRepository.partialUpsert(SeedData.newTestContactDetails());

        vehicleClassRepository = new VehicleClassRepository(connectionFactory);
        vehicleClassPK = vehicleClassRepository.partialUpsert(SeedData.newTestVehicleClass());

        technicalRecordRepository = new TechnicalRecordRepository(connectionFactory);
        technicalRecordPK = technicalRecordRepository.fullUpsert(SeedData.newTestTechnicalRecord(vehiclePK, makeModelPK, vehicleClassPK, contactDetailsPK, identityPK));

        deleteOnExit = new ArrayList<>();
    }

    @After
    public void tearDown() {
        for (int primaryKey : deleteOnExit) {
            psvBrakesRepository.delete(primaryKey);
        }

        technicalRecordRepository.delete(technicalRecordPK);
        if (technicalRecord2PK != null){
            technicalRecordRepository.delete(technicalRecord2PK);
        }
        vehicleRepository.delete(vehiclePK);
        makeModelRepository.delete(makeModelPK);
        identityRepository.delete(identityPK);
        contactDetailsRepository.delete(contactDetailsPK);
        vehicleClassRepository.delete(vehicleClassPK);
    }

    @Title("VOTT-8 - AC1 - TC35 - Testing psvbrakes unique index compound key")
    @Test
    public void upsertingIdenticalPSVBrakesReturnsSamePk() {
        int primaryKey1 = psvBrakesRepository.fullUpsert(SeedData.newTestPSVBrakes(technicalRecordPK));
        int primaryKey2 = psvBrakesRepository.fullUpsert(SeedData.newTestPSVBrakes(technicalRecordPK));

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertEquals(primaryKey1, primaryKey2);
    }

    @Title("VOTT-8 - AC1 - TC36 - Testing psvbrakes unique index compound key")
    @Test
    public void upsertingDifferentTechRecordIDReturnsDifferentPk() {
        TechnicalRecord tr2 = SeedData.newTestTechnicalRecord(vehiclePK, makeModelPK, vehicleClassPK, contactDetailsPK, identityPK);
        tr2.setCreatedAt("2021-12-31 00:00:00");
        technicalRecord2PK = technicalRecordRepository.fullUpsert(tr2);

        PSVBrakes vs1 = SeedData.newTestPSVBrakes(technicalRecordPK);
        PSVBrakes vs2 = SeedData.newTestPSVBrakes(technicalRecord2PK);

        int primaryKey1 = psvBrakesRepository.fullUpsert(vs1);
        int primaryKey2 = psvBrakesRepository.fullUpsert(vs2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }

    @Title("VOTT-8 - AC1 - TC37 - Testing psvbrakes unique index compound key")
    @Test
    public void upsertingIdenticalIndexValuesReturnsSamePk() {
        PSVBrakes vs1 = SeedData.newTestPSVBrakes(technicalRecordPK);

        PSVBrakes vs2 = SeedData.newTestPSVBrakes(technicalRecordPK);
        vs2.setBrakeCode("Code");

        int primaryKey1 = psvBrakesRepository.fullUpsert(vs1);
        int primaryKey2 = psvBrakesRepository.fullUpsert(vs2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertEquals(primaryKey1, primaryKey2);
    }
}
