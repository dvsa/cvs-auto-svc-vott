package vott.database;

import vott.database.connection.ConnectionFactory;
import vott.database.sqlgeneration.TableDetails;
import vott.models.dao.ProductListUnNo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductListUnNoRepository extends AbstractRepository<ProductListUnNo> {
    public ProductListUnNoRepository(ConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    @Override
    protected TableDetails getTableDetails() {
        TableDetails tableDetails = new TableDetails();
        tableDetails.setTableName("AdrDetails");
        tableDetails.setColumnNames(new String[] {
                "adr_details_id",
                "adr_productListUnNo_list_id"
        });
        return tableDetails;
    }

    @Override
    protected void setParameters(PreparedStatement preparedStatement, ProductListUnNo entity) throws SQLException {
        //1-indexed
        preparedStatement.setString(1, entity.getAdr_details_id());
        preparedStatement.setString(2, entity.getAdr_productListUnNo_list_id());
    }

    @Override
    protected void setParametersFull(PreparedStatement preparedStatement, ProductListUnNo entity) throws SQLException {
        setParameters(preparedStatement, entity);

        preparedStatement.setString(3, entity.getAdr_details_id());
        preparedStatement.setString(4, entity.getAdr_productListUnNo_list_id());
    }

    @Override
    protected ProductListUnNo mapToEntity(ResultSet rs) throws SQLException {
        ProductListUnNo productListUnNo = new ProductListUnNo();

        productListUnNo.setAdr_details_id(rs.getString("adr_details_id"));
        productListUnNo.setAdr_productListUnNo_list_id(rs.getString("adr_productListUnNo_list_id"));

        return productListUnNo;
    }

    @Override
    protected TableDetails getFingerPrintTableDetails() {
        throw new UnsupportedOperationException("Unimplemented method 'getFingerPrintTableDetails'");
    }

    @Override
    protected void setFingerprintParameters(PreparedStatement preparedStatement, ProductListUnNo entity)
            throws SQLException {
        throw new UnsupportedOperationException("Unimplemented method 'setFingerprintParameters'");
    }
}
