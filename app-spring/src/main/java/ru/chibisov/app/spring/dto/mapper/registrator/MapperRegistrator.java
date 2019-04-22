package ru.chibisov.app.spring.dto.mapper.registrator;

import ma.glasnost.orika.MapperFactory;

public interface MapperRegistrator {
    /**
     * Register custom mapper
     * @param mapperFactory
     */
    void register(MapperFactory mapperFactory);
}
