package com.tripplanner.citybreak.repository;

import com.tripplanner.citybreak.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip,Long> {
}
