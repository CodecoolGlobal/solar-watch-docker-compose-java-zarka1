package com.codecool.solarwatch.service;

import com.codecool.solarwatch.controller.SunriseSunsetDataExistException;
import com.codecool.solarwatch.controller.SunriseSunsetDateNotAvailableException;
import com.codecool.solarwatch.model.sunrisesunset.SunriseSunsetData;
import com.codecool.solarwatch.model.sunrisesunset.SunriseSunsetResult;
import com.codecool.solarwatch.model.city.CityEntity;
import com.codecool.solarwatch.model.sunrisesunset.SunriseSunsetEntity;
import com.codecool.solarwatch.repository.SunriseSunsetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Service
public class GetSunriseSunsetService {
    private static final Logger logger = LoggerFactory.
            getLogger(GeocodingService.class);
    private final RestTemplate restTemplate;
    private final SunriseSunsetRepository sunriseSunsetRepository;
    private final GeocodingService geocodingService;

    public GetSunriseSunsetService(RestTemplate restTemplate, SunriseSunsetRepository sunriseSunsetRepository, GeocodingService geocodingService) {
        this.restTemplate = restTemplate;
        this.sunriseSunsetRepository = sunriseSunsetRepository;
        this.geocodingService = geocodingService;
    }

    public SunriseSunsetData getSunriseSunsetData(String cityName, LocalDate date) {
        CityEntity cityEntity = geocodingService.getGeoLocation(cityName);
        Optional<SunriseSunsetEntity> sunriseSunsetOptional = sunriseSunsetRepository.findByCityEntityAndDate(cityEntity, date);
        if (sunriseSunsetOptional.isPresent()) {
            logger.info("From sunrise - sunset database");
            return new SunriseSunsetData(sunriseSunsetOptional.get().getSunrise(),
                    sunriseSunsetOptional.get().getSunset());
        }
        String url = String.format("https://api.sunrise-sunset.org/json?lat=%s&lng=%s&date=%s&tzid=Europe",
               cityEntity.getLatitude(), cityEntity.getLongitude(), date);
        SunriseSunsetResult response = restTemplate.getForObject(url, SunriseSunsetResult.class);
        if (response != null) {
            SunriseSunsetEntity sunriseSunsetEntity = new SunriseSunsetEntity();
            sunriseSunsetEntity.setSunrise(response.results().sunrise());
            sunriseSunsetEntity.setSunset(response.results().sunset());
            sunriseSunsetEntity.setDate(date);
            sunriseSunsetEntity.setCityEntity(cityEntity);
            sunriseSunsetRepository.saveAndFlush(sunriseSunsetEntity);
            return new SunriseSunsetData(response.results().sunrise(),
                    response.results().sunset());
        } else throw new SunriseSunsetDateNotAvailableException();
    }

    public void deleteSunriseSunsetData(String cityName, LocalDate date) {
        CityEntity cityEntity = geocodingService.getGeoLocation(cityName);
        Optional<SunriseSunsetEntity> sunriseSunsetEntityopt = sunriseSunsetRepository.findByCityEntityAndDate(cityEntity, date);
        if(sunriseSunsetEntityopt.isPresent()) {
            sunriseSunsetRepository.delete(sunriseSunsetEntityopt.get());
        } else throw new SunriseSunsetDateNotAvailableException();
    }

    public void updateSunriseSunsetData(String cityName, LocalDate date,
                                        String sunrise, String sunset ) {
        CityEntity cityEntity = geocodingService.getGeoLocation(cityName);
        Optional<SunriseSunsetEntity> sunriseSunsetEntityopt = sunriseSunsetRepository.findByCityEntityAndDate(cityEntity, date);
        if(sunriseSunsetEntityopt.isPresent()) {
            SunriseSunsetEntity sunriseSunsetEntity = sunriseSunsetEntityopt.get();
            if (!Objects.equals(sunrise, "")) sunriseSunsetEntity.setSunrise(sunrise);
            if (!Objects.equals(sunset, "")) sunriseSunsetEntity.setSunset(sunset);
            sunriseSunsetRepository.saveAndFlush(sunriseSunsetEntity);
        } else throw new SunriseSunsetDateNotAvailableException();
    }

    public void createSunriseSunsetData(String cityName, LocalDate date,
                                        String sunrise, String sunset ) {
        CityEntity cityEntity = geocodingService.getGeoLocation(cityName);
        Optional<SunriseSunsetEntity> sunriseSunsetEntityopt = sunriseSunsetRepository.findByCityEntityAndDate(cityEntity, date);
        if(sunriseSunsetEntityopt.isPresent()) {
            throw new SunriseSunsetDataExistException();
        }
            SunriseSunsetEntity sunriseSunsetEntity = new SunriseSunsetEntity();
            sunriseSunsetEntity.setSunrise(sunrise);
            sunriseSunsetEntity.setSunset(sunset);
            sunriseSunsetEntity.setCityEntity(cityEntity);
            sunriseSunsetEntity.setDate(date);
            sunriseSunsetRepository.saveAndFlush(sunriseSunsetEntity);

    }
}
