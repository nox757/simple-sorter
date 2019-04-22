package ru.chibisov.app.spring.config;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.chibisov.app.spring.dto.mapper.registrator.AttributeMapperRegistrator;
import ru.chibisov.app.spring.dto.mapper.registrator.CityMapperRegistrator;
import ru.chibisov.app.spring.dto.mapper.registrator.MayorMapperRegistrator;
import ru.chibisov.app.spring.dto.mapper.registrator.RegionMapperRegistrator;

@Configuration
public class MapperConfig {

    @Bean
    public MapperFactory getMapperFactory() {
        MapperFactory mapperFactory =  new DefaultMapperFactory.Builder().build();
        new AttributeMapperRegistrator().register(mapperFactory);
        new CityMapperRegistrator().register(mapperFactory);
        new MayorMapperRegistrator().register(mapperFactory);
        new RegionMapperRegistrator().register(mapperFactory);
        return mapperFactory;
    }
}
