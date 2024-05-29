package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.city.City;
import com.codecool.solarwatch.repository.SunriseSunsetRepository;
import com.codecool.solarwatch.service.GeocodingService;
import com.codecool.solarwatch.service.GetSunriseSunsetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class SunriseSunsetController {
    private static final String defaultCity = "Budapest";
    private final GeocodingService geocodingService;
    private final GetSunriseSunsetService getSunriseSunsetService;
    private static final Logger loggerGeoCoding = LoggerFactory.
            getLogger(GeocodingService.class);
    private static final Logger loggerSunset = LoggerFactory.
            getLogger(GeocodingService.class);
    private final SunriseSunsetRepository sunriseSunsetRepository;


    public SunriseSunsetController(GeocodingService geocodingService, GetSunriseSunsetService getSunriseSunsetService, SunriseSunsetRepository sunriseSunsetRepository) {
        this.geocodingService = geocodingService;
        this.getSunriseSunsetService = getSunriseSunsetService;
        this.sunriseSunsetRepository = sunriseSunsetRepository;
    }

    @GetMapping("api/suncalculator")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getSunriseSunset(@RequestParam(defaultValue = defaultCity) String city,
                                              @RequestParam (defaultValue = "2024-01-01") String date) {
        LocalDate dateParam = null;
        try {
            dateParam = LocalDate.parse(date);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Date parameter is invalid.");
        }
        return ok(getSunriseSunsetService.getSunriseSunsetData(
                city, dateParam));
    }

    @GetMapping("/city")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getCity(@RequestParam(defaultValue = defaultCity) String city) {
        return ok(geocodingService.getGeoLocation(city));
    }

    @DeleteMapping("/city")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteCity(@RequestParam(defaultValue = defaultCity) String city) {
        geocodingService.deleteGeoLocation(city);
        return ok("City " + city + " deleted.");
    }

    @DeleteMapping("/suncalculator")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteSunriseSunset(@RequestParam(defaultValue = defaultCity) String city,
                                                 @RequestParam String date) {
        LocalDate dateParam = null;
        try {
            dateParam = LocalDate.parse(date);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Date parameter is invalid.");
        }
        getSunriseSunsetService.deleteSunriseSunsetData(city, dateParam);
        return ok("City " + city + ": sunrise and sunset data for " + date + " are deleted.");
    }

    @PatchMapping("/city")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateCity(@RequestParam String city, @RequestParam(defaultValue = "100")
    String latitude, @RequestParam(defaultValue = "100") String longitude,
                                        @RequestParam(defaultValue = "") String country,
                                        @RequestParam(defaultValue = "") String state) {
        City newCity = new City(Integer.parseInt(latitude),
                Integer.parseInt(longitude), city, country, state);
        geocodingService.updateCity(newCity);
        return (ok("City " + city + " updated successfully"));
    }

    @PostMapping("/city")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createCity(@RequestParam String city, @RequestParam String lat,
                                        @RequestParam String lon,
                                        @RequestParam String country,
                                        @RequestParam String state) {
        City newCity = new City(Integer.parseInt(lat),
                Integer.parseInt(lon), city, country, state);
        geocodingService.createCity(newCity);
        return (ok("City " + city + "created successfully"));
    }

    @PatchMapping("/suncalculator")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> patchSunriseSunset( @RequestParam String city,
            @RequestParam(defaultValue = "") String sunrise,
                                                 @RequestParam (defaultValue = "") String sunset,
                                                 @RequestParam String date)
    {
        LocalDate dateParam = null;
        try {
            dateParam = LocalDate.parse(date);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Date parameter is invalid.");
        }
        getSunriseSunsetService.updateSunriseSunsetData(city, dateParam, sunrise,
                sunset);
        return ok("City " + city + ": sunrise and sunset data for " + date + " are updated.");
    }

    @PostMapping("/suncalculator")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> postSunriseSunset( @RequestParam String city,
                                                 @RequestParam String sunrise,
                                                 @RequestParam String sunset,
                                                 @RequestParam String date)
    {
        LocalDate dateParam = null;
        try {
            dateParam = LocalDate.parse(date);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Date parameter is invalid.");
        }
        getSunriseSunsetService.createSunriseSunsetData(city, dateParam, sunrise,
                sunset);
        return ok("City " + city + ": sunrise and sunset data for " + date + " are created.");
    }
}
