package vott.database;

import vott.database.connection.ConnectionFactory;
import vott.database.sqlgeneration.TableDetails;
import vott.models.dao.ProductListUnNoList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductListUnNoListRepository extends AbstractRepository<ProductListUnNoList> {
    public ProductListUnNoListRepository(ConnectionFactory connectionFactory) {
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
    protected void setParameters(PreparedStatement preparedStatement, ProductListUnNoList entity) throws SQLException {
        //1-indexed
        preparedStatement.setString(1, entity.getName());
    }

    @Override
    protected void setParametersFull(PreparedStatement preparedStatement, ProductListUnNoList entity) throws SQLException {
        setParameters(preparedStatement, entity);

        preparedStatement.setString(2, entity.getName());
    }

    @Override
    protected ProductListUnNoList mapToEntity(ResultSet rs) throws SQLException {
        ProductListUnNoList productListUnNoList = new ProductListUnNoList();

        productListUnNoList.setName(rs.getString("name"));

        return productListUnNoList;
    }

    @Override
    protected TableDetails getFingerPrintTableDetails() {
        throw new UnsupportedOperationException("Unimplemented method 'getFingerPrintTableDetails'");
    }

    @Override
    protected void setFingerprintParameters(PreparedStatement preparedStatement, ProductListUnNoList entity)
            throws SQLException {
        throw new UnsupportedOperationException("Unimplemented method 'setFingerprintParameters'");
    }
}
