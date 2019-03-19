package ru.chibisov.app.servicies;

import ru.chibisov.app.dto.RegionDTO;

import java.util.List;

public interface RegionService {

    List<RegionDTO> getAll();
    RegionDTO getById(Long id);
    RegionDTO update(RegionDTO region);
    RegionDTO create(RegionDTO region);
    void delete(RegionDTO region);

}
