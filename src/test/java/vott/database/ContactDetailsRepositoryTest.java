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
import vott.models.dao.ContactDetails;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SerenityRunner.class)
public class ContactDetailsRepositoryTest {

    private List<Integer> deleteOnExit;

    private ContactDetailsRepository contactDetailsRepository;

    @Before
    public void setUp() {
        ConnectionFactory connectionFactory = new ConnectionFactory(
                VottConfiguration.local()
        );

        contactDetailsRepository = new ContactDetailsRepository(connectionFactory);

        deleteOnExit = new ArrayList<>();
    }

    @After
    public void tearDown() {
        for (int primaryKey : deleteOnExit) {
            contactDetailsRepository.delete(primaryKey);
        }
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC10 - Testing contact details unique index compound key")
    @Test
    public void upsertingIdenticalContactDetailsReturnsSamePk() {
        int primaryKey1 = contactDetailsRepository.partialUpsert(SeedData.newTestContactDetails());
        int primaryKey2 = contactDetailsRepository.partialUpsert(SeedData.newTestContactDetails());

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertEquals(primaryKey1, primaryKey2);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC11 - Testing contact details unique index compound key")
    @Test
    public void upsertingNewDataReturnsDifferentPk() {
        ContactDetails cd1 = SeedData.newTestContactDetails();

        ContactDetails cd2 = SeedData.newTestContactDetails();
        cd2.setName("Test Change Name");
        cd2.setAddress1("Test Change Address 1");

        int primaryKey1 = contactDetailsRepository.partialUpsert(cd1);
        int primaryKey2 = contactDetailsRepository.partialUpsert(cd2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }
}
