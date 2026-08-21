package com.tripplanner.citybreak.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CityResponse {
    private long id;
    private String cityName;
    private String country;
    private String details;
}
