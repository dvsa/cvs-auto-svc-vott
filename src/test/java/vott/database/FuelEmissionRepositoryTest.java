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
import vott.models.dao.FuelEmission;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SerenityRunner.class)
public class FuelEmissionRepositoryTest {

    private List<Integer> deleteOnExit;

    private FuelEmissionRepository fuelEmissionRepository;

    @Before
    public void setUp() {
        ConnectionFactory connectionFactory = new ConnectionFactory(
                VottConfiguration.local()
        );

        fuelEmissionRepository = new FuelEmissionRepository(connectionFactory);

        deleteOnExit = new ArrayList<>();
    }

    @After
    public void tearDown() {
        for (int primaryKey : deleteOnExit) {
            fuelEmissionRepository.delete(primaryKey);
        }
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC17 - Testing fuel emission unique index compound key")
    @Test
    public void upsertingIdenticalFuelEmissionReturnsSamePk() {
        int primaryKey1 = fuelEmissionRepository.partialUpsert(SeedData.newTestFuelEmission());
        int primaryKey2 = fuelEmissionRepository.partialUpsert(SeedData.newTestFuelEmission());

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertEquals(primaryKey1, primaryKey2);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC18 - Testing fuel emission unique index compound key")
    @Test
    public void upsertingNewDataReturnsDifferentPk() {
        FuelEmission fe1 = SeedData.newTestFuelEmission();

        FuelEmission fe2 = SeedData.newTestFuelEmission();
        fe2.setEmissionStandard("Another Standard");

        int primaryKey1 = fuelEmissionRepository.partialUpsert(fe1);
        int primaryKey2 = fuelEmissionRepository.partialUpsert(fe2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }
}
