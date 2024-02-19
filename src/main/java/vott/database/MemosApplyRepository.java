package vott.database;

import vott.database.connection.ConnectionFactory;
import vott.database.sqlgeneration.TableDetails;
import vott.models.dao.MemosApply;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemosApplyRepository extends AbstractRepository<MemosApply> {
    public MemosApplyRepository(ConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    @Override
    protected TableDetails getTableDetails() {
        TableDetails tableDetails = new TableDetails();
        tableDetails.setTableName("AdrDetails");
        tableDetails.setColumnNames(new String[] {
                "adr_details_id",
                "memo"
        });
        return tableDetails;
    }

    @Override
    protected void setParameters(PreparedStatement preparedStatement, MemosApply entity) throws SQLException {
        //1-indexed
        preparedStatement.setString(1, entity.getAdr_details_id());
        preparedStatement.setString(2, entity.getMemo());
    }

    @Override
    protected void setParametersFull(PreparedStatement preparedStatement, MemosApply entity) throws SQLException {
        setParameters(preparedStatement, entity);

        preparedStatement.setString(3, entity.getAdr_details_id());
        preparedStatement.setString(4, entity.getMemo());
    }

    @Override
    protected MemosApply mapToEntity(ResultSet rs) throws SQLException {
        MemosApply memosApply = new MemosApply();

        memosApply.setAdr_details_id(rs.getString("adr_details_id"));
        memosApply.setMemo(rs.getString("memo"));

        return memosApply;
    }
}
