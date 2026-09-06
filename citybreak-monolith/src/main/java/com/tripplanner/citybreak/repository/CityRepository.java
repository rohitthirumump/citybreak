package com.tripplanner.citybreak.repository;

import com.tripplanner.citybreak.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City,Long> {
    boolean existsByCityName(String cityName);
    Optional<City> findByCityNameAndCountry(String cityName, String country);
}
