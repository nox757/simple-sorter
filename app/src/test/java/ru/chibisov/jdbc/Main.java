package ru.chibisov.jdbc;

import dai.CountryDao;
import entities.Country;
import jdbc.CountryDaoImpl;
import jdbc.connection.ConnectionFactory;
import jdbc.connection.ConnectionFactoryImpl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

public class Main {

    private final static String QUERY = "select * from country";

    //TODO: Временный класс для проверок будет заменен тестами позже
    public static void main(String[] args) {

        Properties properties = new Properties();
        try {
            InputStream input = ClassLoader.getSystemClassLoader().getResourceAsStream("db.properties");
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ConnectionFactory connectionFactory = new ConnectionFactoryImpl(properties);
        CountryDao countryDao = new CountryDaoImpl(connectionFactory);
        Country country  = countryDao.read(3L);
//        countryDao.delete(country);
        Country country1 = new Country();
        country1.setName("Новороссия");
       Long result =  countryDao.create(country1);
        System.out.println(result);
//        System.out.println(country.getName());
//        country.setName("РОССИЯ");
//        countryDao.update(country);
//        List<Country> countries = countryDao.getAll();
//        for (Country country1 : countries) {
//            System.out.print(country1.getId().toString() + " : ");
//            System.out.println(country1.getName());
//        }



    }
}
