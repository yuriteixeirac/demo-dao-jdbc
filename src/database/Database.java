package database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    public static Connection conn = null;

    public static Connection getConnection() {
        Properties properties = loadProperties();
        String databaseUrl = properties.getProperty("url");

        try {
            conn = DriverManager.getConnection(databaseUrl, properties);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Properties loadProperties() {
        try (BufferedReader br = new BufferedReader(new FileReader("db.properties"))) {
            if (conn == null) {
                Properties props = new Properties();
                props.load(br);

                return props;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
