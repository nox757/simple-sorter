package ru.chibisov.jdbc;

import jdbc.connection.ConnectionFactory;
import jdbc.connection.ConnectionFactoryImpl;
import org.junit.BeforeClass;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

abstract public class BaseJdbcTest {

    static ConnectionFactory connectionFactory;

    @BeforeClass
    public static void init() throws IOException {
        Properties properties = new Properties();

        InputStream input = ClassLoader.getSystemClassLoader().getResourceAsStream("db.properties");
        properties.load(input);

        connectionFactory = new ConnectionFactoryImpl(properties);
    }
}
