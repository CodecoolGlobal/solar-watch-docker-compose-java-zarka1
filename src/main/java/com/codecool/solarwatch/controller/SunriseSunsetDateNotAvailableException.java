package com.codecool.solarwatch.controller;

public class SunriseSunsetDateNotAvailableException extends RuntimeException{
    public SunriseSunsetDateNotAvailableException() {
        super("Sunrise - sunset data not found.");
    }
}
