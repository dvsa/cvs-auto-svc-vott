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
        tableDetails.setTableName("tfl_view");
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
        preparedStatement.setString(1, entity.getVrm());
        preparedStatement.setString(2, entity.getVin());
        preparedStatement.setString(3, entity.getSerialNumberOfCertificate());
        preparedStatement.setString(4,entity.getEmissionClassificationCode());
        preparedStatement.setString(5, entity.getCertificationModificationType());
        preparedStatement.setString(6, entity.getTestStatus());
        preparedStatement.setString(7, entity.getIssueDate());
        preparedStatement.setString(8, entity.getTestExpiryDate());
        preparedStatement.setString(9, entity.getIssuedBy());
        preparedStatement.setString(10, entity.getTestValidFromDate());
    }

    @Override
    protected void setParametersFull(PreparedStatement preparedStatement, TFLView entity) throws SQLException {
        // 1-indexed
        preparedStatement.setString(1, entity.getVrm());
        preparedStatement.setString(2, entity.getVin());
        preparedStatement.setString(3, entity.getSerialNumberOfCertificate());
        preparedStatement.setString(4,entity.getEmissionClassificationCode());
        preparedStatement.setString(5, entity.getCertificationModificationType());
        preparedStatement.setString(6, entity.getTestStatus());
        preparedStatement.setString(7, entity.getIssueDate());
        preparedStatement.setString(8, entity.getTestExpiryDate());
        preparedStatement.setString(9, entity.getIssuedBy());
        preparedStatement.setString(10, entity.getTestValidFromDate());
    }

    @Override
    protected TFLView mapToEntity(ResultSet rs) throws SQLException {
        TFLView tfl = new TFLView();
        tfl.setVrm(rs.getString("VRM"));
        tfl.setVin(rs.getString("VIN"));
        tfl.setSerialNumberOfCertificate(rs.getString("SerialNumberOfCertificate"));
        tfl.setCertificationModificationType(rs.getString("CertificationModificationType"));
        tfl.setTestStatus(rs.getString("TestStatus"));
        tfl.setEmissionClassificationCode(rs.getString("PMEuropeanEmissionClassificationCode"));
        tfl.setTestValidFromDate(rs.getString("ValidFromDate"));
        tfl.setTestExpiryDate(rs.getString("ExpiryDate"));
        tfl.setIssuedBy(rs.getString("IssuedBy"));
        tfl.setIssueDate(rs.getString("IssueDate"));
        tfl.setIssueDateTime(rs.getString("IssueDateTime"));
        return tfl;
    }

    }
