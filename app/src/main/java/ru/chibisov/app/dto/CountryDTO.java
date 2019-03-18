package ru.chibisov.app.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CountryDTO implements Serializable {


    @SerializedName("country_id")
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public CountryDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CountryDTO setName(String name) {
        this.name = name;
        return this;
    }

}
