package hibernate.dao;

import dai.CityDao;
import entities.City;
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
