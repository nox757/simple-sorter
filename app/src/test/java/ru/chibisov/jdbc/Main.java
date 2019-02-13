package ru.chibisov.jdbc;

import hibernate.dao.CountryDaoImpl;
import hibernate.dao.interfeces.CountryDao;
import hibernate.entities.AttributeCity;
import hibernate.entities.AttributeType;
import hibernate.entities.City;
import hibernate.entities.Country;
import hibernate.entities.Mayor;
import hibernate.entities.Region;
import hibernate.util.HibernateSessionEx;
import hibernate.util.HibernateUtil;
import jdbc.connection.ConnectionFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {

    private final static String QUERY = "select * from country";

    public static void main(String[] args) {

        Properties properties = new Properties();
        try {
            InputStream input = ClassLoader.getSystemClassLoader().getResourceAsStream("db.properties");
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ConnectionFactory connectionFactory = new ConnectionFactory(properties);
        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(QUERY)) {
            while (rs.next()) {
                int id = rs.getInt("country_id");
                String name = rs.getString("name");
                System.out.println(id + "," + name );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
