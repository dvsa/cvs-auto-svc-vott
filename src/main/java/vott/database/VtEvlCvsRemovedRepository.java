package vott.database;

import vott.database.connection.ConnectionFactory;
import vott.database.sqlgeneration.TableDetails;
import vott.models.dao.VtEvlCvsRemoved;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VtEvlCvsRemovedRepository extends AbstractRepository<VtEvlCvsRemoved> {
    public VtEvlCvsRemovedRepository(ConnectionFactory connectionFactory) { super(connectionFactory); }

    @Override
    protected TableDetails getTableDetails() {
        TableDetails tableDetails = new TableDetails();
        tableDetails.setTableName("vt_evl_02_cvs_removed");
        tableDetails.setColumnNames(new String[] {
                "vrm",
                "vrm_test_record",
                "system_number",
                "vin",
                "certificateNumber",
                "testStartDate",
                "testExpiryDate",
        });
        return tableDetails;
    }

    @Override
    protected TableDetails getFingerPrintTableDetails() {
        throw new UnsupportedOperationException("Unimplemented method 'getFingerPrintTableDetails'");
    }

    @Override
    protected void setFingerprintParameters(PreparedStatement preparedStatement, VtEvlCvsRemoved entity) throws SQLException {
        throw new UnsupportedOperationException("Unimplemented method 'setFingerprintParameters'");
    }

    @Override
    protected void setParameters(PreparedStatement preparedStatement, VtEvlCvsRemoved entity) throws SQLException {
        // 1-indexed
        preparedStatement.setString(1, entity.getVrm());
        preparedStatement.setString(2, entity.getVrmTestRecord());
        preparedStatement.setString(3, entity.getSystemNumber());
        preparedStatement.setString(4, entity.getVin());
        preparedStatement.setString(5, entity.getCertificateNumber());
        preparedStatement.setString(6,entity.getTestStartDate());
        preparedStatement.setString(7,entity.getTestExpiryDate());
    }

    @Override
    protected void setParametersFull(PreparedStatement preparedStatement, VtEvlCvsRemoved entity) throws SQLException {
        // 1-indexed
        preparedStatement.setString(1, entity.getVrm());
        preparedStatement.setString(2, entity.getVrmTestRecord());
        preparedStatement.setString(3, entity.getSystemNumber());
        preparedStatement.setString(4, entity.getVin());
        preparedStatement.setString(5, entity.getCertificateNumber());
        preparedStatement.setString(6,entity.getTestStartDate());
        preparedStatement.setString(7,entity.getTestExpiryDate());
    }

    @Override
    protected VtEvlCvsRemoved mapToEntity(ResultSet rs) throws SQLException {
        VtEvlCvsRemoved vt = new VtEvlCvsRemoved();
        vt.setVrm(rs.getString("vrm"));
        vt.setVrmTestRecord(rs.getString("vrm_test_record"));
        vt.setSystemNumber(rs.getString("system_number"));
        vt.setVin(rs.getString("vin"));
        vt.setCertificateNumber(rs.getString("certificateNumber"));
        vt.setTestExpiryDate(rs.getString("testStartDate"));
        vt.setTestExpiryDate(rs.getString("testExpiryDate"));
        return vt;
    }

}
