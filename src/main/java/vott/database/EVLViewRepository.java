package vott.database;

import vott.database.connection.ConnectionFactory;
import vott.models.dao.Defect;
import vott.models.dao.EVLView;
import vott.database.sqlgeneration.TableDetails;
import vott.models.dao.TestResult;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EVLViewRepository extends AbstractRepository<EVLView> {
    public EVLViewRepository(ConnectionFactory connectionFactory) { super(connectionFactory); }

    @Override
    protected TableDetails getTableDetails() {
        TableDetails tableDetails = new TableDetails();
        tableDetails.setTableName("evl_View");
        tableDetails.setColumnNames(new String[] {
                "testExpiryDate",
                "vrm_trm",
                "certificateNumber" , });
        return tableDetails;
    }

    @Override
    protected void setParameters(PreparedStatement preparedStatement, EVLView entity) throws SQLException {
        // 1-indexed
        preparedStatement.setString(1, entity.getTestExpiryDate());
        preparedStatement.setString(2, entity.getVrmTrm());
        preparedStatement.setString(3, entity.getCertificateNumber());
    }

    @Override
    protected void setParametersFull(PreparedStatement preparedStatement, EVLView entity) throws SQLException {
        // 1-indexed
        preparedStatement.setString(1, entity.getTestExpiryDate());
        preparedStatement.setString(2, entity.getVrmTrm());
        preparedStatement.setString(3, entity.getCertificateNumber());
    }

    @Override
    protected EVLView mapToEntity(ResultSet rs) throws SQLException {
        EVLView evl = new EVLView();
        evl.setTestExpiryDate(rs.getString("testExpiryDate"));
        evl.setVrmTrm(rs.getString("vrm_trm"));
        evl.setCertificateNumber(rs.getString("certificateNumber"));
        return evl;
    }

    }
