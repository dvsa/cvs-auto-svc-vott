package vott.database;

import vott.database.connection.ConnectionFactory;
import vott.models.dao.Vehicle;
import vott.database.sqlgeneration.TableDetails;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VehicleRepository extends AbstractRepository<Vehicle> {

    public VehicleRepository(ConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    @Override
    protected TableDetails getTableDetails() {
        TableDetails tableDetails = new TableDetails();

        tableDetails.setTableName("vehicle");
        tableDetails.setColumnNames(new String[] {
            "system_number",
            "vin",
            "vrm_trm",
            "trailer_id"
        });

        return tableDetails;
    }

    @Override
    protected TableDetails getFingerPrintTableDetails() {
        throw new UnsupportedOperationException("Unimplemented method 'getFingerPrintTableDetails'");
    }

    @Override
    protected void setFingerprintParameters(PreparedStatement preparedStatement, Vehicle entity) throws SQLException {
        throw new UnsupportedOperationException("Unimplemented method 'setFingerprintParameters'");
    }

    @Override
    protected void setParameters(PreparedStatement preparedStatement, Vehicle entity) throws SQLException {
        // 1-indexed
        preparedStatement.setString(1, entity.getSystemNumber());
        preparedStatement.setString(2, entity.getVin());
        preparedStatement.setString(3, entity.getVrm_trm());
        preparedStatement.setString(4, entity.getTrailerID());
    }

    @Override
    protected void setParametersFull(PreparedStatement preparedStatement, Vehicle entity) throws SQLException {
        setParameters(preparedStatement, entity);

        preparedStatement.setString(5, entity.getSystemNumber());
        preparedStatement.setString(6, entity.getVin());
        preparedStatement.setString(7, entity.getVrm_trm());
        preparedStatement.setString(8, entity.getTrailerID());
    }

    @Override
    protected Vehicle mapToEntity(ResultSet rs) throws SQLException {
        Vehicle vehicle = new Vehicle();

        vehicle.setSystemNumber(rs.getString("system_number"));
        vehicle.setVin(rs.getString("vin"));
        vehicle.setVrm_trm(rs.getString("vrm_trm"));
        vehicle.setTrailerID(rs.getString("trailer_id"));

        return vehicle;
    }
}
