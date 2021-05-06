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
import vott.models.dao.Tester;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SerenityRunner.class)
public class TesterReporitoryTest {

    private List<Integer> deleteOnExit;

    private TesterRepository testerRepository;

    @Before
    public void setUp() {
        ConnectionFactory connectionFactory = new ConnectionFactory(
                VottConfiguration.local()
        );

        testerRepository = new TesterRepository(connectionFactory);

        deleteOnExit = new ArrayList<>();
    }

    @After
    public void tearDown() {
        for (int primaryKey : deleteOnExit) {
            testerRepository.delete(primaryKey);
        }
    }

    @Title("VOTT-8 - AC1 - TC47 - Testing tester unique index compound key")
    @Test
    public void upsertingIdenticalTesterReturnsSamePk() {
        int primaryKey1 = testerRepository.partialUpsert(SeedData.newTestTester());
        int primaryKey2 = testerRepository.partialUpsert(SeedData.newTestTester());

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertEquals(primaryKey1, primaryKey2);
    }

    @Title("VOTT-8 - AC1 - TC48 - Testing tester unique index compound key")
    @Test
    public void upsertingNewDataReturnsDifferentPk() {
        Tester tester1 = SeedData.newTestTester();

        Tester tester2 = SeedData.newTestTester();
        tester2.setName("Auto Test 2");

        int primaryKey1 = testerRepository.partialUpsert(tester1);
        int primaryKey2 = testerRepository.partialUpsert(tester2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }
}
