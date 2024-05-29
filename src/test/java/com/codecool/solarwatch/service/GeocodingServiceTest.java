/*package com.codecool.solarwatch.service;

import com.codecool.solarwatch.model.city.City;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GeocodingServiceTest {

    @Test
    void testIfGetGeoLocationForGivenCity() {
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        String url = "http://api.openweathermap.org/geo/1.0/direct?q=Budapest&limit=1&appid=44c27d64d86f8a137a50a3b8e5d188b8";
        City[] cities = new City[1];
        cities[0] = new City(
                13, 10
        );
        when(restTemplate.getForObject(url, City[].class)).thenReturn(cities);
        GeocodingService service = new GeocodingService(restTemplate);
        assertTrue(service.getGeoLocation("Budapest").equals(new City(
                13, 10
        )));
    }

    @Test
    void testIfGetGeoLocationForArrayWithNullThrowsRuntimeException() {
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        String url = "http://api.openweathermap.org/geo/1.0/direct?q=Budapest&limit=1&appid=44c27d64d86f8a137a50a3b8e5d188b8";
        City[] cities = new City[1];
        System.out.println(cities.length);
        System.out.println(cities[0]);
        when(restTemplate.getForObject(url, City[].class)).thenReturn(cities);
        GeocodingService service = new GeocodingService(restTemplate);
        assertThrows(RuntimeException.class, () -> service.getGeoLocation("Budapest"));
    }

    @Test
    void testIfGetGeoLocationForArrayWithEmptyArrayThrowsRuntimeException() {
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        String url = "http://api.openweathermap.org/geo/1.0/direct?q=Budapest&limit=1&appid=44c27d64d86f8a137a50a3b8e5d188b8";
        City[] cities = new City[0];
        System.out.println(cities.length);
        when(restTemplate.getForObject(url, City[].class)).thenReturn(cities);
        GeocodingService service = new GeocodingService(restTemplate);
        assertThrows(RuntimeException.class, () -> service.getGeoLocation("Budapest"));
    }

    @Test
    void testIfGetGeoLocationForNullThrowsRuntimeException() {
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        String url = "http://api.openweathermap.org/geo/1.0/direct?q=Budapest&limit=1&appid=44c27d64d86f8a137a50a3b8e5d188b8";
        City[] cities = null;
        when(restTemplate.getForObject(url, City[].class)).thenReturn(cities);
        GeocodingService service = new GeocodingService(restTemplate);
        assertThrows(RuntimeException.class, () -> service.getGeoLocation("Budapest"));
    }

    @Test
    void testIfGetGeoLocationForDamagedResponseThrowsRuntimeException() {
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        String url = "http://api.openweathermap.org/geo/1.0/direct?q=Budapest&limit=1&appid=44c27d64d86f8a137a50a3b8e5d188b8";
        String[] cities = new String[1];
        cities[0] = "hi";
        when(restTemplate.getForObject(url, String[].class)).thenReturn(cities);
        GeocodingService service = new GeocodingService(restTemplate);
        assertThrows(RuntimeException.class, () -> service.getGeoLocation("Budapest"));
    }
}*/
