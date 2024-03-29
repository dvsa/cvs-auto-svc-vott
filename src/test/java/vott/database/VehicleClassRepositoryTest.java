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
import vott.models.dao.VehicleClass;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SerenityRunner.class)
public class VehicleClassRepositoryTest {
    private List<Integer> deleteOnExit;

    private VehicleClassRepository vehicleClassRepository;

    @Before
    public void setUp() {
        ConnectionFactory connectionFactory = new ConnectionFactory(
                VottConfiguration.local()
        );

        vehicleClassRepository = new VehicleClassRepository(connectionFactory);

        deleteOnExit = new ArrayList<>();
    }

    @After
    public void tearDown() {
        for (int primaryKey : deleteOnExit) {
            vehicleClassRepository.delete(primaryKey);
        }
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC60 - Testing vehicle class unique index compound key")
    @Test
    public void upsertingIdenticalVehicleClassReturnsSamePk() {
        int primaryKey1 = vehicleClassRepository.partialUpsert(SeedData.newTestVehicleClass());
        int primaryKey2 = vehicleClassRepository.partialUpsert(SeedData.newTestVehicleClass());

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertEquals(primaryKey1, primaryKey2);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC61 - Testing vehicle class unique index compound key")
    @Test
    public void upsertingNewDataReturnsDifferentPk() {
        VehicleClass vc1 = SeedData.newTestVehicleClass();

        VehicleClass vc2 = SeedData.newTestVehicleClass();
        vc2.setVehicleSize("88888");

        int primaryKey1 = vehicleClassRepository.partialUpsert(vc1);
        int primaryKey2 = vehicleClassRepository.partialUpsert(vc2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }
}
