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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SerenityRunner.class)
public class PlateRepositoryTest {
    private List<Integer> deleteOnExit;
    private Integer vehiclePK;
    private Integer makeModelPK;
    private Integer identityPK;
    private Integer contactDetailsPK;
    private Integer vehicleClassPK;
    private Integer technicalRecordPK;
    private Integer technicalRecord2PK;

    private PlateRepository plateRepository;
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

        plateRepository = new PlateRepository(connectionFactory);

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
            plateRepository.delete(primaryKey);
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

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC28 - Testing plate unique index compound key")
    @Test
    public void upsertingIdenticalPlateReturnsSamePk() {
        int primaryKey1 = plateRepository.fullUpsert(SeedData.newTestPlate(technicalRecordPK));
        int primaryKey2 = plateRepository.fullUpsert(SeedData.newTestPlate(technicalRecordPK));

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertEquals(primaryKey1, primaryKey2);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC29 - Testing plate unique index compound key")
    @Test
    public void upsertingNewTechRecordIDReturnsDifferentPk() {
        TechnicalRecord tr2 = SeedData.newTestTechnicalRecord(vehiclePK, makeModelPK, vehicleClassPK, contactDetailsPK, identityPK);
        tr2.setCreatedAt("2021-12-31 00:00:00");
        technicalRecord2PK = technicalRecordRepository.fullUpsert(tr2);

        Plate plate1 = SeedData.newTestPlate(technicalRecordPK);
        Plate plate2 = SeedData.newTestPlate(technicalRecord2PK);

        int primaryKey1 = plateRepository.fullUpsert(plate1);
        int primaryKey2 = plateRepository.fullUpsert(plate2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC30 - Testing plate unique index compound key")
    @Test
    public void upsertingNewPlateSerialNumReturnsDifferentPk() {
        Plate plate1 = SeedData.newTestPlate(technicalRecordPK);

        Plate plate2 = SeedData.newTestPlate(technicalRecordPK);
        plate2.setPlateSerialNumber("777777");

        int primaryKey1 = plateRepository.fullUpsert(plate1);
        int primaryKey2 = plateRepository.fullUpsert(plate2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC31 - Testing plate unique index compound key")
    @Test
    public void upsertingNewPlateIssueDateReturnsDifferentPk() {
        Plate plate1 = SeedData.newTestPlate(technicalRecordPK);

        Plate plate2 = SeedData.newTestPlate(technicalRecordPK);
        plate2.setPlateIssueDate("2100-01-01");

        int primaryKey1 = plateRepository.fullUpsert(plate1);
        int primaryKey2 = plateRepository.fullUpsert(plate2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC32 - Testing plate unique index compound key")
    @Test
    public void upsertingIdenticalIndexValuesReturnsSamePk() {
        Plate plate1 = SeedData.newTestPlate(technicalRecordPK);

        Plate plate2 = SeedData.newTestPlate(technicalRecordPK);
        plate2.setPlateIssuer("Test Update");

        int primaryKey1 = plateRepository.fullUpsert(plate1);
        int primaryKey2 = plateRepository.fullUpsert(plate2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertEquals(primaryKey1, primaryKey2);
    }
}
