package ru.chibisov.app.dto.mapper;

import entities.Country;
import ru.chibisov.app.dto.CountryDTO;

public class CountryMapperDTO implements MapperDTO<Country, CountryDTO> {

    @Override
    public CountryDTO mapToDto(Country entity) {
        return new CountryDTO().setId(entity.getId())
                .setName(entity.getName());
    }

    @Override
    public Country mapFromDto(CountryDTO dto) {
        Country country = new Country();
        country.setId(dto.getId());
        country.setName(dto.getName());
        return country;
    }
}
