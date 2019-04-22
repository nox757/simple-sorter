package ru.chibisov.app.spring.service.impl;

import dai.CityDao;
import entities.AttributeCity;
import entities.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chibisov.app.dto.AttributeDTO;
import ru.chibisov.app.dto.CityDTO;
import ru.chibisov.app.dto.CityDTO;
import ru.chibisov.app.service.CityService;
import ru.chibisov.app.spring.dto.mapper.MapperFacade;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    
    @Autowired
    private CityDao cityDao;

    @Autowired
    private MapperFacade mapper;

    @Override
    public List<CityDTO> getAll() {
        return mapper.mapAsList(cityDao.getAll(), CityDTO.class);
    }

    @Override
    public CityDTO getById(Long id) {
        return mapper.map(cityDao.read(id), CityDTO.class);
    }

    @Override
    public CityDTO update(CityDTO city) {
        City cityEntity = mapper.map(city, City.class);
        
        //Attributes don't update
        cityEntity.setAttributes(cityDao.read(city.getId()).getAttributes());
        cityDao.update(cityEntity);
        return mapper.map(cityDao.read(city.getId()), CityDTO.class);
    }

    @Override
    public CityDTO create(CityDTO city) {
        Long id = cityDao.create(mapper.map(city, City.class));
        return mapper.map(cityDao.read(id), CityDTO.class);
    }

    @Override
    public void delete(CityDTO city) {
        cityDao.delete(mapper.map(city, City.class));
    }

    @Override
    public List<AttributeDTO> getCityAttributes(Long cityId) {
        List<AttributeCity> citys = cityDao.read(cityId).getAttributes();
        if (citys == null) {
            return null;
        }
        return mapper.mapAsList(citys, AttributeDTO.class);
    }

    @Override
    public CityDTO addCityAttribute(Long cityId, Long attributeId) {
        City city = cityDao.read(cityId);
        AttributeCity attribute = new AttributeCity();
        attribute.setId(attributeId);
        city.getAttributes().add(attribute);
        cityDao.update(city);
        return mapper.map(cityDao.read(cityId), CityDTO.class);
    }
}
