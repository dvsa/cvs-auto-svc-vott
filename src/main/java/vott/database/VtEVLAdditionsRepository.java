package vott.database;

import vott.database.connection.ConnectionFactory;
import vott.database.sqlgeneration.TableDetails;
import vott.models.dao.VtEVLAdditions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VtEVLAdditionsRepository extends AbstractRepository<VtEVLAdditions> {

    public VtEVLAdditionsRepository(ConnectionFactory connectionFactory) { super(connectionFactory); }

    @Override
    protected TableDetails getTableDetails() {
        TableDetails tableDetails = new TableDetails();
        tableDetails.setTableName("vt_evl_additions");
        tableDetails.setColumnNames(new String[] {
                "vrm_trm",
                "certificateNumber" ,
                "testExpiryDate",
        });
        return tableDetails;
    }

    @Override
    protected TableDetails getFingerPrintTableDetails() {
        return null;
    }

    @Override
    protected void setFingerprintParameters(PreparedStatement preparedStatement, VtEVLAdditions entity) throws SQLException {

    }

    @Override
    protected void setParameters(PreparedStatement preparedStatement, VtEVLAdditions entity) throws SQLException {
        // 1-indexed
        preparedStatement.setString(1, entity.getVrmTrmID());
        preparedStatement.setString(2, entity.getCertificateNumber());
        preparedStatement.setString(3, entity.getTestExpiryDate());
    }

    @Override
    protected void setParametersFull(PreparedStatement preparedStatement, VtEVLAdditions entity) throws SQLException {
        // 1-indexed
        preparedStatement.setString(1, entity.getVrmTrmID());
        preparedStatement.setString(2, entity.getCertificateNumber());
        preparedStatement.setString(3, entity.getTestExpiryDate());
    }

    @Override
    protected VtEVLAdditions mapToEntity(ResultSet rs) throws SQLException {
        VtEVLAdditions vt = new VtEVLAdditions();
        vt.setVrmTrmID(rs.getString("vrm_trm"));
        vt.setCertificateNumber(rs.getString("certificateNumber"));
        vt.setTestExpiryDate(rs.getString("testExpiryDate"));
        return vt;
    }
}
