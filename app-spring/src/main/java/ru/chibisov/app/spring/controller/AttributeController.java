package ru.chibisov.app.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.chibisov.app.dto.AttributeDTO;
import ru.chibisov.app.service.AttributeService;

import java.util.List;

import static ru.chibisov.app.spring.util.HttpUtil.ACCEPT_JSON_UTF_8;
import static ru.chibisov.app.spring.util.HttpUtil.BASE;
import static ru.chibisov.app.spring.util.HttpUtil.BY_ID;

@RestController
@RequestMapping(value = "/attributes", headers = {ACCEPT_JSON_UTF_8})
public class AttributeController {

    @Autowired
    private AttributeService attributeService;

    @RequestMapping(value = BASE, method = RequestMethod.GET)
    public List<AttributeDTO> getAttributes() {
        return attributeService.getAll();
    }

    @RequestMapping(value = BASE, method = RequestMethod.POST)
    public AttributeDTO create(@RequestBody AttributeDTO attribute) {
       return attributeService.create(attribute);
    }

    @RequestMapping(value = BY_ID, method = RequestMethod.GET)
    public AttributeDTO getById(@PathVariable("id") Long id) {
        return attributeService.getById(id);
    }

    @RequestMapping(value = BY_ID, method = RequestMethod.PUT)
    public AttributeDTO update(@PathVariable("id") Long id, @RequestBody AttributeDTO attribute) {
        attribute = attribute.setId(id);
        return attributeService.update(attribute);
    }

    @RequestMapping(value = BY_ID, method = RequestMethod.DELETE)
    public void deleteById(@PathVariable("id") Long id) {
        AttributeDTO attribute = new AttributeDTO().setId(id);
        attributeService.delete(attribute);
    }
}
