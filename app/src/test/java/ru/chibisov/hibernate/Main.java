package ru.chibisov.hibernate;

import dai.CountryDao;
import hibernate.util.HibernateSessionEx;
import hibernate.util.HibernateUtil;
import hibernate.dao.CountryDaoImpl;
import entities.AttributeCity;
import entities.AttributeType;
import entities.City;
import entities.Country;
import entities.Mayor;
import entities.Region;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {

    //TODO: Временный класс для проверок будет заменен тестами позже
    public static void main(String[] args) {

        Properties properties = new Properties();
        try {
            InputStream input = ClassLoader.getSystemClassLoader().getResourceAsStream("hibernate.properties");
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Class> classList = new ArrayList<>();
        classList.add(Country.class);
        classList.add(Region.class);
        classList.add(City.class);
        classList.add(Mayor.class);
        classList.add(AttributeCity.class);
        classList.add(AttributeType.class);
        HibernateUtil hibernateUtil = new HibernateUtil(properties, classList);
        CountryDao countryDao = null;
        try {
            countryDao = new CountryDaoImpl(hibernateUtil.getSessionFactory());
        } catch (HibernateSessionEx hibernateSessionEx) {
            hibernateSessionEx.printStackTrace();
        }
        Country country = new Country();
        country.setName("new Country");
        countryDao.create(country);
        System.out.println(countryDao.read(2L).getName());
        List<Country> countries = countryDao.getAll();
        for (Country country1 : countries) {
            System.out.println(country1.getName());
        }
    }
}
