package ru.chibisov.app.dto;

import com.google.gson.annotations.SerializedName;

public class RegionDTO {

    @SerializedName("region_id")
    private Long id;

    private String name;

    @SerializedName("country_id")
    private Long countryId;

    public Long getId() {
        return id;
    }

    public RegionDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RegionDTO setName(String name) {
        this.name = name;
        return this;
    }

    public Long getCountryId() {
        return countryId;
    }

    public RegionDTO setCountryId(Long countryId) {
        this.countryId = countryId;
        return this;
    }
}
