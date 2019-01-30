package hibernate.dao;

import hibernate.entities.Country;

public interface CountryDao {

    public void save(Country country);
    public Country getById(Long id);
}
