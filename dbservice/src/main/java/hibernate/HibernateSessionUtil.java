package hibernate;

import hibernate.entities.AttributeCity;
import hibernate.entities.AttributeType;
import hibernate.entities.City;
import hibernate.entities.Country;
import hibernate.entities.Mayor;
import hibernate.entities.Region;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionUtil {

    private static SessionFactory sessionFactory;

    private HibernateSessionUtil() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Country.class);
                configuration.addAnnotatedClass(Region.class);
                configuration.addAnnotatedClass(City.class);
                configuration.addAnnotatedClass(Mayor.class);
                configuration.addAnnotatedClass(AttributeCity.class);
                configuration.addAnnotatedClass(AttributeType.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return sessionFactory;
    }
}
