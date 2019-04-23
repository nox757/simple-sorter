package ru.chibisov.app.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.chibisov.app.dto.AttributeDTO;
import ru.chibisov.app.dto.CityDTO;
import ru.chibisov.app.service.CityService;
import ru.chibisov.app.spring.exception.NotFoundException;

import java.util.List;

import static ru.chibisov.app.spring.util.HttpUtil.ACCEPT_JSON_UTF_8;
import static ru.chibisov.app.spring.util.HttpUtil.BASE;
import static ru.chibisov.app.spring.util.HttpUtil.BY_ID;

@RestController
@RequestMapping(value = "/cities", headers = {ACCEPT_JSON_UTF_8})
public class CityController {

    public static final String ATTRIBUTES = BY_ID + "/attributes";
    public static final String ADD_ATTRIBUTES = BY_ID + "/attributes/{attribute_id}";

    @Autowired
    private CityService cityService;

    @RequestMapping(value = BASE, method = RequestMethod.GET)
    public List<CityDTO> getCities() {
        return cityService.getAll();
    }

    @RequestMapping(value = BASE, method = RequestMethod.POST)
    public CityDTO create(@RequestBody CityDTO city) {
        return cityService.create(city);
    }

    @RequestMapping(value = BY_ID, method = RequestMethod.GET)
    public CityDTO getById(@PathVariable("id") Long id) {
        CityDTO city = cityService.getById(id);
        if (city == null) {
            throw new NotFoundException("Not found city by id: " + id);
        }
        return city;
    }

    @RequestMapping(value = BY_ID, method = RequestMethod.PUT)
    public CityDTO update(@PathVariable("id") Long id, @RequestBody CityDTO city) {
        city = city.setId(id);
        return cityService.update(city);
    }

    @RequestMapping(value = BY_ID, method = RequestMethod.DELETE)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id) {
        CityDTO city = new CityDTO().setId(id);
        cityService.delete(city);
    }

    @RequestMapping(value = ATTRIBUTES, method = RequestMethod.GET)
    public List<AttributeDTO> getCities(@PathVariable("id") Long id) {
        return cityService.getCityAttributes(id);
    }

    @RequestMapping(value = ADD_ATTRIBUTES, method = RequestMethod.PUT)
    public CityDTO getCities(@PathVariable("id") Long id, @PathVariable("attribute_id") Long attributeId) {
        return cityService.addCityAttribute(id, attributeId);
    }
}
