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
import vott.models.dao.Tyre;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SerenityRunner.class)
public class TyreRepositoryTest {

    private List<Integer> deleteOnExit;

    private TyreRepository tyreRepository;

    @Before
    public void setUp() {
        ConnectionFactory connectionFactory = new ConnectionFactory(
                VottConfiguration.local()
        );

        tyreRepository = new TyreRepository(connectionFactory);

        deleteOnExit = new ArrayList<>();
    }

    @After
    public void tearDown() {
        for (int primaryKey : deleteOnExit) {
            tyreRepository.delete(primaryKey);
        }
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC58 - Testing tyre unique index compound key")
    @Test
    public void upsertingIdenticalTyreReturnsSamePk() {
        int primaryKey1 = tyreRepository.partialUpsert(SeedData.newTestTyre());
        int primaryKey2 = tyreRepository.partialUpsert(SeedData.newTestTyre());

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertEquals(primaryKey1, primaryKey2);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC59 - Testing tyre unique index compound key")
    @Test
    public void upsertingNewDataReturnsDifferentPk() {
        Tyre tyre1 = SeedData.newTestTyre();

        Tyre tyre2 = SeedData.newTestTyre();
        tyre2.setTyreSize("222");

        int primaryKey1 = tyreRepository.partialUpsert(tyre1);
        int primaryKey2 = tyreRepository.partialUpsert(tyre2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }
}
