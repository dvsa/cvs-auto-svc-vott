package vott.database;

import vott.database.connection.ConnectionFactory;
import vott.database.sqlgeneration.TableDetails;
import vott.models.dao.TFLView;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TFLViewRepository extends AbstractRepository<TFLView> {
    public TFLViewRepository(ConnectionFactory connectionFactory) { super(connectionFactory); }

    @Override
    protected TableDetails getTableDetails() {
        TableDetails tableDetails = new TableDetails();
        tableDetails.setTableName("CVSNOPCVSB5043.tfl_view");
        tableDetails.setColumnNames(new String[] {
                "vrm_trm",
                "vin",
                "certificateNumber",
                "modTypeCode",
                "Name_exp_5",
                "issueDate",
                "testExpiryDate",
                "pNumber",
                "testTypeStartTimestamp",
        });
        return tableDetails;
    }

    @Override
    protected void setParameters(PreparedStatement preparedStatement, TFLView entity) throws SQLException {
        // 1-indexed
        preparedStatement.setString(1, entity.getVrmTrm());
        preparedStatement.setString(2, entity.getVin());
        preparedStatement.setString(3, entity.getCertificateNumber());
        preparedStatement.setString(4, entity.getModTypeCode());
        preparedStatement.setString(5, entity.getName_exp_5());
        preparedStatement.setString(6, entity.getIssueDate());
        preparedStatement.setString(7, entity.getTestExpiryDate());
        preparedStatement.setString(8, entity.getPNumber());
        preparedStatement.setString(9, entity.getTestTypeStartTimestamp());
    }

    @Override
    protected void setParametersFull(PreparedStatement preparedStatement, TFLView entity) throws SQLException {
        // 1-indexed
        preparedStatement.setString(1, entity.getVrmTrm());
        preparedStatement.setString(2, entity.getVin());
        preparedStatement.setString(3, entity.getCertificateNumber());
        preparedStatement.setString(4, entity.getModTypeCode());
        preparedStatement.setString(5, entity.getName_exp_5());
        preparedStatement.setString(6, entity.getIssueDate());
        preparedStatement.setString(7, entity.getTestExpiryDate());
        preparedStatement.setString(8, entity.getPNumber());
        preparedStatement.setString(9, entity.getTestTypeStartTimestamp());
    }

    @Override
    protected TFLView mapToEntity(ResultSet rs) throws SQLException {
        TFLView tfl = new TFLView();
        tfl.setVrmTrm(rs.getString("vrm_trm"));
        tfl.setVin(rs.getString("vin"));
        tfl.setCertificateNumber(rs.getString("certificateNumber"));
        tfl.setModTypeCode(rs.getString("IFNULL(fe.modTypeCode,\"\")"));
        tfl.setName_exp_5(rs.getString("Name_exp_5"));
        tfl.setIssueDate(rs.getString("DATE_FORMAT(tr.testTypeStartTimestamp, '%Y-%m-%d')"));
        tfl.setTestExpiryDate(rs.getString("DATE_FORMAT(tr.testExpiryDate, '%Y-%m-%d')"));
        tfl.setPNumber(rs.getString("pNumber"));
        tfl.setTestTypeStartTimestamp(rs.getString("My_exp_DATE_FORMAT(tr.testTypeStartTimestamp, '%Y-%m-%d')"));
        return tfl;
    }

    }
