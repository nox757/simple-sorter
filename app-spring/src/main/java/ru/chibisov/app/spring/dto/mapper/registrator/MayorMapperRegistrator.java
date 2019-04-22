package ru.chibisov.app.spring.dto.mapper.registrator;

import entities.Mayor;
import ma.glasnost.orika.MapperFactory;
import ru.chibisov.app.dto.MayorDTO;

public class MayorMapperRegistrator implements MapperRegistrator {

    @Override
    public void register(MapperFactory mapperFactory) {
        mapperFactory.classMap(Mayor.class, MayorDTO.class)
                .field("city.id", "cityId")
                .byDefault()
                .register();
    }
}
