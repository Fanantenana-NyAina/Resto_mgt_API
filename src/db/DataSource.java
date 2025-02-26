package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    private final Connection con;
    private final String host = System.getenv("DB_HOST");
    private final int port = 5432;
    private final String database = System.getenv("DB_NAME") ;
    private final String user = System.getenv("DB_USER") ;
    private final String password = System.getenv("DB_PWD") ;
    private final String jdbcUrl = "jdbc:postgresql://" + host + ":" + port + "/" + database;

    public DataSource() {
        try {
            con = DriverManager.getConnection(jdbcUrl, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return con;
    }
}
