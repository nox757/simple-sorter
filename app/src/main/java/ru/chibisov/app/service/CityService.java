package ru.chibisov.app.service;

import ru.chibisov.app.dto.AttributeDTO;
import ru.chibisov.app.dto.CityDTO;

import java.util.List;

/**
 * Contains methods to work with cities
 */
public interface CityService {

    /**
     * Return list of all cities
     * @return
     */
    List<CityDTO> getAll();

    /**
     * Find city by ID
     * @param id
     * @return
     */
    CityDTO getById(Long id);

    /**
     * Update fields of city
     * @param city dto of city with new/old value
     * @return
     */
    CityDTO update(CityDTO city);

    /**
     * Create new city
     * @param city city's dto
     * @return
     */
    CityDTO create(CityDTO city);

    /**
     * Delete existing city
     * @param city city's dto
     */
    void delete(CityDTO city);

    /**
     *  Get all attributes of city
     * @param id city ID
     * @return
     */
    List<AttributeDTO> getCityAttributes(Long id);

    /**
     * Add attribute to city
     * @param cityId city ID
     * @param attributeId attribute ID
     * @return
     */
    CityDTO addCityAttribute(Long cityId, Long attributeId);
}
