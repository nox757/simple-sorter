package hibernate.dao;

import hibernate.entities.Country;
import org.hibernate.SessionFactory;

public class CountryDaoImpl extends AbstractDAO<Country, Long> implements CountryDao {

    public CountryDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    protected Class<Country> getEntityClass() {
        return Country.class;
    }
}
