package ru.chibisov.app.spring.dto.mapper.registrator;

import entities.City;
import ma.glasnost.orika.MapperFactory;
import ru.chibisov.app.dto.CityDTO;

public class CityMapperRegistrator implements MapperRegistrator {

    @Override
    public void register(MapperFactory mapperFactory) {
        mapperFactory.classMap(City.class, CityDTO.class)
                .mapNulls(true)
                .field("attributes{id}", "attributes{}")
                .field("mayor.id", "mayor")
                .field("region.id", "region")
                .byDefault()
                .register();
    }
}
