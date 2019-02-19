package jdbc.connection;

import hibernate.dao.HibernateDdException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactoryImpl implements ConnectionFactory {

    private final Properties properties;

    public ConnectionFactoryImpl(Properties properties) {
        this.properties = properties;
    }

    public Connection getConnection() {
        try {
            Class.forName(properties.getProperty("CLASS_NAME"));

            String url = properties.getProperty("URL");
            String name = properties.getProperty("USERNAME");
            String pass = properties.getProperty("PASSWORD");

            return DriverManager.getConnection(url, name, pass);
        } catch (SQLException | ClassNotFoundException e) {
            throw new HibernateDdException(e);
        }
    }
}
