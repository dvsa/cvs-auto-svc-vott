package vott.database;

import vott.database.connection.ConnectionFactory;
import vott.database.sqlgeneration.TableDetails;
import vott.models.dao.PermittedDangerousGoods;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PermittedDangerousGoodsRepository extends AbstractRepository<PermittedDangerousGoods> {
    public PermittedDangerousGoodsRepository(ConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    @Override
    protected TableDetails getTableDetails() {
        TableDetails tableDetails = new TableDetails();
        tableDetails.setTableName("AdrDetails");
        tableDetails.setColumnNames(new String[] {
                "adr_details_id",
                "adr_dangerous_goods_list"
        });
        return tableDetails;
    }

    @Override
    protected void setParameters(PreparedStatement preparedStatement, PermittedDangerousGoods entity) throws SQLException {
        //1-indexed
        preparedStatement.setString(1, entity.getAdr_details_id());
        preparedStatement.setString(2, entity.getAdr_dangerous_goods_list());
    }

    @Override
    protected void setParametersFull(PreparedStatement preparedStatement, PermittedDangerousGoods entity) throws SQLException {
        setParameters(preparedStatement, entity);

        preparedStatement.setString(3, entity.getAdr_details_id());
        preparedStatement.setString(4, entity.getAdr_dangerous_goods_list());
    }

    @Override
    protected PermittedDangerousGoods mapToEntity(ResultSet rs) throws SQLException {
        PermittedDangerousGoods permittedDangerousGoods = new PermittedDangerousGoods();

        permittedDangerousGoods.setAdr_details_id(rs.getString("adr_details_id"));
        permittedDangerousGoods.setAdr_dangerous_goods_list(rs.getString("adr_dangerous_goods_list"));

        return permittedDangerousGoods;
    }
}
