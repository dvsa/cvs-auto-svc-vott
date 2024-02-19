package vott.database;

import vott.database.connection.ConnectionFactory;
import vott.database.sqlgeneration.TableDetails;
import vott.models.dao.PassCertificateDetails;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PassCertificateDetailsRepository extends AbstractRepository<PassCertificateDetails> {
    public PassCertificateDetailsRepository(ConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    @Override
    protected TableDetails getTableDetails() {
        TableDetails tableDetails = new TableDetails();
        tableDetails.setTableName("AdrDetails");
        tableDetails.setColumnNames(new String[] {
                "technical_record_id",
                "createdByName",
                "certificateType",
                "generatedTimestamp",
                "certificateId"
        });
        return tableDetails;
    }

    @Override
    protected void setParameters(PreparedStatement preparedStatement, PassCertificateDetails entity) throws SQLException {
        //1-indexed
        preparedStatement.setString(1, entity.getTechnical_record_id());
        preparedStatement.setString(2, entity.getCreatedByName());
        preparedStatement.setString(3, entity.getCertificateType());
        preparedStatement.setString(4, entity.getGeneratedTimestamp());
        preparedStatement.setString(5, entity.getCertificateId());
    }

    @Override
    protected void setParametersFull(PreparedStatement preparedStatement, PassCertificateDetails entity) throws SQLException {
        setParameters(preparedStatement, entity);

        preparedStatement.setString(6, entity.getTechnical_record_id());
        preparedStatement.setString(7, entity.getCreatedByName());
        preparedStatement.setString(8, entity.getCertificateType());
        preparedStatement.setString(9, entity.getGeneratedTimestamp());
        preparedStatement.setString(10, entity.getCertificateId());
    }

    @Override
    protected PassCertificateDetails mapToEntity(ResultSet rs) throws SQLException {
        PassCertificateDetails passCertificateDetails = new PassCertificateDetails();

        passCertificateDetails.setTechnical_record_id(rs.getString("technical_record_id"));
        passCertificateDetails.setCreatedByName(rs.getString("createdByName"));
        passCertificateDetails.setCertificateType(rs.getString("certificateType"));
        passCertificateDetails.setGeneratedTimestamp(rs.getString("generatedTimestamp"));
        passCertificateDetails.setCertificateId(rs.getString("certificateId"));

        return passCertificateDetails;
    }
}
