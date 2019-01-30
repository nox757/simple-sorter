package services;

import hibernate.dao.CountryDao;
import hibernate.entities.Country;

public class CountryServices {

    private CountryDao countryDao;

    public CountryServices(CountryDao countryDao){
        this.countryDao = countryDao;
    }

    public void saveCountry(Country country) {
        countryDao.save(country);
    }

    public Country getById(Long id) {
        return countryDao.getById(id);
    }
}
