package vott.database;

        import net.serenitybdd.junit.runners.SerenityRunner;
        import net.thucydides.core.annotations.Title;
        import net.thucydides.core.annotations.WithTag;
        import org.joda.time.DateTime;
        import org.junit.After;
        import org.junit.Before;
        import org.junit.Test;
        import org.junit.runner.RunWith;
        import vott.config.VottConfiguration;
        import vott.database.connection.ConnectionFactory;
        import vott.database.seeddata.SeedData;
        import vott.models.dao.*;

        import java.text.SimpleDateFormat;
        import java.time.LocalDate;
        import java.time.format.DateTimeFormatter;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.List;
        import java.util.Random;

        import static org.junit.Assert.*;

@RunWith(SerenityRunner.class)
public class ActivityRepositoryTest {

    private List<Integer> deleteOnExit;
    private String visitPK;
    private ActivityRepository activityRepository;
    private VehicleRepository vehicleRepository;
    private TesterRepository testerRepository;
    private TestStationRepository testStationRepository;
    private Integer testerId;
    private Integer testStationId;

    @Before
    public void setUp() {
        ConnectionFactory connectionFactory = new ConnectionFactory(
                VottConfiguration.local()
        );

        activityRepository = new ActivityRepository(connectionFactory);
        testerRepository = new TesterRepository(connectionFactory);
        testStationRepository = new TestStationRepository(connectionFactory);

        Random rand = new Random();

        String staffId =  String.valueOf(rand.nextInt(99999));

        testerId = testerRepository.fullUpsert(SeedData.newTestTester(staffId));
        testStationId = testStationRepository.fullUpsert(SeedData.newTestTestStation("P"+staffId));

        deleteOnExit = new ArrayList<>();
    }

    @After
    public void tearDown() {
        for (int primaryKey : deleteOnExit) {
            activityRepository.delete(primaryKey);
        }
        testerRepository.delete(testerId);
        testStationRepository.delete(testStationId);

    }

    @WithTag("Vott")
    @Title("CVSB-19421 - AC1 - TC1 - Open Site visit")
    @Test
    public void upsertingOpenSiteVisit() {

        vott.models.dao.Activity activity = SeedData.newVisitActivity(testerId.toString(),null, testStationId.toString(),false);
        int primaryKey = activityRepository.fullUpsert(activity);
        deleteOnExit.add(primaryKey);

       Activity activityDB = activityRepository.select(primaryKey);

       assertNull(activityDB.getEndTime());
       assertEquals(activityDB.getActivityID(), activity.getActivityID());
       assertEquals(activityDB.getActivityType(), activity.getActivityType());
       assertEquals(activityDB.getNotes(), activity.getNotes());
       assertEquals(activityDB.getTesterID(), activity.getTesterID());
       assertEquals(activityDB.getStartTime(), activity.getStartTime().substring(0, 19));
       assertEquals(activityDB.getTestStationID(), activity.getTestStationID());
       assertEquals(activityDB.getParentID(), activity.getParentID());
    }

    @WithTag("Vott")
    @Title("CVSB-19421 - AC1 - TC2 - Start wait time")
    @Test
    public void upsertingStartWaitTime() {
        vott.models.dao.Activity activity = SeedData.newWaitActivity(testerId.toString(),null, testStationId.toString(),false);
        int primaryKey = activityRepository.fullUpsert(activity);
        deleteOnExit.add(primaryKey);

        Activity activityDB = activityRepository.select(primaryKey);

        assertNull(activityDB.getEndTime());
        assertEquals(activityDB.getActivityID(), activity.getActivityID());
        assertEquals(activityDB.getActivityType(), activity.getActivityType());
        assertEquals(activityDB.getNotes(), activity.getNotes());
        assertEquals(activityDB.getTesterID(), activity.getTesterID());
        assertEquals(activityDB.getStartTime(), activity.getStartTime().substring(0, 19));
        assertEquals(activityDB.getTestStationID(), activity.getTestStationID());
        assertEquals(activityDB.getParentID(), activity.getParentID());
    }

