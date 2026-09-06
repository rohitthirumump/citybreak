package com.citybreak.trip_service.service;

import com.citybreak.trip_service.dto.TripRequest;
import com.citybreak.trip_service.dto.TripResponse;
import com.citybreak.trip_service.entity.City;
import com.citybreak.trip_service.entity.Trip;
import com.citybreak.trip_service.entity.TripStatus;
import com.citybreak.trip_service.exception.ResourceNotFoundException;
import com.citybreak.trip_service.repository.CityRepository;
import com.citybreak.trip_service.repository.TripRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TripService {
    private final TripRepository tripRepository;
    private final CityRepository cityRepository;

    public TripService(TripRepository tripRepository, CityRepository cityRepository) {
        this.tripRepository = tripRepository;
        this.cityRepository = cityRepository;
    }

    public TripResponse createTrip(Long userId, TripRequest request){
//        User user = userRepository.findById(userId).orElseThrow(
//                () -> new ResourceNotFoundException("User does not exist " + userId));

        City city = resolveCity(request.getCityName(), request.getCountry());

        validateDates(request.getStartDate(),request.getEndDate());

        Trip trip = new Trip();
        trip.setUserId(userId);
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
                () -> new ResourceNotFoundException("Trip not found for " + tripId));

        tripRepository.delete(trip);
    }

    public TripResponse updateTrip(Long tripId, Long userId,TripRequest request){
        Trip trip = tripRepository.findByIdAndUserId(tripId,userId).orElseThrow(
                () -> new ResourceNotFoundException("Trip not found for " + tripId));

        City city = resolveCity(request.getCityName(), request.getCountry());

        validateDates(request.getStartDate(),request.getEndDate());

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
                () -> new ResourceNotFoundException("Trip does not exist for " + tripId));

        return toResponse(trip);

    }

    public List<TripResponse> getTripUsingStatus(Long userId, TripStatus status){
        return tripRepository.findByUserIdAndStatus(userId,status).stream()
                .map(this::toResponse)
                .toList();
    }

    private City resolveCity(String cityName, String country){
        return cityRepository.findByCityNameAndCountry(
                cityName, country).orElseGet(() -> cityRepository.save(
                new City(null,cityName,country)));
    }

    private void validateDates(LocalDate startDate, LocalDate endDate) {
        if (endDate != null && startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("startDate cannot be after endDate");
        }
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
