package ru.chibisov.app.servicies.impl;

import dai.AttributeTypeDao;
import entities.AttributeType;
import ru.chibisov.app.dto.AttributeTypeDTO;
import ru.chibisov.app.dto.mapper.AttributeTypeMapperDTO;
import ru.chibisov.app.servicies.AttributeTypeService;

import java.util.ArrayList;
import java.util.List;

public class AttributeTypeServiceImpl implements AttributeTypeService {

    private static final AttributeTypeMapperDTO mapperDTO = new AttributeTypeMapperDTO();
    private AttributeTypeDao attributeTypeDao;

    public AttributeTypeServiceImpl(AttributeTypeDao attributeTypeDao) {
        this.attributeTypeDao = attributeTypeDao;
    }

    @Override
    public List<AttributeTypeDTO> getAll() {
        List<AttributeType> attributeTypes = attributeTypeDao.getAll();
        if(attributeTypes == null) {
            return null;
        }
        List<AttributeTypeDTO> attributeTypeDtos = new ArrayList<>();
        for(AttributeType attributeType : attributeTypes) {
            attributeTypeDtos.add(mapperDTO.mapToDto(attributeType));
        }
        return attributeTypeDtos;
    }

    @Override
    public AttributeTypeDTO getById(Long id) {
        AttributeType attributeType = attributeTypeDao.read(id);
        if(attributeType == null) {
            return null;
        }
        return mapperDTO.mapToDto(attributeType);
    }

    @Override
    public AttributeTypeDTO update(AttributeTypeDTO attributeType) {
        AttributeType attributeTypeEntity = mapperDTO.mapFromDto(attributeType);
        attributeTypeDao.update(attributeTypeEntity);
        return mapperDTO.mapToDto(attributeTypeDao.read(attributeType.getId()));
    }

    @Override
    public AttributeTypeDTO create(AttributeTypeDTO attributeType) {
        Long id = attributeTypeDao.create(mapperDTO.mapFromDto(attributeType));
        return mapperDTO.mapToDto(attributeTypeDao.read(id));
    }

    @Override
    public void delete(AttributeTypeDTO attributeType) {
        AttributeType attributeTypeEntity = mapperDTO.mapFromDto(attributeType);
        attributeTypeDao.delete(attributeTypeEntity);
    }
}
