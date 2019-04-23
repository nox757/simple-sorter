package ru.chibisov.app.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.chibisov.app.dto.RegionDTO;
import ru.chibisov.app.service.RegionService;

import java.util.List;

import static ru.chibisov.app.spring.util.HttpUtil.ACCEPT_JSON_UTF_8;
import static ru.chibisov.app.spring.util.HttpUtil.BASE;
import static ru.chibisov.app.spring.util.HttpUtil.BY_ID;

@RestController
@RequestMapping(value = "/regions", headers = { ACCEPT_JSON_UTF_8 })
public class RegionController {

    @Autowired
    private RegionService regionService;

    @RequestMapping(value = BASE, method = RequestMethod.GET)
    public List<RegionDTO> getRegions() {
        return regionService.getAll();
    }

    @RequestMapping(value = BASE, method = RequestMethod.POST)
    public RegionDTO create(@RequestBody RegionDTO region) {
        return regionService.create(region);
    }

    @RequestMapping(value = BY_ID, method = RequestMethod.GET)
    public RegionDTO getById(@PathVariable("id") Long id) {
        return regionService.getById(id);
    }

    @RequestMapping(value = BY_ID, method = RequestMethod.PUT)
    public RegionDTO update(@PathVariable("id") Long id, @RequestBody RegionDTO region) {
        region = region.setId(id);
        return regionService.update(region);
    }

    @RequestMapping(value = BY_ID, method = RequestMethod.DELETE)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id) {
        RegionDTO region = new RegionDTO().setId(id);
        regionService.delete(region);
    }
}
