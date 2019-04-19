package ru.chibisov.app.service;

import ru.chibisov.app.dto.AttributeDTO;

import java.util.List;

/**
 * Contains methods of city's attributes
 */
public interface AttributeService {

    /**
     * Get all attributes
     * @return
     */
    List<AttributeDTO> getAll();

    /**
     * Find attribute by ID
     * @param id id of city
     * @return
     */
    AttributeDTO getById(Long id);

    /**
     * Update fields of attribute
     * @param attribute dto of city attribute
     * @return
     */
    AttributeDTO update(AttributeDTO attribute);

    /**
     * Create new attribute
     * @param attribute dto of city attribute
     * @return
     */
    AttributeDTO create(AttributeDTO attribute);

    /**
     * Delete existing city's attribute
     * @param attribute
     */
    void delete(AttributeDTO attribute);
}
