package hibernate.dao;

import hibernate.HibernateSessionUtil;
import hibernate.entities.Country;
import org.hibernate.Session;

public class CountryDaoImpl implements CountryDao {

    @Override
    public void save(Country country) {
        Session session = HibernateSessionUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(country);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Country getById(Long id) {
        Country country = null;
        Session session = HibernateSessionUtil.getSessionFactory().openSession();
        session.beginTransaction();
        country = session.get(Country.class, id);
        session.getTransaction().commit();
        session.close();
        return country;
    }
}
