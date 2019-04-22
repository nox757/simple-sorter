package ru.chibisov.app.spring.dto.mapper.registrator;

import entities.AttributeCity;
import ma.glasnost.orika.MapperFactory;
import ru.chibisov.app.dto.AttributeDTO;

public class AttributeMapperRegistrator implements MapperRegistrator {
    @Override
    public void register(MapperFactory mapperFactory) {
        mapperFactory.classMap(AttributeCity.class, AttributeDTO.class)
                .field("attributeType.id", "attributeType")
                .byDefault()
                .register();
    }
}
