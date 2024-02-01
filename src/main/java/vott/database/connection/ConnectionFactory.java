package vott.database.connection;

import lombok.RequiredArgsConstructor;
import vott.config.VottConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@RequiredArgsConstructor
public class ConnectionFactory {

    private final VottConfiguration configuration;

    public Connection getConnection() throws SQLException {

//        Properties connectionProps = new Properties();
//        connectionProps.put("user", configuration.getDatabaseProperties().getUsername());
//        connectionProps.put("password", configuration.getDatabaseProperties().getPassword());
//        //connectionProps.put("useAffectedRows", true);
//        connectionProps.put("compensateOnDuplicateKeyUpdateCounts", true);
//
//
//        Connection newConnection = DriverManager.getConnection(configuration.getDatabaseProperties().toJdbcUrl(),connectionProps);
 //       return newConnection;

        return DriverManager.getConnection(
            configuration.getDatabaseProperties().toJdbcUrl(),
            configuration.getDatabaseProperties().getUsername(),
            configuration.getDatabaseProperties().getPassword()
        );
    }
}
