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
import vott.database.seeddata.SeedData;
import vott.models.dao.Preparer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SerenityRunner.class)
public class PreparerRepositoryTest {

    private List<Integer> deleteOnExit;

    private PreparerRepository preparerRepository;

    @Before
    public void setUp() {
        ConnectionFactory connectionFactory = new ConnectionFactory(
                VottConfiguration.local()
        );

        preparerRepository = new PreparerRepository(connectionFactory);

        deleteOnExit = new ArrayList<>();
    }

    @After
    public void tearDown() {
        for (int primaryKey : deleteOnExit) {
            preparerRepository.delete(primaryKey);
        }
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC33 - Testing preparer unique index compound key")
    @Test
    public void upsertingIdenticalPreparerReturnsSamePk() {
        int primaryKey1 = preparerRepository.partialUpsert(SeedData.newTestPreparer());
        int primaryKey2 = preparerRepository.partialUpsert(SeedData.newTestPreparer());

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertEquals(primaryKey1, primaryKey2);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC34 - Testing preparer unique index compound key")
    @Test
    public void upsertingNewDataReturnsDifferentPk() {
        Preparer preparer1 = SeedData.newTestPreparer();

        Preparer preparer2 = SeedData.newTestPreparer();
        preparer2.setName("Auto Test Name");

        int primaryKey1 = preparerRepository.partialUpsert(preparer1);
        int primaryKey2 = preparerRepository.partialUpsert(preparer2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }
}
