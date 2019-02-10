package hibernate.dao;

import hibernate.dao.interfeces.AttributeCityDao;
import hibernate.entities.AttributeCity;
import org.hibernate.SessionFactory;

public class AttributeCityDaoImpl extends AbstractDAO<AttributeCity, Long> implements AttributeCityDao {

    public AttributeCityDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    protected Class<AttributeCity> getEntityClass() {
        return AttributeCity.class;
    }
}
