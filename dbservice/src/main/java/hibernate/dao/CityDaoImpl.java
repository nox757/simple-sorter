package hibernate.dao;

import hibernate.dao.interfeces.CityDao;
import hibernate.entities.City;
import org.hibernate.SessionFactory;

public class CityDaoImpl extends AbstractDAO<City, Long> implements CityDao {

    public CityDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    protected Class<City> getEntityClass() {
        return City.class;
    }
}
