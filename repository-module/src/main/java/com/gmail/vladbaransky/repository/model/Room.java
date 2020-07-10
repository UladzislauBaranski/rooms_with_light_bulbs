package com.gmail.vladbaransky.repository.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "country")
    private CountryEnum country;
    @Column(name = "light_is_enable")
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
        Room room = (Room) o;
        return Objects.equals(id, room.id) &&
                Objects.equals(name, room.name) &&
                country == room.country &&
                Objects.equals(lightIsEnable, room.lightIsEnable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, country, lightIsEnable);
    }
}
