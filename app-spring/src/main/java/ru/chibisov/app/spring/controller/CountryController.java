package ru.chibisov.app.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.chibisov.app.dto.CountryDTO;
import ru.chibisov.app.service.CountryService;

import java.util.List;

import static ru.chibisov.app.spring.util.HttpUtil.ACCEPT_JSON_UTF_8;
import static ru.chibisov.app.spring.util.HttpUtil.BASE;
import static ru.chibisov.app.spring.util.HttpUtil.BY_ID;

@RestController
@RequestMapping(value = "/countries", headers = { ACCEPT_JSON_UTF_8 })
public class CountryController {

    @Autowired
    private CountryService countryService;

    @RequestMapping(value = BASE, method = RequestMethod.GET)
    public List<CountryDTO> getCountries() {
        return countryService.getAll();
    }

    @RequestMapping(value = BASE, method = RequestMethod.POST)
    public CountryDTO create(@RequestBody CountryDTO country) {
        return countryService.create(country);
    }

    @RequestMapping(value = BY_ID, method = RequestMethod.GET)
    public CountryDTO getById(@PathVariable("id") Long id) {
        return countryService.getById(id);
    }

    @RequestMapping(value = BY_ID, method = RequestMethod.PUT)
    public CountryDTO update(@PathVariable("id") Long id, @RequestBody CountryDTO country) {
        country = country.setId(id);
        return countryService.update(country);
    }

    @RequestMapping(value = BY_ID, method = RequestMethod.DELETE)
    public void deleteById(@PathVariable("id") Long id) {
        CountryDTO country = new CountryDTO().setId(id);
        countryService.delete(country);
    }
}
