package com.citybreak.trip_service.repository;

import com.citybreak.trip_service.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City,Long> {
    boolean existsByCityName(String cityName);
    Optional<City> findByCityNameAndCountry(String cityName, String country);
}
