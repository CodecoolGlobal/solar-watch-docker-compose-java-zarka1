package com.codecool.solarwatch.model.city;

import com.codecool.solarwatch.model.sunrisesunset.SunriseSunsetEntity;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class CityEntity {
    @GeneratedValue
    @Id
    private Long id;
    private String name;
    private float latitude;
    private float longitude;
    private String country;
    private String state;
    @OneToMany (mappedBy = "cityEntity", cascade = CascadeType.ALL)
    private List<SunriseSunsetEntity> sunriseSunsetData;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
