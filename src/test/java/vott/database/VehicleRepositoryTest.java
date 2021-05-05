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
import vott.models.dao.Vehicle;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SerenityRunner.class)
public class VehicleRepositoryTest {

    private List<Integer> deleteOnExit;

    private VehicleRepository vehicleRepository;

    @Before
    public void setUp() {
        ConnectionFactory connectionFactory = new ConnectionFactory(
            VottConfiguration.local()
        );

        vehicleRepository = new VehicleRepository(connectionFactory);

        deleteOnExit = new ArrayList<>();
    }

    @After
    public void tearDown() {
        for (int primaryKey : deleteOnExit) {
            vehicleRepository.delete(primaryKey);
        }
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC62 - Testing vehicle unique index compound key")
    @Test
    public void upsertingIdenticalVehicleReturnsSamePk() {
        int primaryKey1 = vehicleRepository.fullUpsert(newTestVehicle());
        int primaryKey2 = vehicleRepository.fullUpsert(newTestVehicle());

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertEquals(primaryKey1, primaryKey2);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC63 - Testing vehicle unique index compound key")
    @Test
    public void upsertingDifferentSystemNumberReturnsDifferentPk() {
        Vehicle vehicle1 = newTestVehicle();

        Vehicle vehicle2 = newTestVehicle();
        vehicle2.setSystemNumber("A B C D E F");

        int primaryKey1 = vehicleRepository.fullUpsert(vehicle1);
        int primaryKey2 = vehicleRepository.fullUpsert(vehicle2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC64 - Testing vehicle unique index compound key")
    @Test
    public void upsertingDifferentVINReturnsDifferentPk() {
        Vehicle vehicle1 = newTestVehicle();

        Vehicle vehicle2 = newTestVehicle();
        vehicle2.setVin("Vin Updated");

        int primaryKey1 = vehicleRepository.fullUpsert(vehicle1);
        int primaryKey2 = vehicleRepository.fullUpsert(vehicle2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC65 - Testing vehicle unique index compound key")
    @Test
    public void upsertingIdenticalIndexValuesReturnsSamePk() {
        Vehicle vehicle1 = newTestVehicle();

        Vehicle vehicle2 = newTestVehicle();
        vehicle2.setVrm_trm("7777");
        vehicle2.setTrailerID("7777");

        int primaryKey1 = vehicleRepository.fullUpsert(vehicle1);
        int primaryKey2 = vehicleRepository.fullUpsert(vehicle2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertEquals(primaryKey1, primaryKey2);
    }

    private Vehicle newTestVehicle() {
        Vehicle vehicle = new Vehicle();

        vehicle.setSystemNumber("SYSTEM-NUMBER");
        vehicle.setVin("Test VIN");
        vehicle.setVrm_trm("999999999");
        vehicle.setTrailerID("88888888");

        return vehicle;
    }
}
