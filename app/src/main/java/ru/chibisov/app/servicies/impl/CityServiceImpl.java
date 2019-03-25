package ru.chibisov.app.servicies.impl;

import dai.CityDao;
import dai.MayorDao;
import entities.AttributeCity;
import entities.City;
import entities.Mayor;
import ru.chibisov.app.dto.AttributeDTO;
import ru.chibisov.app.dto.CityDTO;
import ru.chibisov.app.dto.mapper.AttributeMapperDTO;
import ru.chibisov.app.dto.mapper.CityMapperDTO;
import ru.chibisov.app.servicies.AttributeService;
import ru.chibisov.app.servicies.CityService;

import javax.xml.stream.events.Attribute;
import java.util.ArrayList;
import java.util.List;

public class CityServiceImpl implements CityService {

    private static final CityMapperDTO mapperDTO = new CityMapperDTO();
    private static final AttributeMapperDTO attributeMapperDTO = new AttributeMapperDTO();
    private CityDao cityDao;
    private MayorDao mayorDao;


    public CityServiceImpl(CityDao cityDao, MayorDao mayorDao) {
        this.cityDao = cityDao;
        this.mayorDao = mayorDao;
    }

    @Override
    public List<CityDTO> getAll() {
        List<City> cities = cityDao.getAll();
        if (cities == null) {
            return null;
        }
        List<CityDTO> cityDtos = new ArrayList<>();
        for (City city : cities) {
            cityDtos.add(mapperDTO.mapToDto(city));
        }
        return cityDtos;
    }

    @Override
    public CityDTO getById(Long id) {
        City city = cityDao.read(id);
        if (city == null) {
            return null;
        }
        return mapperDTO.mapToDto(city);
    }

    @Override
    public CityDTO update(CityDTO city) {
        City cityEntity = mapperDTO.mapFromDto(city);

        //Attributes don't update
        cityEntity.setAttributes(cityDao.read(city.getId()).getAttributes());
        cityDao.update(cityEntity);
        return mapperDTO.mapToDto(cityDao.read(city.getId()));
    }

    @Override
    public CityDTO create(CityDTO city) {
        Long id = cityDao.create(mapperDTO.mapFromDto(city));
        return mapperDTO.mapToDto(cityDao.read(id));
    }

    @Override
    public void delete(CityDTO city) {
        City cityEntity = mapperDTO.mapFromDto(city);
        cityDao.delete(cityEntity);
    }

    @Override
    public List<AttributeDTO> getCityAttributes(Long id) {
        List<AttributeCity> attributes = cityDao.read(id).getAttributes();
        if (attributes == null) {
            return null;
        }
        List<AttributeDTO> attributeDtos = new ArrayList<>();
        for (AttributeCity attribute : attributes) {
            attributeDtos.add(attributeMapperDTO.mapToDto(attribute));
        }
        return attributeDtos;
    }
}
