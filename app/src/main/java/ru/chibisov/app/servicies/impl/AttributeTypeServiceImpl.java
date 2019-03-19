package ru.chibisov.app.servicies.impl;

import dai.AttributeTypeDao;
import ru.chibisov.app.servicies.AttributeTypeService;

public class AttributeTypeServiceImpl implements AttributeTypeService {

    private AttributeTypeDao attributeTypeDao;

    public AttributeTypeServiceImpl(AttributeTypeDao attributeTypeDao) {
        this.attributeTypeDao = attributeTypeDao;
    }
}
