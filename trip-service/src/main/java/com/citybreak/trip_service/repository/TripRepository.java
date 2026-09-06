package com.citybreak.trip_service.repository;

import com.citybreak.trip_service.entity.Trip;
import com.citybreak.trip_service.entity.TripStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TripRepository extends JpaRepository<Trip,Long> {
    List<Trip> findByUserId(Long userId);
    Optional<Trip> findByIdAndUserId(Long id, Long userId);
    List<Trip> findByUserIdAndStatus(Long userId, TripStatus status);
}
