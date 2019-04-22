package ru.chibisov.app.spring.dto.mapper.registrator;

import entities.Region;
import ma.glasnost.orika.MapperFactory;
import ru.chibisov.app.dto.RegionDTO;

public class RegionMapperRegistrator implements MapperRegistrator {

    @Override
    public void register(MapperFactory mapperFactory) {
        mapperFactory.classMap(Region.class, RegionDTO.class)
                .field("country.id", "countryId")
                .byDefault()
                .register();
    }
}
