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

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC47 - Testing tester unique index compound key")
    @Test
    public void upsertingIdenticalTesterReturnsSamePk() {
        int primaryKey1 = testerRepository.partialUpsert(newTestTester());
        int primaryKey2 = testerRepository.partialUpsert(newTestTester());

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertEquals(primaryKey1, primaryKey2);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC48 - Testing tester unique index compound key")
    @Test
    public void upsertingNewDataReturnsDifferentPk() {
        Tester tester1 = newTestTester();

        Tester tester2 = newTestTester();
        tester2.setName("Auto Test 2");

        int primaryKey1 = testerRepository.partialUpsert(tester1);
        int primaryKey2 = testerRepository.partialUpsert(tester2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }

    private Tester newTestTester() {
        Tester tester = new Tester();

        tester.setStaffID("1");
        tester.setName("Auto Test");
        tester.setEmailAddress("auto@test.com");

        return tester;
    }
}
