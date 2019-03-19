package ru.chibisov.app.dto;

import com.google.gson.annotations.SerializedName;

public class AttributeTypeDTO {

    @SerializedName("attribute_type_id")
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public AttributeTypeDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AttributeTypeDTO setName(String name) {
        this.name = name;
        return this;
    }
}
