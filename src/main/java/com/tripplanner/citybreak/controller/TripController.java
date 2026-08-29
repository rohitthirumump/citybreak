package com.tripplanner.citybreak.controller;

import com.tripplanner.citybreak.dto.TripRequest;
import com.tripplanner.citybreak.dto.TripResponse;
import com.tripplanner.citybreak.service.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping("/users/{userId}/trips")
    public ResponseEntity<TripResponse> createTrip(
            @PathVariable Long userId,
            @RequestBody TripRequest request){

        TripResponse response = tripService.createTrip(userId,request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
}
