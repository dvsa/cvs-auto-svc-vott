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
import vott.models.dao.Location;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SerenityRunner.class)
public class LocationRepositoryTest {

    private List<Integer> deleteOnExit;

    private LocationRepository locationRepository;

    @Before
    public void setUp() {
        ConnectionFactory connectionFactory = new ConnectionFactory(
                VottConfiguration.local()
        );

        locationRepository = new LocationRepository(connectionFactory);

        deleteOnExit = new ArrayList<>();
    }

    @After
    public void tearDown() {
        for (int primaryKey : deleteOnExit) {
            locationRepository.delete(primaryKey);
        }
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC21 - Testing location unique index compound key")
    @Test
    public void upsertingIdenticalLocationReturnsSamePk() {
        int primaryKey1 = locationRepository.partialUpsert(SeedData.newTestLocation());
        int primaryKey2 = locationRepository.partialUpsert(SeedData.newTestLocation());

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertEquals(primaryKey1, primaryKey2);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC22 - Testing location unique index compound key")
    @Test
    public void upsertingNewDataReturnsDifferentPk() {
        Location location1 = SeedData.newTestLocation();

        Location location2 = SeedData.newTestLocation();
        location2.setVertical("Vert");

        int primaryKey1 = locationRepository.partialUpsert(location1);
        int primaryKey2 = locationRepository.partialUpsert(location2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }
}
