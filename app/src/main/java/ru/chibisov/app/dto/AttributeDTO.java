package ru.chibisov.app.dto;

import com.google.gson.annotations.SerializedName;

public class AttributeDTO {

    @SerializedName("attribute_id")
    private Long id;

    private String name;

    private String value;

    @SerializedName("attribute_type_id")
    private Long attributeType;

    public Long getId() {
        return id;
    }

    public AttributeDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AttributeDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getValue() {
        return value;
    }

    public AttributeDTO setValue(String value) {
        this.value = value;
        return this;
    }

    public Long getAttributeType() {
        return attributeType;
    }

    public AttributeDTO setAttributeType(Long attributeType) {
        this.attributeType = attributeType;
        return this;
    }
}
