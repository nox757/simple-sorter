package ru.chibisov.app.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.chibisov.app.dto.MayorDTO;
import ru.chibisov.app.service.MayorService;

import java.util.List;

import static ru.chibisov.app.spring.util.HttpUtil.ACCEPT_JSON_UTF_8;
import static ru.chibisov.app.spring.util.HttpUtil.BASE;
import static ru.chibisov.app.spring.util.HttpUtil.BY_ID;

@RestController
@RequestMapping(value = "/mayors", headers = {ACCEPT_JSON_UTF_8})
public class MayorController {

    @Autowired
    private MayorService mayorService;

    @RequestMapping(value = BASE, method = RequestMethod.GET)
    public List<MayorDTO> getMayors() {
        return mayorService.getAll();
    }

    @RequestMapping(value = BASE, method = RequestMethod.POST)
    public MayorDTO create(@RequestBody MayorDTO mayor) {
        return mayorService.create(mayor);
    }

    @RequestMapping(value = BY_ID, method = RequestMethod.GET)
    public MayorDTO getById(@PathVariable("id") Long id) {
        return mayorService.getById(id);
    }

    @RequestMapping(value = BY_ID, method = RequestMethod.PUT)
    public MayorDTO update(@PathVariable("id") Long id, @RequestBody MayorDTO mayor) {
        mayor = mayor.setId(id);
        return mayorService.update(mayor);
    }

    @RequestMapping(value = BY_ID, method = RequestMethod.DELETE)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id) {
        MayorDTO mayor = new MayorDTO().setId(id);
        mayorService.delete(mayor);
    }
}
