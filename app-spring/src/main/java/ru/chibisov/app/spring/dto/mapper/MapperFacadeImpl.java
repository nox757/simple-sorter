package ru.chibisov.app.spring.dto.mapper;

import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MapperFacadeImpl implements MapperFacade {

    @Autowired
    private MapperFactory mapperFactory;

    @Override
    public <S, D> D map(S source, Class<D> destinationClass) {
        return mapperFactory.getMapperFacade().map(source, destinationClass);
    }

    @Override
    public <S, D> List<D> mapAsList(Iterable<S> source, Class<D> destinationClass) {
        return mapperFactory.getMapperFacade().mapAsList(source, destinationClass);
    }
}
