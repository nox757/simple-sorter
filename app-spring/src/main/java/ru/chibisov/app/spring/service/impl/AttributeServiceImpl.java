package ru.chibisov.app.spring.service.impl;

import dai.AttributeCityDao;
import entities.AttributeCity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chibisov.app.dto.AttributeDTO;
import ru.chibisov.app.service.AttributeService;
import ru.chibisov.app.spring.dto.mapper.MapperFacade;

import java.util.List;

@Service
public class AttributeServiceImpl implements AttributeService {

    @Autowired
    private AttributeCityDao cityDao;

    @Autowired
    private MapperFacade mapper;

    @Override
    public List<AttributeDTO> getAll() {
        return mapper.mapAsList(cityDao.getAll(), AttributeDTO.class);
    }

    @Override
    public AttributeDTO getById(Long id) {
        return mapper.map(cityDao.read(id), AttributeDTO.class);
    }

    @Override
    public AttributeDTO update(AttributeDTO attribute) {
        cityDao.update(mapper.map(attribute, AttributeCity.class));
        return mapper.map(cityDao.read(attribute.getId()), AttributeDTO.class);
    }

    @Override
    public AttributeDTO create(AttributeDTO attribute) {
        Long id = cityDao.create(mapper.map(attribute, AttributeCity.class));
        return mapper.map(cityDao.read(id), AttributeDTO.class);
    }

    @Override
    public void delete(AttributeDTO attribute) {
        cityDao.delete(mapper.map(attribute, AttributeCity.class));
    }
}
