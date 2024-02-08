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
public class AxlesRepositoryTest {

    private List<Integer> deleteOnExit;
    private Integer vehiclePK;
    private Integer makeModelPK;
    private Integer identityPK;
    private Integer contactDetailsPK;
    private Integer vehicleClassPK;
    private Integer technicalRecordPK;
    private Integer technicalRecord2PK;
    private Integer tyrePK;
    private Integer tyre2PK;

    private AxlesRepository axlesRepository;
    private TechnicalRecordRepository technicalRecordRepository;
    private VehicleRepository vehicleRepository;
    private MakeModelRepository makeModelRepository;
    private IdentityRepository identityRepository;
    private ContactDetailsRepository contactDetailsRepository;
    private VehicleClassRepository vehicleClassRepository;
    private TyreRepository tyreRepository;

    @Before
    public void setUp() {
        ConnectionFactory connectionFactory = new ConnectionFactory(
                VottConfiguration.local()
        );

        axlesRepository = new AxlesRepository(connectionFactory);

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

        tyreRepository = new TyreRepository(connectionFactory);
        tyrePK = tyreRepository.partialUpsert(SeedData.newTestTyre());

        deleteOnExit = new ArrayList<>();
    }

    @After
    public void tearDown() {
        for (int primaryKey : deleteOnExit) {
            axlesRepository.delete(primaryKey);
        }

        technicalRecordRepository.delete(technicalRecordPK);
        if (technicalRecord2PK != null){
            technicalRecordRepository.delete(technicalRecord2PK);
        }
        tyreRepository.delete(tyrePK);
        if (tyre2PK != null){
            tyreRepository.delete(tyre2PK);
        }
        vehicleRepository.delete(vehiclePK);
        makeModelRepository.delete(makeModelPK);
        identityRepository.delete(identityPK);
        contactDetailsRepository.delete(contactDetailsPK);
        vehicleClassRepository.delete(vehicleClassPK);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC5 - Testing axles unique index compound key")
    @Test
    public void upsertingIdenticalAxleReturnsSamePk() {
        int primaryKey1 = axlesRepository.fullUpsert(SeedData.newTestAxles(technicalRecordPK, tyrePK));
        int primaryKey2 = axlesRepository.fullUpsert(SeedData.newTestAxles(technicalRecordPK, tyrePK));

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertEquals(primaryKey1, primaryKey2);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC6 - Testing axles unique index compound key")
    @Test
    public void upsertingNewTechRecordIDReturnsDifferentPk() {
        TechnicalRecord tr2 = SeedData.newTestTechnicalRecord(vehiclePK, makeModelPK, vehicleClassPK, contactDetailsPK, identityPK);
        tr2.setCreatedAt("2021-12-31 00:00:00");
        technicalRecord2PK = technicalRecordRepository.fullUpsert(tr2);

        Axles axles1 = SeedData.newTestAxles(technicalRecordPK, tyrePK);
        Axles axles2 = SeedData.newTestAxles(technicalRecord2PK, tyrePK);

        int primaryKey1 = axlesRepository.fullUpsert(axles1);
        int primaryKey2 = axlesRepository.fullUpsert(axles2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC7 - Testing axles unique index compound key")
    @Test
    public void upsertingNewTyreIDReturnsDifferentPk() {
        Tyre tyre2 = SeedData.newTestTyre();
        tyre2.setTyreSize("222");
        tyre2PK = tyreRepository.partialUpsert(tyre2);

        Axles axles1 = SeedData.newTestAxles(technicalRecordPK, tyrePK);
        Axles axles2 = SeedData.newTestAxles(technicalRecordPK, tyre2PK);

        int primaryKey1 = axlesRepository.fullUpsert(axles1);
        int primaryKey2 = axlesRepository.fullUpsert(axles2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC8 - Testing axles unique index compound key")
    @Test
    public void upsertingNewAxleNumberReturnsDifferentPk() {
        Axles axles1 = SeedData.newTestAxles(technicalRecordPK, tyrePK);

        Axles axles2 = SeedData.newTestAxles(technicalRecordPK, tyrePK);
        axles2.setAxleNumber("333");

        int primaryKey1 = axlesRepository.fullUpsert(axles1);
        int primaryKey2 = axlesRepository.fullUpsert(axles2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC9 - Testing axles unique index compound key")
    @Test
    public void upsertingIdenticalIndexValuesReturnsSamePk() {
        Axles axles1 = SeedData.newTestAxles(technicalRecordPK, tyrePK);

        Axles axles2 = SeedData.newTestAxles(technicalRecordPK, tyrePK);
        axles2.setParkingBrakeMrk("8");

        int primaryKey1 = axlesRepository.fullUpsert(axles1);
        int primaryKey2 = axlesRepository.fullUpsert(axles2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertEquals(primaryKey1, primaryKey2);
    }
}
