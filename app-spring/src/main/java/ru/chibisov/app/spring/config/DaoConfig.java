package ru.chibisov.app.spring.config;

import dai.AttributeCityDao;
import dai.AttributeTypeDao;
import dai.CityDao;
import dai.CountryDao;
import dai.MayorDao;
import dai.RegionDao;
import entities.AttributeCity;
import entities.AttributeType;
import entities.City;
import entities.Country;
import entities.Mayor;
import entities.Region;
import hibernate.dao.AttributeCityDaoImpl;
import hibernate.dao.AttributeTypeDaoImpl;
import hibernate.dao.CityDaoImpl;
import hibernate.dao.CountryDaoImpl;
import hibernate.dao.MayorDaoImpl;
import hibernate.dao.RegionDaoImpl;
import hibernate.util.HibernateSessionEx;
import hibernate.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Configuration
public class DaoConfig {

    private static final String HIBERNATE_PROPERTIES = "hibernate_default.properties";

    @Bean
    public SessionFactory initSessionFactory() throws HibernateSessionEx {
        Properties properties = new Properties();
        try {
            InputStream input = AppServiceConfig.class.getClassLoader().getResourceAsStream(HIBERNATE_PROPERTIES);
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
        try {
            return hibernateUtil.getSessionFactory();
        } catch (HibernateSessionEx hibernateSessionEx) {
            hibernateSessionEx.printStackTrace();
            throw hibernateSessionEx;
        }
    }

    @Bean
    public CountryDao getCountryDao(SessionFactory sessionFactory) {
        return new CountryDaoImpl(sessionFactory);
    }

    @Bean
    public RegionDao getRegionDao(SessionFactory sessionFactory) {
        return new RegionDaoImpl(sessionFactory);
    }

    @Bean
    public CityDao getCityDao(SessionFactory sessionFactory) {
        return new CityDaoImpl(sessionFactory);
    }

    @Bean
    public MayorDao getMayorDao(SessionFactory sessionFactory) {
        return new MayorDaoImpl(sessionFactory);
    }

    @Bean
    public AttributeCityDao getAttributeCityDao(SessionFactory sessionFactory) {
        return new AttributeCityDaoImpl(sessionFactory);
    }

    @Bean
    public AttributeTypeDao getAttributeTypeDao(SessionFactory sessionFactory) {
        return new AttributeTypeDaoImpl(sessionFactory);
    }
}
