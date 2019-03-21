package ru.chibisov.app.servicies;

import ru.chibisov.app.dto.MayorDTO;

import java.util.List;

public interface MayorService {

    List<MayorDTO> getAll();
    MayorDTO getById(Long id);
    MayorDTO update(MayorDTO mayor);
    MayorDTO create(MayorDTO mayor);
    void delete(MayorDTO mayor);
}
