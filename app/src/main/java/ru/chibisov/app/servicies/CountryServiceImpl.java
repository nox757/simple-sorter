package ru.chibisov.app.servicies;

import dai.CountryDao;
import entities.Country;

import java.util.List;

public class CountryServiceImpl implements CountryService {

    private CountryDao countryDao;

    public CountryServiceImpl(CountryDao dao) {
        this.countryDao = dao;
    }

    @Override
    public List<Country> getAll() {
        return countryDao.getAll();
    }

    @Override
    public Country getById(Long id) {
        return countryDao.read(id);
    }

    @Override
    public void update(Country country) {
        countryDao.update(country);
    }

    @Override
    public Country create(Country country) {
        Long id = countryDao.create(country);
        return countryDao.read(id);
    }

    @Override
    public void delete(Country country) {
        countryDao.delete(country);
    }
}
