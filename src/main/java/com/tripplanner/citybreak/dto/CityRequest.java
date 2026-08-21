package com.tripplanner.citybreak.dto;

import com.tripplanner.citybreak.entity.CityVisited;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CityRequest {

    @NotBlank(message = "City name is Required")
    private String cityName;

    @NotBlank(message = "Country is Required")
    private String country;

    @NotBlank(message = "Description is Required")
    private String details;

    @NotNull(message = "Visited field is Required")
    private CityVisited visited;
}
