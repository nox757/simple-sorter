package ru.chibisov.app.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.chibisov.app.dto.AttributeTypeDTO;
import ru.chibisov.app.service.AttributeTypeService;

import java.util.List;

import static ru.chibisov.app.spring.util.HttpUtil.ACCEPT_JSON_UTF_8;
import static ru.chibisov.app.spring.util.HttpUtil.BASE;
import static ru.chibisov.app.spring.util.HttpUtil.BY_ID;

@RestController
@RequestMapping(value = "/attributetypes", headers = {ACCEPT_JSON_UTF_8})
public class AttributeTypeController {

    @Autowired
    private AttributeTypeService attributeTypeService;

    @RequestMapping(value = BASE, method = RequestMethod.GET)
    public List<AttributeTypeDTO> getAttributeTypes() {
        return attributeTypeService.getAll();
    }

    @RequestMapping(value = BASE, method = RequestMethod.POST)
    public AttributeTypeDTO create(@RequestBody AttributeTypeDTO attributeType) {
        return attributeTypeService.create(attributeType);
    }

    @RequestMapping(value = BY_ID, method = RequestMethod.GET)
    public AttributeTypeDTO getById(@PathVariable("id") Long id) {
        return attributeTypeService.getById(id);
    }

    @RequestMapping(value = BY_ID, method = RequestMethod.PUT)
    public AttributeTypeDTO update(@PathVariable("id") Long id, @RequestBody AttributeTypeDTO attributeType) {
        attributeType = attributeType.setId(id);
        return attributeTypeService.update(attributeType);
    }

    @RequestMapping(value = BY_ID, method = RequestMethod.DELETE)
    public void deleteById(@PathVariable("id") Long id) {
        AttributeTypeDTO attributeType = new AttributeTypeDTO().setId(id);
        attributeTypeService.delete(attributeType);
    }
}
