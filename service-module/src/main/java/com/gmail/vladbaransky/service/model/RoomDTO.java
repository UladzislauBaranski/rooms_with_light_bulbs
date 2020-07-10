package com.gmail.vladbaransky.service.model;

import com.gmail.vladbaransky.repository.model.CountryEnum;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.util.Objects;

import static com.gmail.vladbaransky.service.constant.validation.RoomValidationMessages.*;
import static com.gmail.vladbaransky.service.constant.validation.RoomValidationRules.*;
import static com.gmail.vladbaransky.service.constant.validation.UserValidationMessages.NOT_EMPTY_MESSAGE;

public class RoomDTO {
    private Long id;
    @NotEmpty(message = NOT_EMPTY_NAME_MESSAGE)
    @Size(min = MIN_NAME_SIZE, max = MAX_NAME_SIZE, message = NAME_SIZE_MESSAGE)
    @Pattern(regexp = NAME_PATTERN, message = NAME_PATTERN_MESSAGE)
    private String name;
    @NotNull(message = NOT_EMPTY_MESSAGE)
    private CountryEnum country;
    private Boolean lightIsEnable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CountryEnum getCountry() {
        return country;
    }

    public void setCountry(CountryEnum country) {
        this.country = country;
    }

    public Boolean getLightIsEnable() {
        return lightIsEnable;
    }

    public void setLightIsEnable(Boolean lightIsEnable) {
        this.lightIsEnable = lightIsEnable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomDTO roomDTO = (RoomDTO) o;
        return Objects.equals(id, roomDTO.id) &&
                Objects.equals(name, roomDTO.name) &&
                country == roomDTO.country &&
                Objects.equals(lightIsEnable, roomDTO.lightIsEnable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, country, lightIsEnable);
    }

    @Override
    public String toString() {
        return "RoomDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country=" + country +
                ", lightIsEnable=" + lightIsEnable +
                '}';
    }
}
