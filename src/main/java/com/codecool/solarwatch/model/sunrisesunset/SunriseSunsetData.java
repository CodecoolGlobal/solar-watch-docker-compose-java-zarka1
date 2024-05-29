package com.codecool.solarwatch.model.sunrisesunset;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SunriseSunsetData(String sunrise, String sunset) {
}
