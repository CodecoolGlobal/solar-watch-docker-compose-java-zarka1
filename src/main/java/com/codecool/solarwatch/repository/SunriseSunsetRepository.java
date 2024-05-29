package com.codecool.solarwatch.repository;

import com.codecool.solarwatch.model.sunrisesunset.SunriseSunsetEntity;
import com.codecool.solarwatch.model.city.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface SunriseSunsetRepository extends JpaRepository<SunriseSunsetEntity, Long> {
    Optional<SunriseSunsetEntity> findByCityEntityAndDate(CityEntity cityEntity, LocalDate date);
}
