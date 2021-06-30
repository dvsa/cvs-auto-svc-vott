package vott.database;

        import vott.database.connection.ConnectionFactory;
        import vott.models.dao.Activity;
        import vott.models.dao.Axles;
        import vott.database.sqlgeneration.TableDetails;

        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;

public class ActivityRepository extends AbstractRepository<Activity> {
    public ActivityRepository(ConnectionFactory connectionFactory) { super(connectionFactory); }

    @Override
    protected TableDetails getTableDetails() {

        TableDetails tableDetails = new TableDetails();

        tableDetails.setTableName("activity");
        tableDetails.setColumnNames(new String[] {
              //  "id",
                "test_station_id",
                "tester_id",
                "activityId",
                "parentId",
                "activityType",
                "startTime",
                "endTime",
                "notes"
        });

        return tableDetails;

    }

    @Override
    protected void setParameters(PreparedStatement preparedStatement, Activity entity) throws SQLException {
        // 1-indexed
      //  preparedStatement.setString(1, entity.getId());
        preparedStatement.setString(1, entity.getTestStationID());
        preparedStatement.setString(2, entity.getTesterID());
        preparedStatement.setString(3, entity.getActivityID());
        preparedStatement.setString(4, entity.getParentID());
        preparedStatement.setString(5, entity.getActivityType());
        preparedStatement.setString(6, entity.getStartTime());
        preparedStatement.setString(7, entity.getEndTime());
        preparedStatement.setString(8, entity.getNotes());
    }

    @Override
    protected void setParametersFull(PreparedStatement preparedStatement, Activity entity) throws SQLException {
        // 1-indexed
        setParameters(preparedStatement, entity);
      //  preparedStatement.setString(1, entity.getId());
        preparedStatement.setString(9, entity.getTestStationID());
        preparedStatement.setString(10, entity.getTesterID());
        preparedStatement.setString(11, entity.getActivityID());
        preparedStatement.setString(12, entity.getParentID());
        preparedStatement.setString(13, entity.getActivityType());
        preparedStatement.setString(14, entity.getStartTime());
        preparedStatement.setString(15, entity.getEndTime());
        preparedStatement.setString(16, entity.getNotes());
    }

    @Override
    protected Activity mapToEntity(ResultSet rs) throws SQLException {
        Activity activity = new Activity();

     //   activity.setId(rs.getString("id"));
        activity.setTestStationID(rs.getString("test_station_id"));
        activity.setTesterID(rs.getString("tester_id"));
        activity.setActivityID(rs.getString("activityId"));
        activity.setParentID(rs.getString("parentId"));
        activity.setActivityType(rs.getString("activityType"));
        activity.setStartTime(rs.getString("startTime"));
        activity.setEndTime(rs.getString("endTime"));
        activity.setNotes(rs.getString("notes"));

        return activity;
    }
}
