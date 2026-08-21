package com.tripplanner.citybreak.service;

import com.tripplanner.citybreak.dto.CityRequest;
import com.tripplanner.citybreak.dto.CityResponse;
import com.tripplanner.citybreak.entity.City;
import com.tripplanner.citybreak.repository.CityRepository;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public CityResponse addCity(CityRequest request){
        if(cityRepository.existsByCityName(request.getCityName())){
            throw new IllegalStateException("City Already Exist, Please update the existing city description");
        }

        City city = new City();
        city.setCityName(request.getCityName());
        city.setCountry(request.getCountry());
        city.setDetails(request.getDetails());
        city.setVisited(request.getVisited());

        City saved = cityRepository.save(city);

        return new CityResponse(saved.getId(),saved.getCityName(), saved.getCountry(),saved.getDetails());
    }

    public CityResponse updateCity(Long id, CityRequest request){
        City city = cityRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("City Not found for id: " + id));

        city.setCityName(request.getCityName());
        city.setCountry(request.getCountry());
        city.setDetails(request.getDetails());
        city.setVisited(request.getVisited());

        City updated = cityRepository.save(city);
        return new CityResponse(updated.getId(),updated.getCityName(), updated.getCountry(),updated.getDetails());
    }
}
