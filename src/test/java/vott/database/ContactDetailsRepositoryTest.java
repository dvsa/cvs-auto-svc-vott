package vott.database;

import net.thucydides.core.annotations.Title;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import vott.config.VottConfiguration;
import vott.database.connection.ConnectionFactory;
import vott.models.dao.ContactDetails;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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

    @Title("VOTT-8 - AC1 - TC10 - Testing contact details unique index compound key")
    @Test
    public void upsertingIdenticalContactDetailsReturnsSamePk() {
        int primaryKey1 = contactDetailsRepository.partialUpsert(newTestContactDetails());
        int primaryKey2 = contactDetailsRepository.partialUpsert(newTestContactDetails());

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertEquals(primaryKey1, primaryKey2);
    }

    @Title("VOTT-8 - AC1 - TC11 - Testing contact details unique index compound key")
    @Test
    public void upsertingNewDataReturnsDifferentPk() {
        ContactDetails cd1 = newTestContactDetails();

        ContactDetails cd2 = newTestContactDetails();
        cd2.setName("Test Change Name");
        cd2.setAddress1("Test Change Address 1");

        int primaryKey1 = contactDetailsRepository.partialUpsert(cd1);
        int primaryKey2 = contactDetailsRepository.partialUpsert(cd2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }

    private ContactDetails newTestContactDetails() {
        ContactDetails cd = new ContactDetails();

        cd.setName("Test Name");
        cd.setAddress1("Test Address 1");
        cd.setAddress2("Test Address 2");
        cd.setPostTown("Test Post Town");
        cd.setAddress3("Test Address 3");
        cd.setEmailAddress("TestEmailAddress");
        cd.setTelephoneNumber("8888888");
        cd.setFaxNumber("99999999");

        return cd;
    }
}
