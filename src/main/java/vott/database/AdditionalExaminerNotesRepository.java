package vott.database;

import vott.database.connection.ConnectionFactory;
import vott.database.sqlgeneration.TableDetails;
import vott.models.dao.AdditionalExaminerNotes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdditionalExaminerNotesRepository extends AbstractRepository<AdditionalExaminerNotes> {
    public AdditionalExaminerNotesRepository(ConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    @Override
    protected TableDetails getTableDetails() {
        TableDetails tableDetails = new TableDetails();
        tableDetails.setTableName("AdrDetails");
        tableDetails.setColumnNames(new String[] {
                "adr_details_id",
                "note",
                "createdAtDate",
                "lastUpdatedBy"
        });
        return tableDetails;
    }

    @Override
    protected void setParameters(PreparedStatement preparedStatement, AdditionalExaminerNotes entity) throws SQLException {
        // 1-indexed
        preparedStatement.setString(1, entity.getAdr_details_id());
        preparedStatement.setString(2, entity.getNote());
        preparedStatement.setString(3, entity.getCreatedAtDate());
        preparedStatement.setString(4, entity.getLastUpdatedBy());
    }

    @Override
    protected void setParametersFull(PreparedStatement preparedStatement, AdditionalExaminerNotes entity) throws SQLException {
        setParameters(preparedStatement, entity);

        preparedStatement.setString(5, entity.getAdr_details_id());
        preparedStatement.setString(6, entity.getNote());
        preparedStatement.setString(7, entity.getCreatedAtDate());
        preparedStatement.setString(8, entity.getLastUpdatedBy());
    }

    @Override
    protected AdditionalExaminerNotes mapToEntity(ResultSet rs) throws SQLException {
        AdditionalExaminerNotes additionalExaminerNotes = new AdditionalExaminerNotes();

        additionalExaminerNotes.setAdr_details_id(rs.getString("adr_details_id"));
        additionalExaminerNotes.setNote(rs.getString("note"));
        additionalExaminerNotes.setCreatedAtDate(rs.getString("createdAtDate"));
        additionalExaminerNotes.setLastUpdatedBy(rs.getString("lastUpdatedBy"));

        return additionalExaminerNotes;
    }

    @Override
    protected TableDetails getFingerPrintTableDetails() {
        throw new UnsupportedOperationException("Unimplemented method 'getFingerPrintTableDetails'");
    }

    @Override
    protected void setFingerprintParameters(PreparedStatement preparedStatement, AdditionalExaminerNotes entity)
            throws SQLException {
        throw new UnsupportedOperationException("Unimplemented method 'setFingerprintParameters'");
    }
}
