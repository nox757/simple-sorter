package ru.chibisov.app.servicies.impl;

import dai.AttributeCityDao;
import ru.chibisov.app.servicies.AttributeService;
import ru.chibisov.app.servicies.AttributeTypeService;

public class AttributeServiceImpl implements AttributeService {

    private AttributeCityDao attributeCityDao;

    public AttributeServiceImpl(AttributeCityDao attributeCityDao) {
        this.attributeCityDao = attributeCityDao;
    }
}
