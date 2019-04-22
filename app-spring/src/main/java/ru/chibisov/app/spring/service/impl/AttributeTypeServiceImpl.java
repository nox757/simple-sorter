package ru.chibisov.app.spring.service.impl;

import dai.AttributeTypeDao;
import entities.AttributeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chibisov.app.dto.AttributeTypeDTO;
import ru.chibisov.app.service.AttributeTypeService;
import ru.chibisov.app.spring.dto.mapper.MapperFacade;

import java.util.List;

@Service
public class AttributeTypeServiceImpl implements AttributeTypeService {

    @Autowired
    private AttributeTypeDao typeDao;

    @Autowired
    private MapperFacade mapper;

    @Override
    public List<AttributeTypeDTO> getAll() {
        return mapper.mapAsList(typeDao.getAll(), AttributeTypeDTO.class);
    }

    @Override
    public AttributeTypeDTO getById(Long id) {
        return mapper.map(typeDao.read(id), AttributeTypeDTO.class);
    }

    @Override
    public AttributeTypeDTO update(AttributeTypeDTO attributeType) {
        typeDao.update(mapper.map(attributeType, AttributeType.class));
        return mapper.map(typeDao.read(attributeType.getId()), AttributeTypeDTO.class);
    }

    @Override
    public AttributeTypeDTO create(AttributeTypeDTO attributeType) {
        Long id = typeDao.create(mapper.map(attributeType, AttributeType.class));
        return mapper.map(typeDao.read(id), AttributeTypeDTO.class);
    }

    @Override
    public void delete(AttributeTypeDTO attributeType) {
        typeDao.delete(mapper.map(attributeType, AttributeType.class));
    }
}
