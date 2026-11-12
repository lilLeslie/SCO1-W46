package dk.easv.mrs.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

public class DbConnection {
    private static final String PROP_FILE = "config/config.settings";
    SQLServerDataSource dataSource;

    public DbConnection() throws IOException {
        Properties dbProperties = new Properties();
        dbProperties.load(new FileInputStream(PROP_FILE));

        dataSource = new SQLServerDataSource();
        dataSource.setServerName(dbProperties.getProperty("SQL_SERVER"));
        dataSource.setDatabaseName(dbProperties.getProperty("SQL_DATABASE"));
        dataSource.setUser(dbProperties.getProperty("SQL_USER"));
        dataSource.setPassword(dbProperties.getProperty("SQL_PASSWORD"));
        dataSource.setPortNumber(1433);
        dataSource.setTrustServerCertificate(true);
    }

    public Connection getConnection() throws SQLServerException {
        return dataSource.getConnection();
    }

    public static void main(String[] args) throws Exception {
        DbConnection dbConnection = new DbConnection();
        try (Connection connection = dbConnection.getConnection()){
            System.out.println("Connection open: " + !connection.isClosed());
        }
    }
}
