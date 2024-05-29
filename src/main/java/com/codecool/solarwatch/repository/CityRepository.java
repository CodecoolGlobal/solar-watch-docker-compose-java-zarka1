package com.codecool.solarwatch.repository;

import com.codecool.solarwatch.model.city.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository <CityEntity, Long> {
     Optional<CityEntity> findByName(String name);
}
