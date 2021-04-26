package vott.database;

import net.thucydides.core.annotations.Title;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import vott.config.VottConfiguration;
import vott.database.connection.ConnectionFactory;
import vott.models.dao.Location;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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

    @Title("VOTT-8 - AC1 - TC21 - Testing location unique index compound key")
    @Test
    public void upsertingIdenticalLocationReturnsSamePk() {
        int primaryKey1 = locationRepository.partialUpsert(newTestLocation());
        int primaryKey2 = locationRepository.partialUpsert(newTestLocation());

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertEquals(primaryKey1, primaryKey2);
    }

    @Title("VOTT-8 - AC1 - TC22 - Testing location unique index compound key")
    @Test
    public void upsertingNewDataReturnsDifferentPk() {
        Location location1 = newTestLocation();

        Location location2 = newTestLocation();
        location2.setVertical("Vert");

        int primaryKey1 = locationRepository.partialUpsert(location1);
        int primaryKey2 = locationRepository.partialUpsert(location2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }

    private Location newTestLocation() {
        Location location = new Location();

        location.setVertical("TestV");
        location.setHorizontal("TestH");
        location.setLateral("TestLat");
        location.setLongitudinal("TestL");
        location.setRowNumber("10");
        location.setSeatNumber("20");
        location.setAxleNumber("30");

        return location;
    }
}
