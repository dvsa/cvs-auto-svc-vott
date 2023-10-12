package vott.database;
import vott.database.connection.ConnectionFactory;
import vott.database.sqlgeneration.TableDetails;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import vott.models.dao.AuthIntoServices;
import vott.models.dao.EVLView;


public class AuthIntoServicesRepository extends AbstractRepository<AuthIntoServices> {

        public AuthIntoServicesRepository(ConnectionFactory connectionFactory) { super(connectionFactory); }

        @Override
        protected TableDetails getTableDetails() {
                TableDetails tableDetails = new TableDetails();
                tableDetails.setTableName("auth_into_service");
                tableDetails.setColumnNames(new String[] {
                        "id",
                        "technical_record_id",
                        "cocIssueDate" ,
                        "dateReceived",
                        "datePending",
                        "dateAuthorised",
                        "dateRejected"});
                return tableDetails;
        }

        @Override
        protected void setParameters(PreparedStatement preparedStatement, AuthIntoServices entity) throws SQLException {
                // 1-indexed
                preparedStatement.setString(1, entity.getId());
                preparedStatement.setString(2, entity.getTechnical_record_id());
                preparedStatement.setString(3, entity.getCocIssueDate());
                preparedStatement.setString(4,entity.getDateReceived());
                preparedStatement.setString(5,entity.getDatePending());
                preparedStatement.setString(6,entity.getDateAuthorised());
                preparedStatement.setString(7,entity.getDateRejected());
        }

        @Override
        protected void setParametersFull(PreparedStatement preparedStatement, AuthIntoServices entity) throws SQLException {
                // 1-indexed
                preparedStatement.setString(1, entity.getId());
                preparedStatement.setString(2, entity.getTechnical_record_id());
                preparedStatement.setString(3, entity.getCocIssueDate());
                preparedStatement.setString(4,entity.getDateReceived());
                preparedStatement.setString(5,entity.getDatePending());
                preparedStatement.setString(6,entity.getDateAuthorised());
                preparedStatement.setString(7,entity.getDateRejected());
        }
        @Override
        protected AuthIntoServices mapToEntity(ResultSet rs) throws SQLException {
                AuthIntoServices ais = new AuthIntoServices();
                ais.setId(rs.getString("id"));
                ais.setTechnical_record_id(rs.getString("technical_record_id"));
                ais.setCocIssueDate(rs.getString("cocIssueDate"));
                ais.setDateReceived(rs.getString("dateReceived"));
                ais.setDatePending(rs.getString("datePending"));
                ais.setDateAuthorised(rs.getString("dateAuthorised"));
                ais.setDateRejected(rs.getString("dateRejected"));
                return ais;
        }



}