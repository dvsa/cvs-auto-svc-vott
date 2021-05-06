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
import vott.models.dao.Defect;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SerenityRunner.class)
public class DefectRepositoryTest {

    private List<Integer> deleteOnExit;

    private DefectRepository defectRepository;

    @Before
    public void setUp() {
        ConnectionFactory connectionFactory = new ConnectionFactory(
                VottConfiguration.local()
        );

        defectRepository = new DefectRepository(connectionFactory);

        deleteOnExit = new ArrayList<>();
    }

    @After
    public void tearDown() {
        for (int primaryKey : deleteOnExit) {
            defectRepository.delete(primaryKey);
        }
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC15 - Testing defect unique index compound key")
    @Test
    public void upsertingIdenticalDefectReturnsSamePk() {
        int primaryKey1 = defectRepository.partialUpsert(SeedData.newTestDefect());
        int primaryKey2 = defectRepository.partialUpsert(SeedData.newTestDefect());

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertEquals(primaryKey1, primaryKey2);
    }

    @WithTag("Vott")
    @Title("VOTT-8 - AC1 - TC16 - Testing defect unique index compound key")
    @Test
    public void upsertingNewDataReturnsDifferentPk() {
        Defect defect1 = SeedData.newTestDefect();

        Defect defect2 = SeedData.newTestDefect();
        defect2.setImNumber("456");

        int primaryKey1 = defectRepository.partialUpsert(defect1);
        int primaryKey2 = defectRepository.partialUpsert(defect2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }

}
