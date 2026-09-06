package com.citybreak.trip_service.dto;

import com.citybreak.trip_service.entity.TripStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TripRequest {
    @NotBlank(message = "City Name is required")
    private String cityName;
    @NotBlank(message = "Country Name is required")
    private String country;
    @NotBlank(message = "Description is required")
    private String description;
    @NotNull(message = "Enter the Status")
    private TripStatus status;
    @NotNull(message = "Enter a Start Date")
    private LocalDate startDate;
    @NotNull(message = "Enter a End Date")
    private LocalDate endDate;
}