    @WithTag("Vott")
    @Title("CVSB-19421 - AC1 - TC3 - Start unaccountable time")
    @Test
    public void upsertingStartUnaccountableTime() {
        vott.models.dao.Activity activity = SeedData.newWaitActivity(testerId.toString(),null, testStationId.toString(),false);
        int primaryKey = activityRepository.fullUpsert(activity);
        deleteOnExit.add(primaryKey);

        Activity activityDB = activityRepository.select(primaryKey);

        assertNull(activityDB.getEndTime());
        assertEquals(activityDB.getActivityID(), activity.getActivityID());
        assertEquals(activityDB.getActivityType(), activity.getActivityType());
        assertEquals(activityDB.getNotes(), activity.getNotes());
        assertEquals(activityDB.getTesterID(), activity.getTesterID());
        assertEquals(activityDB.getStartTime(), activity.getStartTime().substring(0, 19));
        assertEquals(activityDB.getTestStationID(), activity.getTestStationID());
        assertEquals(activityDB.getParentID(), activity.getParentID());
    }

    @WithTag("Vott")
    @Title("CVSB-19421 - AC2 - TC1 - Close site visit")
    @Test
    public void upsertingCloseSiteVisit() {
      //  insert original visit
        vott.models.dao.Activity activity = SeedData.newVisitActivity(testerId.toString(),null, testStationId.toString(),false);
        int primaryKey = activityRepository.fullUpsert(activity);
        deleteOnExit.add(primaryKey);

        //  Add end time to the visit
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String endDateTime = formatter.format(date);
        activity.setEndTime(endDateTime);
        int primaryKey2 = activityRepository.fullUpsert(activity);
        Activity activityDB = activityRepository.select(primaryKey2);

        // Ensure same primary key and Visit is ended
        assertEquals(primaryKey, primaryKey2);
        assertNotNull(activityDB.getEndTime());
        assertEquals(activityDB.getEndTime(), endDateTime);

    }

    @WithTag("Vott")
    @Title("CVSB-19421 - AC2 - TC2 - Close wait time")
    @Test
    public void upsertingEndWaitTime() {
        //  insert start of a wait
        vott.models.dao.Activity activity = SeedData.newWaitActivity(testerId.toString(),null, testStationId.toString(),false);
        int primaryKey = activityRepository.fullUpsert(activity);
        deleteOnExit.add(primaryKey);

        //  update wait time
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String endDateTime = formatter.format(date);
        activity.setEndTime(endDateTime);
        int primaryKey2 = activityRepository.fullUpsert(activity);
        Activity activityDB = activityRepository.select(primaryKey2);

        // ensure same primary key and wait time updated
        assertEquals(primaryKey, primaryKey2);
        assertNotNull(activityDB.getEndTime());
        assertEquals(activityDB.getEndTime(), endDateTime);
    }

    @WithTag("Vott")
    @Title("CVSB-19421 - AC2 - TC3 - Close unaccountable time")
    @Test
    public void upsertingEndUnaccoutableTime() {
        //  insert unaccountable time
        vott.models.dao.Activity activity = SeedData.newUnaccountableActivity(testerId.toString(),null, testStationId.toString(), false);
        int primaryKey = activityRepository.fullUpsert(activity);
        deleteOnExit.add(primaryKey);

        //  update unaccountable time
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String endDateTime = formatter.format(date);
        activity.setEndTime(endDateTime);
        int primaryKey2 = activityRepository.fullUpsert(activity);
        Activity activityDB = activityRepository.select(primaryKey2);

        // ensure same primary key and unaccountable time updated
        assertEquals(primaryKey, primaryKey2);
        assertNotNull(activityDB.getEndTime());
        assertEquals(activityDB.getEndTime(), endDateTime);

    }
}
