package ru.chibisov.app.spring.gen;

import entities.AttributeType;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class AttributeTypeGenerator extends AbstractGenerator<AttributeType> {

    protected AttributeTypeGenerator(Class<AttributeType> type) {
        super(type);
    }

    @Override
    public AttributeType fill(AttributeType model) {
        model.setId(RandomUtils.nextLong(1, 1000000));
        model.setName("Name_" + RandomStringUtils.random(10));
        return model;
    }
}
