package ru.chibisov.app.dto;

import com.google.gson.annotations.SerializedName;

public class MayorDTO {

    @SerializedName("mayor_id")
    private Long id;

    private String fio;

    @SerializedName("city_id")
    private Long cityId;

    public Long getId() {
        return id;
    }

    public MayorDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFio() {
        return fio;
    }

    public MayorDTO setFio(String fio) {
        this.fio = fio;
        return this;
    }

    public Long getCityId() {
        return cityId;
    }

    public MayorDTO setCityId(Long cityId) {
        this.cityId = cityId;
        return this;
    }
}
