package ru.chibisov.app.servicies;

import ru.chibisov.app.dto.CountryDTO;

import java.util.List;

public interface CountryService {

    List<CountryDTO> getAll();
    CountryDTO getById(Long id);
    CountryDTO update(CountryDTO country);
    CountryDTO create(CountryDTO country);
    void delete(CountryDTO country);
}
