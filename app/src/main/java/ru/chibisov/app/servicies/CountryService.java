package ru.chibisov.app.servicies;

import entities.Country;

import java.util.List;

public interface CountryService {

    List<Country> getAll();
    Country getById(Long id);
    void update(Country country);
    Country create(Country country);
    void delete(Country country);
}
