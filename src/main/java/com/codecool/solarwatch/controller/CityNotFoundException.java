package com.codecool.solarwatch.controller;

public class CityNotFoundException extends RuntimeException{
    public CityNotFoundException(String city){
        super(city + " not found.");
    }
}
