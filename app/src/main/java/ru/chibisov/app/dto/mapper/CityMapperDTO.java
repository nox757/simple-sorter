package ru.chibisov.app.dto.mapper;

import entities.AttributeCity;
import entities.City;
import entities.Mayor;
import entities.Region;
import ru.chibisov.app.dto.CityDTO;

import java.util.List;
import java.util.stream.Collectors;

public class CityMapperDTO implements MapperDTO<City, CityDTO> {
    @Override
    public CityDTO mapToDto(City entity) {
        return new CityDTO().setId(entity.getId())
                .setMayor(entity.getMayor() == null ? null : entity.getMayor().getId())
                .setName(entity.getName())
                .setRegion(entity.getRegion()== null ? null : entity.getRegion().getId())
                .setAttributes(
                        entity.getAttributes().stream()
                                .map(AttributeCity::getId)
                                .collect(Collectors.toList())
                );
    }

    @Override
    public City mapFromDto(CityDTO dto) {
        City city = new City();
        city.setId(dto.getId());
        city.setName(dto.getName());

        if (dto.getMayor() != null) {
            Mayor mayor = new Mayor();
            mayor.setId(dto.getMayor());
            mayor.setCity(city);
            city.setMayor(mayor);
        }

        if (dto.getRegion() != null) {
            Region region = new Region();
            region.setId(dto.getRegion());
            city.setRegion(region);
        }

        List<Long> attributes = dto.getAttributes();
        if (attributes != null && !attributes.isEmpty()) {
            List<AttributeCity> attributeCities = attributes.stream()
                    .map(id -> {
                                AttributeCity attributeCity = new AttributeCity();
                                attributeCity.setId(id);
                                return attributeCity;
                            }
                    ).collect(Collectors.toList());
            city.setAttributes(attributeCities);
        }

        return city;
    }
}
