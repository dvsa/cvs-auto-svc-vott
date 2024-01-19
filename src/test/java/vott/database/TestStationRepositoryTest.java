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
import vott.models.dao.TestStation;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SerenityRunner.class)
public class TestStationRepositoryTest {
    private List<Integer> deleteOnExit;

    private TestStationRepository testStationRepository;

    @Before
    public void setUp() {
        ConnectionFactory connectionFactory = new ConnectionFactory(
                VottConfiguration.local()
        );

        testStationRepository = new TestStationRepository(connectionFactory);

        deleteOnExit = new ArrayList<>();
    }

    @After
    public void tearDown() {
        for (int primaryKey : deleteOnExit) {
            testStationRepository.delete(primaryKey);
        }
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC54 - Testing test station unique index compound key")
    @Test
    public void upsertingIdenticalTestStationReturnsSamePk() {
        int primaryKey1 = testStationRepository.partialUpsert(SeedData.newTestTestStation());
        int primaryKey2 = testStationRepository.partialUpsert(SeedData.newTestTestStation());

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertEquals(primaryKey1, primaryKey2);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC55 - Testing test station unique index compound key")
    @Test
    public void upsertingNewDataReturnsDifferentPk() {
        TestStation ts1 = SeedData.newTestTestStation();

        TestStation ts2 = SeedData.newTestTestStation();
        ts2.setPNumber("123456789");

        int primaryKey1 = testStationRepository.partialUpsert(ts1);
        int primaryKey2 = testStationRepository.partialUpsert(ts2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }
}
