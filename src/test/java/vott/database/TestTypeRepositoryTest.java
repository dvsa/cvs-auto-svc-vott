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
import vott.models.dao.TestType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SerenityRunner.class)
public class TestTypeRepositoryTest {

    private List<Integer> deleteOnExit;

    private TestTypeRepository testTypeRepository;

    @Before
    public void setUp() {
        ConnectionFactory connectionFactory = new ConnectionFactory(
                VottConfiguration.local()
        );

        testTypeRepository = new TestTypeRepository(connectionFactory);

        deleteOnExit = new ArrayList<>();
    }

    @After
    public void tearDown() {
        for (int primaryKey : deleteOnExit) {
            testTypeRepository.delete(primaryKey);
        }
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC56 - Testing test type unique index compound key")
    @Test
    public void upsertingIdenticalTestTypeReturnsSamePk() {
        int primaryKey1 = testTypeRepository.partialUpsert(newTestTestType());
        int primaryKey2 = testTypeRepository.partialUpsert(newTestTestType());

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertEquals(primaryKey1, primaryKey2);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC57 - Testing test type unique index compound key")
    @Test
    public void upsertingNewDataReturnsDifferentPk() {
        TestType tt1 = newTestTestType();

        TestType tt2 = newTestTestType();
        tt2.setTestTypeClassification("Auto Test Type");

        int primaryKey1 = testTypeRepository.partialUpsert(tt1);
        int primaryKey2 = testTypeRepository.partialUpsert(tt2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }

    private TestType newTestTestType() {
        TestType tt = new TestType();

        tt.setTestTypeClassification("Test Test Type");
        tt.setTestTypeName("Test Name");

        return tt;
    }
}
