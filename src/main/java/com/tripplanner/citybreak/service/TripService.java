package com.tripplanner.citybreak.service;

import com.tripplanner.citybreak.dto.TripRequest;
import com.tripplanner.citybreak.dto.TripResponse;
import com.tripplanner.citybreak.entity.City;
import com.tripplanner.citybreak.entity.Trip;
import com.tripplanner.citybreak.entity.User;
import com.tripplanner.citybreak.repository.CityRepository;
import com.tripplanner.citybreak.repository.TripRepository;
import com.tripplanner.citybreak.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class TripService {

    private final TripRepository tripRepository;
    private final CityRepository cityRepository;
    private final UserRepository userRepository;

    public TripService(TripRepository tripRepository, CityRepository cityRepository, UserRepository userRepository) {
        this.tripRepository = tripRepository;
        this.cityRepository = cityRepository;
        this.userRepository = userRepository;
    }

    public TripResponse createTrip(Long userId,TripRequest request){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("User does not exist " + userId));

        City city = cityRepository.findByCityNameAndCountry(
                request.getCityName(), request.getCountry()).orElseGet(() -> cityRepository.save(
                        new City(null,request.getCityName(),request.getCountry())));

        Trip trip = new Trip();
        trip.setUser(user);
        trip.setCity(city);
        trip.setDescription(request.getDescription());
        trip.setStatus(request.getStatus());
        trip.setStartDate(request.getStartDate());
        trip.setEndDate(request.getEndDate());

        Trip saved = tripRepository.save(trip);

        return new TripResponse(saved.getId(),saved.getCity().getCityName(),saved.getDescription()
                ,saved.getStatus(),saved.getStartDate(),saved.getEndDate(),saved.getCreatedAt());

    }
}
