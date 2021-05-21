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
import vott.models.dao.Identity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SerenityRunner.class)
public class IdentityRepositoryTest {

    private List<Integer> deleteOnExit;

    private IdentityRepository identityRepository;

    @Before
    public void setUp() {
        ConnectionFactory connectionFactory = new ConnectionFactory(
                VottConfiguration.local()
        );

        identityRepository = new IdentityRepository(connectionFactory);

        deleteOnExit = new ArrayList<>();
    }

    @After
    public void tearDown() {
        for (int primaryKey : deleteOnExit) {
            identityRepository.delete(primaryKey);
        }
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC19 - Testing identity unique index compound key")
    @Test
    public void upsertingIdenticalIdentityReturnsSamePk() {
        int primaryKey1 = identityRepository.partialUpsert(SeedData.newTestIdentity());
        int primaryKey2 = identityRepository.partialUpsert(SeedData.newTestIdentity());

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertEquals(primaryKey1, primaryKey2);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC20 - Testing identity unique index compound key")
    @Test
    public void upsertingNewDataReturnsDifferentPk() {
        Identity identity1 = SeedData.newTestIdentity();

        Identity identity2 = SeedData.newTestIdentity();
        identity1.setName("Another Name");

        int primaryKey1 = identityRepository.partialUpsert(identity1);
        int primaryKey2 = identityRepository.partialUpsert(identity2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }
}

