package vott.database;

import vott.database.connection.ConnectionFactory;
import vott.database.sqlgeneration.TableDetails;
import vott.models.dao.AdditionalExaminerNotesNumber;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdditionalExaminerNotesNumberRepository extends AbstractRepository<AdditionalExaminerNotesNumber> {
    public AdditionalExaminerNotesNumberRepository(ConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    @Override
    protected TableDetails getTableDetails() {
        TableDetails tableDetails = new TableDetails();
        tableDetails.setTableName("AdrDetails");
        tableDetails.setColumnNames(new String[] {
                "number",
                "adr_details_id"
        });
        return tableDetails;
    }

    @Override
    protected void setParameters(PreparedStatement preparedStatement, AdditionalExaminerNotesNumber entity) throws SQLException {
        //1-indexed
        preparedStatement.setString(1, entity.getNumber());
        preparedStatement.setString(2, entity.getAdr_details_id());
    }

    @Override
    protected void setParametersFull(PreparedStatement preparedStatement, AdditionalExaminerNotesNumber entity) throws SQLException {
        setParameters(preparedStatement, entity);

        preparedStatement.setString(3, entity.getNumber());
        preparedStatement.setString(4, entity.getAdr_details_id());
    }

    @Override
    protected AdditionalExaminerNotesNumber mapToEntity(ResultSet rs) throws SQLException {
        AdditionalExaminerNotesNumber additionalExaminerNotesNumber = new AdditionalExaminerNotesNumber();

        additionalExaminerNotesNumber.setNumber(rs.getString("number"));
        additionalExaminerNotesNumber.setAdr_details_id(rs.getString("adr_details_id"));

        return additionalExaminerNotesNumber;
    }

    @Override
    protected TableDetails getFingerPrintTableDetails() {    
        throw new UnsupportedOperationException("Unimplemented method 'getFingerPrintTableDetails'");
    }

    @Override
    protected void setFingerprintParameters(PreparedStatement preparedStatement, AdditionalExaminerNotesNumber entity)
            throws SQLException {
        throw new UnsupportedOperationException("Unimplemented method 'setFingerprintParameters'");
    }
}
