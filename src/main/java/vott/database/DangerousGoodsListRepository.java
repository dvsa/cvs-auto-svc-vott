package vott.database;

import vott.database.connection.ConnectionFactory;
import vott.database.sqlgeneration.TableDetails;
import vott.models.dao.DangerousGoodsList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DangerousGoodsListRepository extends AbstractRepository<DangerousGoodsList> {
    public DangerousGoodsListRepository(ConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    @Override
    protected TableDetails getTableDetails() {
        TableDetails tableDetails = new TableDetails();
        tableDetails.setTableName("AdrDetails");
        tableDetails.setColumnNames(new String[] {
                "name"
        });
        return tableDetails;
    }

    @Override
    protected void setParameters(PreparedStatement preparedStatement, DangerousGoodsList entity) throws SQLException {
        //1-indexed
        preparedStatement.setString(1, entity.getName());
    }

    @Override
    protected void setParametersFull(PreparedStatement preparedStatement, DangerousGoodsList entity) throws SQLException {
        setParameters(preparedStatement, entity);

        preparedStatement.setString(2, entity.getName());
    }

    @Override
    protected DangerousGoodsList mapToEntity(ResultSet rs) throws SQLException {
        DangerousGoodsList dangerousGoodsList = new DangerousGoodsList();

        dangerousGoodsList.setName(rs.getString("name"));

        return dangerousGoodsList;
    }
}
