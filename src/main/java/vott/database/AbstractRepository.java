package vott.database;

import vott.database.connection.ConnectionFactory;
import vott.database.sqlgeneration.SqlGenerator;
import vott.database.sqlgeneration.TableDetails;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRepository<T> {

    private final SqlGenerator sqlGenerator = new SqlGenerator();

    private final ConnectionFactory connectionFactory;

    public AbstractRepository(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public int partialUpsert(T entity) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    sqlGenerator.generatePartialUpsertSql(getTableDetails()),
                    //Statement.RETURN_GENERATED_KEYS
                    new String[]{"id"}
            );

            setParameters(preparedStatement, entity);

            return upsert(
                    preparedStatement,
                    entity
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public int fullUpsertIfNotExists(T entity) {

        int idReturned = -1;

        //TODO change the sql to use select statement based on if the table has a fingerprint or not
        //TODO implement the finger print columns into each concrete repository class
        //TODO add a definition of the fingerprint column name for each table to TableDetails object

        try (Connection connection = connectionFactory.getConnection()) {

            //do a select based on table fingerprint
            PreparedStatement preparedStatementSelect = connection.prepareStatement(
                    sqlGenerator.generateSelectByFingerprint(getFingerPrintTableDetails()),
                    Statement.RETURN_GENERATED_KEYS
            );

            setFingerprintParameters(preparedStatementSelect, entity);
            ResultSet selectRS = preparedStatementSelect.executeQuery();

            //should only ever have one row in result set
            while (selectRS.next()) {
                //set id to return to one returned from select query
                idReturned = selectRS.getInt("id");
            }

            //upsert data
            PreparedStatement preparedStatement = connection.prepareStatement(
                    sqlGenerator.generateFullUpsertSql(getTableDetails()),
                    Statement.RETURN_GENERATED_KEYS
            );

            setParametersFull(preparedStatement, entity);
            //capture id returned from upsert
            //in RDS v8 an upsert with no change does not return a generated key
            int upsertIdReturned = upsert(
                    preparedStatement,
                    entity
            );

            //added to ensure id is returned from select if nothing is returned by upsert
            if (upsertIdReturned != -1) {
                idReturned = upsertIdReturned;
            }


            return idReturned;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int fullUpsert(T entity) {
        try (Connection connection = connectionFactory.getConnection()) {

            // String[] columns = {"id"};
            PreparedStatement preparedStatement = connection.prepareStatement(
                    sqlGenerator.generateFullUpsertSql(getTableDetails()),
                    Statement.RETURN_GENERATED_KEYS
            );

            setParametersFull(preparedStatement, entity);

            return upsert(
                    preparedStatement,
                    entity
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void fullUpsertWithoutID(T entity) throws RuntimeException, SQLException {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    sqlGenerator.generateFullUpsertSqlWithoutID(getTableDetails()),
                    Statement.RETURN_GENERATED_KEYS
            );

            setParametersFull(preparedStatement, entity);

            upsert(
                    preparedStatement,
                    entity
            );
        } catch (RuntimeException e) {
            boolean b = (e.getMessage().startsWith("Expected exactly 1 generated key,"));
            if (!b) {
                throw new SQLException(e.getCause());
            }
        }
    }


    public T select(int primaryKey) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    sqlGenerator.generateSelectSql(getTableDetails(), primaryKey)
            );

            ResultSet rs = preparedStatement.executeQuery();

            List<T> entities = new ArrayList<>();

            while (rs.next()) {
                entities.add(mapToEntity(rs));
            }

            if (entities.size() != 1) {
                throw new RuntimeException("Expected exactly 1 entity, got " + entities.size());
            }

            return entities.get(0);
        } catch (SQLException e) {
            throw new RuntimeException("DELETE failed", e);
        }
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

    public void delete(int primaryKey) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    sqlGenerator.generateDeleteSql(getTableDetails(), primaryKey)
            );

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("DELETE failed", e);
        }
    }

    protected abstract TableDetails getTableDetails();

    protected abstract TableDetails getFingerPrintTableDetails();

    protected abstract void setFingerprintParameters(PreparedStatement preparedStatement, T entity) throws SQLException;

    protected abstract void setParameters(PreparedStatement preparedStatement, T entity) throws SQLException;

    protected abstract void setParametersFull(PreparedStatement preparedStatement, T entity) throws SQLException;

    protected abstract T mapToEntity(ResultSet rs) throws SQLException;

    private int upsert(PreparedStatement preparedStatement, T entity) {


        try {
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows != 1 && affectedRows != 2) {
                throw new RuntimeException("Expected either 1 (INSERT) or 2 (UPDATE) affected rows, got " + affectedRows);
            }

            ResultSet rs = preparedStatement.getGeneratedKeys();

            //Get Generated keys can return 2 values when a full upsert is executed, this is a known issue with JDBC and mysql - https://bugs.mysql.com/bug.php?id=90688
            //Since RDS8 this can also return an empty resultSet if 0 rows are updated
            List<Integer> generatedKeys = new ArrayList<>();
            if (rs.getMetaData().getColumnCount() != 1) {
                throw new RuntimeException("Expected exactly 1 column in generated keys ResultSet, got " + rs.getMetaData().getColumnCount());
            }

            while (rs.next()) {
                generatedKeys.add(rs.getInt(1));

            }

            if (generatedKeys.size() != 1 && generatedKeys.size() != 2) {
                //to ensure there is always a value in generatedKeys(0)
                generatedKeys.add(-1);
            }

            return generatedKeys.get(0);

        } catch (SQLException e) {
            return -1;
        }
    }
}
