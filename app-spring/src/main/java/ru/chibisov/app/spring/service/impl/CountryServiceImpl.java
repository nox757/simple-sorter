package ru.chibisov.app.spring.service.impl;

import dai.CountryDao;
import entities.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chibisov.app.dto.CountryDTO;
import ru.chibisov.app.service.CountryService;
import ru.chibisov.app.spring.dto.mapper.MapperFacade;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryDao countryDao;

    @Autowired
    private MapperFacade mapper;

    @Override
    public List<CountryDTO> getAll() {
        return mapper.mapAsList(countryDao.getAll(), CountryDTO.class);
    }

    @Override
    public CountryDTO getById(Long countryId) {
        return mapper.map(countryDao.read(countryId), CountryDTO.class);
    }

    @Override
    public CountryDTO update(CountryDTO country) {
        Country countryEntity = mapper.map(country, Country.class);
        countryEntity.setVersion(countryDao.read(country.getId()).getVersion());
        countryDao.update(countryEntity);
        return mapper.map(countryDao.read(country.getId()), CountryDTO.class);
    }

    @Override
    public CountryDTO create(CountryDTO country) {
        Long id = countryDao.create(mapper.map(country, Country.class));
        return mapper.map(countryDao.read(id), CountryDTO.class);
    }

    @Override
    public void delete(CountryDTO country) {
        Country countryEntity = mapper.map(country, Country.class);
        countryEntity.setVersion(countryDao.read(country.getId()).getVersion());
        countryDao.delete(countryEntity);
    }
}
