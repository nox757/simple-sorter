package ru.chibisov.app.dto.mapper;

import entities.AttributeType;
import ru.chibisov.app.dto.AttributeTypeDTO;

public class AttributeTypeMapperDTO implements MapperDTO<AttributeType, AttributeTypeDTO> {

    @Override
    public AttributeTypeDTO mapToDto(AttributeType entity) {
        return new AttributeTypeDTO().setId(entity.getId())
                .setName(entity.getName());
    }

    @Override
    public AttributeType mapFromDto(AttributeTypeDTO dto) {
        AttributeType attributeType = new AttributeType();
        attributeType.setId(dto.getId());
        attributeType.setName(dto.getName());
        return attributeType;
    }

}
