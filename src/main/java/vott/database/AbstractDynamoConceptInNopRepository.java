package vott.database;

import vott.database.connection.ConnectionFactory;
import vott.database.sqlgeneration.SqlGenerator;
import vott.database.sqlgeneration.TableDetails;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//used for select only queries to get a data based on a dynamo concept from multiple NOP tables
public abstract class AbstractDynamoConceptInNopRepository<T> {

    private final SqlGenerator sqlGenerator = new SqlGenerator();

    private final ConnectionFactory connectionFactory;

    public AbstractDynamoConceptInNopRepository(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public List<T> select(String query) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet rs = preparedStatement.executeQuery();

            List<T> entities = new ArrayList<>();

            while (rs.next()) {
                entities.add(mapToEntity(rs));
            }

            return entities;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract TableDetails getTableDetails();

    protected abstract T mapToEntity(ResultSet rs) throws SQLException;

}
