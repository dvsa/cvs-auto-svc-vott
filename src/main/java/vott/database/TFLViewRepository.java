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
        tableDetails.setTableName("CVSNOP.tfl_view");
        tableDetails.setColumnNames(new String[] {
                "VRM",
                "VIN",
                "SerialNumberOfCertificate",
                "CertificationModificationType",
                "TestStatus",
                "PMEuropeanEmissionClassificationCode",
                "ValidFromDate",
                "ExpiryDate",
                "IssuedBy",
                "IssueDate",
        });
        return tableDetails;
    }

    @Override
    protected void setParameters(PreparedStatement preparedStatement, TFLView entity) throws SQLException {
        // 1-indexed
        preparedStatement.setString(1, entity.getVrmTrm());
        preparedStatement.setString(2, entity.getVin());
        preparedStatement.setString(3, entity.getCertificateNumber());
        preparedStatement.setString(4,entity.getEmissionClassificationCode());
        preparedStatement.setString(5, entity.getModTypeCode());
        preparedStatement.setString(6, entity.getTestStatus());
        preparedStatement.setString(7, entity.getIssueDate());
        preparedStatement.setString(8, entity.getTestExpiryDate());
        preparedStatement.setString(9, entity.getPNumber());
        preparedStatement.setString(10, entity.getTestTypeStartTimestamp());
    }

    @Override
    protected void setParametersFull(PreparedStatement preparedStatement, TFLView entity) throws SQLException {
        // 1-indexed
        preparedStatement.setString(1, entity.getVrmTrm());
        preparedStatement.setString(2, entity.getVin());
        preparedStatement.setString(3, entity.getCertificateNumber());
        preparedStatement.setString(4,entity.getEmissionClassificationCode());
        preparedStatement.setString(5, entity.getModTypeCode());
        preparedStatement.setString(6, entity.getTestStatus());
        preparedStatement.setString(7, entity.getIssueDate());
        preparedStatement.setString(8, entity.getTestExpiryDate());
        preparedStatement.setString(9, entity.getPNumber());
        preparedStatement.setString(10, entity.getTestTypeStartTimestamp());
    }

    @Override
    protected TFLView mapToEntity(ResultSet rs) throws SQLException {
        TFLView tfl = new TFLView();
        tfl.setVrmTrm(rs.getString("VRM"));
        tfl.setVin(rs.getString("VIN"));
        tfl.setCertificateNumber(rs.getString("SerialNumberOfCertificate"));
        tfl.setEmissionClassificationCode(rs.getString("PMEuropeanEmissionClassificationCode"));
        tfl.setModTypeCode(rs.getString("CertificationModificationType"));
        tfl.setTestStatus(rs.getString("TestStatus"));
        tfl.setIssueDate(rs.getString("ValidFromDate"));
        tfl.setTestExpiryDate(rs.getString("ExpiryDate"));
        tfl.setPNumber(rs.getString("IssuedBy"));
        tfl.setTestTypeStartTimestamp(rs.getString("IssueDate"));
        return tfl;
    }

    }
