package vott.database;

import vott.database.connection.ConnectionFactory;
import vott.database.sqlgeneration.TableDetails;
import vott.models.dao.AdditionalNotesGuidance;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdditionalNotesGuidanceRepository extends AbstractRepository<AdditionalNotesGuidance> {
    public AdditionalNotesGuidanceRepository(ConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    @Override
    protected TableDetails getTableDetails() {
        TableDetails tableDetails = new TableDetails();
        tableDetails.setTableName("AdrDetails");
        tableDetails.setColumnNames(new String[] {
                "guidanceNotes",
                "adr_details_id"
        });
        return tableDetails;
    }

    @Override
    protected void setParameters(PreparedStatement preparedStatement, AdditionalNotesGuidance entity) throws SQLException {
        //1-indexed
        preparedStatement.setString(1, entity.getGuidanceNotes());
        preparedStatement.setString(2, entity.getAdr_details_id());
    }

    @Override
    protected void setParametersFull(PreparedStatement preparedStatement, AdditionalNotesGuidance entity) throws SQLException {
        setParameters(preparedStatement, entity);

        preparedStatement.setString(3, entity.getGuidanceNotes());
        preparedStatement.setString(4, entity.getAdr_details_id());
    }

    @Override
    protected AdditionalNotesGuidance mapToEntity(ResultSet rs) throws SQLException {
        AdditionalNotesGuidance additionalNotesGuidance = new AdditionalNotesGuidance();

        additionalNotesGuidance.setGuidanceNotes(rs.getString("guidanceNotes"));
        additionalNotesGuidance.setAdr_details_id(rs.getString("adr_details_id"));

        return additionalNotesGuidance;
    }

    @Override
    protected TableDetails getFingerPrintTableDetails() {
        throw new UnsupportedOperationException("Unimplemented method 'getFingerPrintTableDetails'");
    }

    @Override
    protected void setFingerprintParameters(PreparedStatement preparedStatement, AdditionalNotesGuidance entity)
            throws SQLException {
        throw new UnsupportedOperationException("Unimplemented method 'setFingerprintParameters'");
    }
}
