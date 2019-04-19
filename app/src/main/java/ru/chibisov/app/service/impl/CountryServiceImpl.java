package ru.chibisov.app.service.impl;

import dai.CountryDao;
import entities.Country;
import ru.chibisov.app.dto.CountryDTO;
import ru.chibisov.app.dto.mapper.CountryMapperDTO;
import ru.chibisov.app.service.CountryService;

import java.util.ArrayList;
import java.util.List;

public class CountryServiceImpl implements CountryService {

    private static final CountryMapperDTO mapperDTO = new CountryMapperDTO();
    private CountryDao countryDao;

    public CountryServiceImpl(CountryDao dao) {
        this.countryDao = dao;
    }

    @Override
    public List<CountryDTO> getAll() {
        List<Country> countries = countryDao.getAll();
        if (countries == null) {
            return null;
        }
        List<CountryDTO> countryDtos = new ArrayList<>();
        for (Country country : countries) {
            countryDtos.add(mapperDTO.mapToDto(country));
        }
        return countryDtos;
    }

    @Override
    public CountryDTO getById(Long id) {
        Country country = countryDao.read(id);
        if (country == null) {
            return null;
        }
        return mapperDTO.mapToDto(country);
    }

    @Override
    public CountryDTO update(CountryDTO country) {
        Country countryEntity = mapperDTO.mapFromDto(country);
        countryEntity.setVersion(countryDao.read(country.getId()).getVersion());
        countryDao.update(countryEntity);
        return mapperDTO.mapToDto(countryDao.read(country.getId()));
    }

    @Override
    public CountryDTO create(CountryDTO country) {
        Long id = countryDao.create(mapperDTO.mapFromDto(country));
        return mapperDTO.mapToDto(countryDao.read(id));
    }

    @Override
    public void delete(CountryDTO country) {
        Country countryEntity = mapperDTO.mapFromDto(country);
        countryEntity.setVersion(countryDao.read(country.getId()).getVersion());
        countryDao.delete(countryEntity);
    }
}
