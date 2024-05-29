package com.codecool.solarwatch.controller;

public class SunriseSunsetDataExistException extends RuntimeException{
    public SunriseSunsetDataExistException(){
        super(" Data for city and date already exists");
    }
}
