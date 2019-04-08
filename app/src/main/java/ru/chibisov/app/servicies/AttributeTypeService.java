package ru.chibisov.app.servicies;

import ru.chibisov.app.dto.AttributeTypeDTO;
import ru.chibisov.app.dto.RegionDTO;

import java.util.List;

/**
 * Contains methods to work with type of city's attributes
 */
public interface AttributeTypeService {

    /**
     * Return all types of attribute
     * @return
     */
    List<AttributeTypeDTO> getAll();

    /**
     * Find type attribute by ID
     * @param id
     * @return
     */
    AttributeTypeDTO getById(Long id);

    /**
     * Update fields existing type of attribute
     * @param attributeType dto attribute type
     * @return
     */
    AttributeTypeDTO update(AttributeTypeDTO attributeType);

    /**
     * Create new type of attribute
     * @param attributeType dto type of attribute
     * @return
     */
    AttributeTypeDTO create(AttributeTypeDTO attributeType);

    /**
     * Delete existing type of attribute
     * @param attributeType
     */
    void delete(AttributeTypeDTO attributeType);
}
