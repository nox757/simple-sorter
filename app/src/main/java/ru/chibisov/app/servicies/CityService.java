package ru.chibisov.app.servicies;

import ru.chibisov.app.dto.AttributeDTO;
import ru.chibisov.app.dto.CityDTO;

import java.util.List;

public interface CityService {

    List<CityDTO> getAll();
    CityDTO getById(Long id);
    CityDTO update(CityDTO city);
    CityDTO create(CityDTO city);
    void delete(CityDTO city);
    List<AttributeDTO> getCityAttributes(Long id);
}
