package ru.chibisov.app.servicies.impl;

import dai.CityDao;
import ru.chibisov.app.servicies.CityService;

public class CityServiceImpl implements CityService {

    private CityDao cityDao;

    public CityServiceImpl(CityDao cityDao) {
        this.cityDao = cityDao;
    }
}
