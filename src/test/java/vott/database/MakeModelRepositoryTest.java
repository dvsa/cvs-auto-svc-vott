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
import vott.models.dao.MakeModel;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SerenityRunner.class)
public class MakeModelRepositoryTest {
    private List<Integer> deleteOnExit;

    private MakeModelRepository makeModelRepository;

    @Before
    public void setUp() {
        ConnectionFactory connectionFactory = new ConnectionFactory(
                VottConfiguration.local()
        );

        makeModelRepository = new MakeModelRepository(connectionFactory);

        deleteOnExit = new ArrayList<>();
    }

    @After
    public void tearDown() {
        for (int primaryKey : deleteOnExit) {
            makeModelRepository.delete(primaryKey);
        }
    }

    @Title("VOTT-8 - AC1 - TC23 - Testing makemodel unique index compound key")
    @Test
    public void upsertingIdenticalMakeModelReturnsSamePk() {
        int primaryKey1 = makeModelRepository.partialUpsert(SeedData.newTestMakeModel());
        int primaryKey2 = makeModelRepository.partialUpsert(SeedData.newTestMakeModel());

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertEquals(primaryKey1, primaryKey2);
    }

    @Title("VOTT-8 - AC1 - TC24 - Testing makemodel unique index compound key")
    @Test
    public void upsertingNewDataReturnsDifferentPk() {
        MakeModel mm1 = SeedData.newTestMakeModel();

        MakeModel mm2 = SeedData.newTestMakeModel();
        mm2.setMake("Test Make mk2");

        int primaryKey1 = makeModelRepository.partialUpsert(mm1);
        int primaryKey2 = makeModelRepository.partialUpsert(mm2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }
}
