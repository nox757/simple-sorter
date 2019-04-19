package ru.chibisov.app.dto.mapper;

import entities.City;
import entities.Mayor;
import ru.chibisov.app.dto.MayorDTO;

public class MayorMapperDTO implements MapperDTO<Mayor, MayorDTO> {
    @Override
    public MayorDTO mapToDto(Mayor entity) {
        return new MayorDTO().setId(entity.getId())
                .setFio(entity.getFio())
                .setCityId(entity.getCity() == null ? null : entity.getCity().getId());
    }

    @Override
    public Mayor mapFromDto(MayorDTO dto) {
        Mayor mayor = new Mayor();
        mayor.setId(dto.getId());
        mayor.setFio(dto.getFio());

        City city = new City();
        city.setId(dto.getCityId());
        mayor.setCity(city);
        return mayor;
    }
}
