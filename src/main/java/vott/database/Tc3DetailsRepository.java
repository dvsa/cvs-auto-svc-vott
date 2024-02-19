package vott.database;

import vott.database.connection.ConnectionFactory;
import vott.database.sqlgeneration.TableDetails;
import vott.models.dao.Tc3Details;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Tc3DetailsRepository extends AbstractRepository<Tc3Details> {
    public Tc3DetailsRepository(ConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    @Override
    protected TableDetails getTableDetails() {
        TableDetails tableDetails = new TableDetails();
        tableDetails.setTableName("AdrDetails");
        tableDetails.setColumnNames(new String[] {
                "adr_details_id",
                "tc3Type",
                "tc3PeriodicNumber",
                "tc3PeriodicExpiryDate"
        });
        return tableDetails;
    }

    @Override
    protected void setParameters(PreparedStatement preparedStatement, Tc3Details entity) throws SQLException {
        //1-indexed
        preparedStatement.setString(1, entity.getAdr_details_id());
        preparedStatement.setString(2, entity.getTc3Type());
        preparedStatement.setString(3, entity.getTc3PeriodicNumber());
        preparedStatement.setString(4, entity.getTc3PeriodicExpiryDate());
    }

    @Override
    protected void setParametersFull(PreparedStatement preparedStatement, Tc3Details entity) throws SQLException {
        setParameters(preparedStatement, entity);

        preparedStatement.setString(5, entity.getAdr_details_id());
        preparedStatement.setString(6, entity.getTc3Type());
        preparedStatement.setString(7, entity.getTc3PeriodicNumber());
        preparedStatement.setString(8, entity.getTc3PeriodicExpiryDate());
    }

    @Override
    protected Tc3Details mapToEntity(ResultSet rs) throws SQLException {
        Tc3Details tc3Details = new Tc3Details();

        tc3Details.setAdr_details_id(rs.getString("adr_details_id"));
        tc3Details.setTc3Type(rs.getString("tc3Type"));
        tc3Details.setTc3PeriodicNumber(rs.getString("tc3PeriodicNumber"));
        tc3Details.setTc3PeriodicExpiryDate(rs.getString("tc3PeriodicExpiryDate"));

        return tc3Details;
    }

    @Override
    protected TableDetails getFingerPrintTableDetails() {
        throw new UnsupportedOperationException("Unimplemented method 'getFingerPrintTableDetails'");
    }

    @Override
    protected void setFingerprintParameters(PreparedStatement preparedStatement, Tc3Details entity)
            throws SQLException {
        throw new UnsupportedOperationException("Unimplemented method 'setFingerprintParameters'");
    }
}
