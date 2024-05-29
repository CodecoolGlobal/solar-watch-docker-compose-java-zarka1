package com.codecool.solarwatch.controller;

public class CityExistException extends RuntimeException {
    public CityExistException(String city){
        super(city + " already exists");
    }
}
