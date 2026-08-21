package com.tripplanner.citybreak.controller;

import com.tripplanner.citybreak.dto.CityRequest;
import com.tripplanner.citybreak.dto.CityResponse;
import com.tripplanner.citybreak.service.CityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/city")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping()
    public ResponseEntity<CityResponse> addCity(@Valid @RequestBody CityRequest request){
        CityResponse cityResponse = cityService.addCity(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(cityResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityResponse> updateCity(
            @PathVariable Long id,
            @Valid @RequestBody CityRequest request){
        CityResponse response = cityService.updateCity(id, request);
        return ResponseEntity.ok(response);
    }
}
