package ru.chibisov.hibernate;

import entities.AttributeCity;
import entities.AttributeType;
import entities.City;
import entities.Country;
import entities.Mayor;
import entities.Region;
import hibernate.util.HibernateSessionEx;
import hibernate.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.junit.Before;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

abstract public class BaseTest {

    private final static String DEFAULT_PROPERTY_NAME = "hibernate_default.properties";
    SessionFactory sessionFactory;

    @Before
    public void init() throws HibernateSessionEx {
        Properties properties = new Properties();
        try {
            InputStream input = ClassLoader.getSystemClassLoader().getResourceAsStream(getNamePropertyResource());
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
        sessionFactory = hibernateUtil.getSessionFactory();
    }

    protected String getNamePropertyResource() {
        return DEFAULT_PROPERTY_NAME;
    }

}
