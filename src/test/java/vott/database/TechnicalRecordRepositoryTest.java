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
public class TechnicalRecordRepositoryTest {

    private List<Integer> deleteOnExit;
    private Integer vehiclePK;
    private Integer vehicle2PK;
    private Integer makeModelPK;
    private Integer identityPK;
    private Integer contactDetailsPK;
    private Integer vehicleClassPK;

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

        technicalRecordRepository = new TechnicalRecordRepository(connectionFactory);

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

        deleteOnExit = new ArrayList<>();
    }

    @After
    public void tearDown() {
        for (int primaryKey : deleteOnExit) {
            technicalRecordRepository.delete(primaryKey);
        }

        vehicleRepository.delete(vehiclePK);
        if (vehicle2PK != null){
            vehicleRepository.delete(vehicle2PK);
        }
        makeModelRepository.delete(makeModelPK);
        identityRepository.delete(identityPK);
        contactDetailsRepository.delete(contactDetailsPK);
        vehicleClassRepository.delete(vehicleClassPK);
    }

    @Title("VOTT-8 - AC1 - TC38 - Testing technical record unique index compound key")
    @Test
    public void upsertingIdenticalTechnicalRecordReturnsSamePk() {
        int primaryKey1 = technicalRecordRepository.fullUpsert(SeedData.newTestTechnicalRecord(vehiclePK, makeModelPK, vehicleClassPK, contactDetailsPK, identityPK));
        int primaryKey2 = technicalRecordRepository.fullUpsert(SeedData.newTestTechnicalRecord(vehiclePK, makeModelPK, vehicleClassPK, contactDetailsPK, identityPK));

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertEquals(primaryKey1, primaryKey2);
    }

    @Title("VOTT-8 - AC1 - TC39 - Testing technical record unique index compound key")
    @Test
    public void upsertingDifferentVehicleIDValueReturnsDifferentPk() {
        Vehicle vehicle2 = SeedData.newTestVehicle();
        vehicle2.setVin("Auto VIN");
        vehicle2PK = vehicleRepository.fullUpsert(vehicle2);

        TechnicalRecord tr1 = SeedData.newTestTechnicalRecord(vehiclePK, makeModelPK, vehicleClassPK, contactDetailsPK, identityPK);
        TechnicalRecord tr2 = SeedData.newTestTechnicalRecord(vehicle2PK, makeModelPK, vehicleClassPK, contactDetailsPK, identityPK);

        int primaryKey1 = technicalRecordRepository.fullUpsert(tr1);
        int primaryKey2 = technicalRecordRepository.fullUpsert(tr2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }

    @Title("VOTT-8 - AC1 - TC40 - Testing technical record unique index compound key")
    @Test
    public void upsertingDifferentCreatedAtValueReturnsDifferentPk() {
        TechnicalRecord tr1 = SeedData.newTestTechnicalRecord(vehiclePK, makeModelPK, vehicleClassPK, contactDetailsPK, identityPK);

        TechnicalRecord tr2 = SeedData.newTestTechnicalRecord(vehiclePK, makeModelPK, vehicleClassPK, contactDetailsPK, identityPK);
        tr2.setCreatedAt("2021-12-31 00:00:00");

        int primaryKey1 = technicalRecordRepository.fullUpsert(tr1);
        int primaryKey2 = technicalRecordRepository.fullUpsert(tr2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }

    @Title("VOTT-8 - AC1 - TC41 - Testing technical record unique index compound key")
    @Test
    public void upsertingIdenticalIndexValuesReturnsSamePk() {
        TechnicalRecord tr1 = SeedData.newTestTechnicalRecord(vehiclePK, makeModelPK, vehicleClassPK, contactDetailsPK, identityPK);

        TechnicalRecord tr2 = SeedData.newTestTechnicalRecord(vehiclePK, makeModelPK, vehicleClassPK, contactDetailsPK, identityPK);
        tr2.setRecordCompleteness("ABCD");

        int primaryKey1 = technicalRecordRepository.fullUpsert(tr1);
        int primaryKey2 = technicalRecordRepository.fullUpsert(tr2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertEquals(primaryKey1, primaryKey2);
    }
}
