package vott.database;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Title;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import vott.config.VottConfiguration;
import vott.database.connection.ConnectionFactory;
import vott.models.dao.VehicleClass;

import vott.models.dao.VehicleSubclass;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SerenityRunner.class)
public class VehicleSubclassRepositoryTest {

    private List<Integer> deleteOnExit;
    private Integer vehicleClassPK;

    private VehicleSubclassRepository vehicleSubclassRepository;
    private VehicleClassRepository vehicleClassRepository;

    @Before
    public void setUp() {
        ConnectionFactory connectionFactory = new ConnectionFactory(
                VottConfiguration.local()
        );

        vehicleSubclassRepository = new VehicleSubclassRepository(connectionFactory);

        vehicleClassRepository = new VehicleClassRepository(connectionFactory);
        vehicleClassPK = vehicleClassRepository.partialUpsert(newTestVehicleClass());

        deleteOnExit = new ArrayList<>();
    }

    @After
    public void tearDown() {
        for (int primaryKey : deleteOnExit) {
            vehicleSubclassRepository.delete(primaryKey);
        }

        vehicleClassRepository.delete(vehicleClassPK);
    }

    @Title("VOTT-8 - AC1 - TC66 - Testing vehicle subclass index compound key")
    @Test
    public void upsertingIdenticalVehicleSubclassReturnsSamePk() {
        int primaryKey1 = vehicleSubclassRepository.partialUpsert(newTestVehicleSubclass());
        int primaryKey2 = vehicleSubclassRepository.partialUpsert(newTestVehicleSubclass());

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertEquals(primaryKey1, primaryKey2);
    }

    @Title("VOTT-8 - AC1 - TC67 - Testing vehicle subclass index compound key")
    @Test
    public void upsertingNewDataReturnsDifferentPk() {
        VehicleSubclass vs1 = newTestVehicleSubclass();

        VehicleSubclass vs2 = newTestVehicleSubclass();
        vs2.setSubclass("y");

        int primaryKey1 = vehicleSubclassRepository.partialUpsert(vs1);
        int primaryKey2 = vehicleSubclassRepository.partialUpsert(vs2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }

    private VehicleSubclass newTestVehicleSubclass() {
        VehicleSubclass vs = new VehicleSubclass();

        vs.setVehicleClassID(String.valueOf(vehicleClassPK));
        vs.setSubclass("z");

        return vs;
    }

    private VehicleClass newTestVehicleClass() {
        VehicleClass vc = new VehicleClass();

        vc.setCode("1");
        vc.setDescription("Test Description");
        vc.setVehicleType("Test Type");
        vc.setVehicleSize("55555");
        vc.setVehicleConfiguration("Test Configuration");
        vc.setEuVehicleCategory("ABC");

        return vc;
    }
}
