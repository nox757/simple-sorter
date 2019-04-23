package ru.chibisov.app.spring.gen;

import entities.AttributeCity;
import entities.AttributeType;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public final class AttributeGenerator extends AbstractGenerator<AttributeCity> {

    private final AttributeTypeGenerator typeGenerator = new AttributeTypeGenerator(AttributeType.class);

    public AttributeGenerator(Class<AttributeCity> type) {
        super(type);
    }

    @Override
    public AttributeCity fill(AttributeCity model) {
        model.setId(RandomUtils.nextLong(1, 1000000));
        model.setName("Name_" + RandomStringUtils.randomAlphabetic(10));
        model.setValue("Value_" + RandomStringUtils.randomAlphabetic(5));
        model.setAttributeType(typeGenerator.getData());
        return model;
    }

}
