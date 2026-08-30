package com.tripplanner.citybreak.service;

import com.tripplanner.citybreak.dto.TripRequest;
import com.tripplanner.citybreak.dto.TripResponse;
import com.tripplanner.citybreak.entity.City;
import com.tripplanner.citybreak.entity.Trip;
import com.tripplanner.citybreak.entity.TripStatus;
import com.tripplanner.citybreak.entity.User;
import com.tripplanner.citybreak.repository.CityRepository;
import com.tripplanner.citybreak.repository.TripRepository;
import com.tripplanner.citybreak.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

        return toResponse(saved);

    }

    public void deleteTrip(Long tripId,Long userId){
        Trip trip = tripRepository.findByIdAndUserId(tripId,userId).orElseThrow(
                () -> new IllegalArgumentException("Trip not found for " + tripId));

        tripRepository.delete(trip);
    }

    public TripResponse updateTrip(Long tripId, Long userId,TripRequest request){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("User does not exist " + userId));

        City city = cityRepository.findByCityNameAndCountry(
                request.getCityName(), request.getCountry()).orElseGet(() -> cityRepository.save(
                new City(null,request.getCityName(),request.getCountry())));

        Trip trip = tripRepository.findByIdAndUserId(tripId,userId).orElseThrow(
                () -> new IllegalArgumentException("Trip not found for " + tripId));

        trip.setUser(user);
        trip.setCity(city);
        trip.setDescription(request.getDescription());
        trip.setStatus(request.getStatus());
        trip.setStartDate(request.getStartDate());
        trip.setEndDate(request.getEndDate());

        Trip saved = tripRepository.save(trip);

        return toResponse(saved);
    }

    public List<TripResponse> getAllTrips(Long userId){
         return tripRepository.findByUserId(userId).stream()
                 .map(this :: toResponse)
                 .toList();

    }

    public TripResponse getTrip(Long userId,Long tripId){
        Trip trip = tripRepository.findByIdAndUserId(tripId,userId).orElseThrow(
                () -> new IllegalArgumentException("Trip does not exist for " + tripId));

        return toResponse(trip);

    }

    public List<TripResponse> getTripUsingStatus(Long userId, TripStatus status){
        return tripRepository.findByUserIdAndStatus(userId,status).stream()
                .map(this::toResponse)
                .toList();
    }

    private TripResponse toResponse(Trip trip){
        return new TripResponse(
                trip.getId(),
                trip.getCity().getCityName(),
                trip.getDescription(),
                trip.getStatus(),
                trip.getStartDate(),
                trip.getEndDate(),
                trip.getCreatedAt()
        );
    }
}
