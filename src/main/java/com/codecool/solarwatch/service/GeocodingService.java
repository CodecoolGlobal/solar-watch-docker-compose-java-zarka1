package com.codecool.solarwatch.service;

import com.codecool.solarwatch.controller.CityExistException;
import com.codecool.solarwatch.controller.CityNotFoundException;
import com.codecool.solarwatch.model.city.City;
import com.codecool.solarwatch.model.city.CityEntity;
import com.codecool.solarwatch.repository.CityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;

@Service
public class GeocodingService {
    private static final Logger logger = LoggerFactory.
            getLogger(GeocodingService.class);

    private final RestTemplate restTemplate;
    private final CityRepository cityRepository;

    public GeocodingService(RestTemplate restTemplate, CityRepository cityRepository) {
        this.restTemplate = restTemplate;
        this.cityRepository = cityRepository;
    }

    public CityEntity getGeoLocation(String city) {
        String url = String.format("http://api.openweathermap.org/geo/1.0/direct?q=%s&limit=1&appid=44c27d64d86f8a137a50a3b8e5d188b8", city);
        if (cityRepository.findByName(city).isPresent()){
            logger.info("From geolocation database");
            return cityRepository.findByName(city).get();
        }
        City[] cities = restTemplate.getForObject(url, City[].class);
        if (cities != null && cities.length > 0) {
            CityEntity cityEntity = new CityEntity();
            cityEntity.setName(city);
            cityEntity.setLatitude(cities[0].lat());
            cityEntity.setLongitude(cities[0].lon());
            cityEntity.setCountry(cities[0].country());
            cityEntity.setState(cities[0].state());
            cityRepository.saveAndFlush(cityEntity);
            return cityEntity;
        } else throw new CityNotFoundException(city);
    }

    public void deleteGeoLocation(String city) {
        if (cityRepository.findByName(city).isPresent()){
            cityRepository.delete(cityRepository.findByName(city).get());
        }
        else throw new CityNotFoundException(city);
    }

    public void updateCity(City city) {
        Optional<CityEntity> cityEntityOpt = cityRepository.findByName(city.name());
        if (cityEntityOpt.isEmpty()){
            throw new CityNotFoundException(city.name());
        }
        CityEntity cityEntity = cityEntityOpt.get();
        if (city.lat() != 100) cityEntity.setLatitude(city.lat());
        if (city.lon() != 100) cityEntity.setLongitude(city.lon());
        if (!Objects.equals(city.country(), "")) cityEntity.setCountry(city.country());
        if (!Objects.equals(city.state(), "")) cityEntity.setState(city.country());
        cityRepository.save(cityEntity);
    }

    public void createCity(City city) {
        Optional<CityEntity> cityEntityOpt = cityRepository.findByName(city.name());
        if (cityEntityOpt.isPresent()){
            throw new CityExistException(city.name());
        }
        CityEntity cityEntity = new CityEntity();
        cityEntity.setName(city.name());
        cityEntity.setLatitude(city.lat());
        cityEntity.setLongitude(city.lon());
        cityEntity.setState(city.state());
        cityEntity.setCountry(city.country());
        cityRepository.save(cityEntity);
    }

}
