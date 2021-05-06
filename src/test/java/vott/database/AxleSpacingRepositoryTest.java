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
public class AxleSpacingRepositoryTest {

    private List<Integer> deleteOnExit;
    private Integer vehiclePK;
    private Integer makeModelPK;
    private Integer identityPK;
    private Integer contactDetailsPK;
    private Integer vehicleClassPK;
    private Integer technicalRecordPK;
    private Integer technicalRecord2PK;

    private AxleSpacingRepository axleSpacingRepository;
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

        axleSpacingRepository = new AxleSpacingRepository(connectionFactory);

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
            axleSpacingRepository.delete(primaryKey);
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
    @Title("VOTT-8 - AC1 - TC1 - Testing axleSpacing unique index compound key")
    @Test
    public void upsertingIdenticalAxleSpacingReturnsSamePk() {
        int primaryKey1 = axleSpacingRepository.fullUpsert(SeedData.newTestAxleSpacing(technicalRecordPK));
        int primaryKey2 = axleSpacingRepository.fullUpsert(SeedData.newTestAxleSpacing(technicalRecordPK));

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertEquals(primaryKey1, primaryKey2);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC2 - Testing axleSpacing unique index compound key")
    @Test
    public void upsertingDifferentTechRecordIDReturnsDifferentPk() {
        TechnicalRecord tr2 = SeedData.newTestTechnicalRecord(vehiclePK, makeModelPK, vehicleClassPK, contactDetailsPK, identityPK);
        tr2.setCreatedAt("2021-12-31 00:00:00");
        technicalRecord2PK = technicalRecordRepository.fullUpsert(tr2);

        AxleSpacing as1 = SeedData.newTestAxleSpacing(technicalRecordPK);
        AxleSpacing as2 = SeedData.newTestAxleSpacing(technicalRecord2PK);

        int primaryKey1 = axleSpacingRepository.fullUpsert(as1);
        int primaryKey2 = axleSpacingRepository.fullUpsert(as2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC3 - Testing axleSpacing unique index compound key")
    @Test
    public void upsertingDifferentAxlesReturnsDifferentPk() {
        AxleSpacing as1 = SeedData.newTestAxleSpacing(technicalRecordPK);

        AxleSpacing as2 = SeedData.newTestAxleSpacing(technicalRecordPK);
        as2.setAxles("Test2");

        int primaryKey1 = axleSpacingRepository.fullUpsert(as1);
        int primaryKey2 = axleSpacingRepository.fullUpsert(as2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC4 - Testing axleSpacing unique index compound key")
    @Test
    public void upsertingIdenticalIndexValuesReturnsSamePk() {
        AxleSpacing as1 = SeedData.newTestAxleSpacing(technicalRecordPK);

        AxleSpacing as2 = SeedData.newTestAxleSpacing(technicalRecordPK);
        as2.setValue("50");

        int primaryKey1 = axleSpacingRepository.fullUpsert(as1);
        int primaryKey2 = axleSpacingRepository.fullUpsert(as2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertEquals(primaryKey1, primaryKey2);
    }
}
