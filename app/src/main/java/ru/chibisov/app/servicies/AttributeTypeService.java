package ru.chibisov.app.servicies;

import ru.chibisov.app.dto.AttributeTypeDTO;
import ru.chibisov.app.dto.RegionDTO;

import java.util.List;

public interface AttributeTypeService {

    List<AttributeTypeDTO> getAll();
    AttributeTypeDTO getById(Long id);
    AttributeTypeDTO update(AttributeTypeDTO attributeType);
    AttributeTypeDTO create(AttributeTypeDTO attributeType);
    void delete(AttributeTypeDTO attributeType);
}
