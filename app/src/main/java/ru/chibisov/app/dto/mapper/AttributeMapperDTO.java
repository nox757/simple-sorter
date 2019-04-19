package ru.chibisov.app.dto.mapper;

import entities.AttributeCity;
import entities.AttributeType;
import ru.chibisov.app.dto.AttributeDTO;

public class AttributeMapperDTO implements MapperDTO<AttributeCity, AttributeDTO> {
    @Override
    public AttributeDTO mapToDto(AttributeCity entity) {
        return new AttributeDTO().setId(entity.getId())
                .setName(entity.getName())
                .setValue(entity.getValue())
                .setAttributeType(entity.getAttributeType().getId());
    }

    @Override
    public AttributeCity mapFromDto(AttributeDTO dto) {
        AttributeCity attributeCity = new AttributeCity();
        attributeCity.setId(dto.getId());
        attributeCity.setName(dto.getName());
        attributeCity.setValue(dto.getValue());

        AttributeType attributeType = new AttributeType();
        attributeType.setId(dto.getAttributeType());
        attributeCity.setAttributeType(attributeType);
        return attributeCity;
    }
}
