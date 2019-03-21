package ru.chibisov.app.servicies;

import ru.chibisov.app.dto.AttributeDTO;

import java.util.List;

public interface AttributeService {

    List<AttributeDTO> getAll();
    AttributeDTO getById(Long id);
    AttributeDTO update(AttributeDTO attribute);
    AttributeDTO create(AttributeDTO attribute);
    void delete(AttributeDTO attribute);
}
