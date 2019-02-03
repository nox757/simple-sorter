package hibernate.dao;

import hibernate.entities.Country;

public class CountryDaoImpl extends AbstractDAO<Country, Long> implements CountryDao {

    @Override
    protected Class<Country> getEntityClass() {
        return Country.class;
    }
}
