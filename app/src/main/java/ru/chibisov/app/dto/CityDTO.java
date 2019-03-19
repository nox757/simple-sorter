package ru.chibisov.app.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CityDTO implements Serializable {

    @SerializedName("city_id")
    private Long id;

    private String name;

    @SerializedName("mayor_id")
    private Long mayorId;

    @SerializedName("region_id")
    private Long region;

    @SerializedName("attributes")
    private List<Long> attributes;

    public Long getId() {
        return id;
    }

    public CityDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CityDTO setName(String name) {
        this.name = name;
        return this;
    }

    public Long getMayor() {
        return mayorId;
    }

    public CityDTO setMayor(Long mayorId) {
        this.mayorId = mayorId;
        return this;
    }

    public Long getRegion() {
        return region;
    }

    public CityDTO setRegion(Long region) {
        this.region = region;
        return this;
    }

    public List<Long> getAttributes() {
        return attributes;
    }

    public CityDTO setAttributes(List<Long> attributes) {
        this.attributes = attributes;
        return this;
    }
}
