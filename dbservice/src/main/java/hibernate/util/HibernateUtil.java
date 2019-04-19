package hibernate.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Properties;

public class HibernateUtil {

    private SessionFactory sessionFactory;
    private final Configuration configuration;

    public HibernateUtil(Properties properties, List<Class> classList) {
        this.configuration = new Configuration().setProperties(properties);
        for (Class clazz : classList) {
            configuration.addAnnotatedClass(clazz);
        }
    }

    public SessionFactory getSessionFactory() throws HibernateSessionEx {
        if (sessionFactory == null) {
            try {
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (Exception ex) {
                throw new HibernateSessionEx(ex);
            }
        }
        return sessionFactory;
    }

    public void closeSessionFactory() throws HibernateSessionEx {
        if (sessionFactory == null) {
            return;
        }
        try {
            sessionFactory.close();
        } catch (Exception ex) {
            throw new HibernateSessionEx(ex);
        }
    }
}
