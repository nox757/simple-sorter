package ru.chibisov.app.servicies.impl;

import dai.AttributeCityDao;
import entities.AttributeCity;
import ru.chibisov.app.dto.AttributeDTO;
import ru.chibisov.app.dto.mapper.AttributeMapperDTO;
import ru.chibisov.app.servicies.AttributeService;

import java.util.ArrayList;
import java.util.List;

public class AttributeServiceImpl implements AttributeService {

    private static final AttributeMapperDTO mapperDTO = new AttributeMapperDTO();
    private AttributeCityDao attributeCityDao;

    public AttributeServiceImpl(AttributeCityDao attributeCityDao) {
        this.attributeCityDao = attributeCityDao;
    }

    @Override
    public List<AttributeDTO> getAll() {
        List<AttributeCity> attributeCities = attributeCityDao.getAll();
        if(attributeCities == null) {
            return null;
        }
        List<AttributeDTO> attributeDtos = new ArrayList<>();
        for(AttributeCity attributeCity : attributeCities) {
            attributeDtos.add(mapperDTO.mapToDto(attributeCity));
        }
        return attributeDtos;
    }

    @Override
    public AttributeDTO getById(Long id) {
        AttributeCity attribute = attributeCityDao.read(id);
        if(attribute == null) {
            return null;
        }
        return mapperDTO.mapToDto(attribute);
    }

    @Override
    public AttributeDTO update(AttributeDTO attribute) {
        AttributeCity attributeEntity = mapperDTO.mapFromDto(attribute);
        attributeCityDao.update(attributeEntity);
        return mapperDTO.mapToDto(attributeCityDao.read(attribute.getId()));
    }

    @Override
    public AttributeDTO create(AttributeDTO attribute) {
        Long id = attributeCityDao.create(mapperDTO.mapFromDto(attribute));
        return mapperDTO.mapToDto(attributeCityDao.read(id));
    }

    @Override
    public void delete(AttributeDTO attribute) {
        AttributeCity attributeEntity = mapperDTO.mapFromDto(attribute);
        attributeCityDao.delete(attributeEntity);
    }
}
