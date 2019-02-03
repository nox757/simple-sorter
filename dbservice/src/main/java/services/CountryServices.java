package services;

import hibernate.dao.CountryDao;
import hibernate.entities.Country;

public class CountryServices {

    private CountryDao countryDao;

    public CountryServices(CountryDao countryDao){
        this.countryDao = countryDao;
    }

    public void save(Country country) {
        countryDao.create(country);
    }

    public Country getById(Long id) {
        return countryDao.read(id);
    }
}
