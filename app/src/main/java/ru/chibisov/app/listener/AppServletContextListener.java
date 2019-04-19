package ru.chibisov.app.listener;

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
import ru.chibisov.app.service.AttributeService;
import ru.chibisov.app.service.AttributeTypeService;
import ru.chibisov.app.service.CityService;
import ru.chibisov.app.service.CountryService;
import ru.chibisov.app.service.MayorService;
import ru.chibisov.app.service.RegionService;
import ru.chibisov.app.service.impl.AttributeServiceImpl;
import ru.chibisov.app.service.impl.AttributeTypeServiceImpl;
import ru.chibisov.app.service.impl.CityServiceImpl;
import ru.chibisov.app.service.impl.CountryServiceImpl;
import ru.chibisov.app.service.impl.MayorServiceImpl;
import ru.chibisov.app.service.impl.RegionServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

public class AppServletContextListener implements ServletContextListener {

    private static final String HIBERNATE_PROPERTIES = "hibernate_default.properties";
    private HibernateUtil hibernateUtil;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            ServletContext servletContext = servletContextEvent.getServletContext();
            SessionFactory sessionFactory = initSessionFactory();
            CountryService countryService = new CountryServiceImpl(new CountryDaoImpl(sessionFactory));
            RegionService regionService = new RegionServiceImpl(new RegionDaoImpl(sessionFactory));
            CityService cityService = new CityServiceImpl(new CityDaoImpl(sessionFactory), new MayorDaoImpl(sessionFactory));
            MayorService mayorService = new MayorServiceImpl(new MayorDaoImpl(sessionFactory));
            AttributeService attributeService = new AttributeServiceImpl(new AttributeCityDaoImpl(sessionFactory));
            AttributeTypeService attributeTypeService = new AttributeTypeServiceImpl(new AttributeTypeDaoImpl(sessionFactory));
            servletContext.setAttribute("countryService", countryService);
            servletContext.setAttribute("regionService", regionService);
            servletContext.setAttribute("cityService", cityService);
            servletContext.setAttribute("mayorService", mayorService);
            servletContext.setAttribute("attributeService", attributeService);
            servletContext.setAttribute("attributeTypeService", attributeTypeService);
        } catch (HibernateSessionEx ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

        try {
            hibernateUtil.closeSessionFactory();
        } catch (HibernateSessionEx ex) {
            ex.printStackTrace();
        }

        Enumeration<Driver> drivers = java.sql.DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            java.sql.Driver driver = drivers.nextElement();
            try {
                java.sql.DriverManager.deregisterDriver(driver);
            } catch (Exception e) {
                //log exception or ignore
            }
        }
    }


    public SessionFactory initSessionFactory() throws HibernateSessionEx {
        Properties properties = new Properties();
        try {
            InputStream input = AppServletContextListener.class.getClassLoader().getResourceAsStream(HIBERNATE_PROPERTIES);
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
        hibernateUtil = new HibernateUtil(properties, classList);
        return hibernateUtil.getSessionFactory();
    }
}
