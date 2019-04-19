package ru.chibisov.app.dto.mapper;

import entities.Country;
import entities.Region;
import ru.chibisov.app.dto.RegionDTO;

public class RegionMapperDTO implements MapperDTO<Region, RegionDTO> {

    @Override
    public RegionDTO mapToDto(Region entity) {
        return new RegionDTO().setId(entity.getId())
                .setName(entity.getName())
                .setCountryId(entity.getCountry().getId());
    }

    @Override
    public Region mapFromDto(RegionDTO dto) {
        Region region = new Region();
        region.setId(dto.getId());
        region.setName(dto.getName());

        Country country = new Country();
        country.setId(dto.getCountryId());
        region.setCountry(country);
        return region;
    }
}
