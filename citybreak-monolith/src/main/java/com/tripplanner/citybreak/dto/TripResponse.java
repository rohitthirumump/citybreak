package com.tripplanner.citybreak.dto;

import com.tripplanner.citybreak.entity.TripStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TripResponse {
    private Long id;
    private String cityName;
    private String description;
    private TripStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private Instant createdAt;
}
