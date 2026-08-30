package com.tripplanner.citybreak.controller;

import com.tripplanner.citybreak.dto.TripRequest;
import com.tripplanner.citybreak.dto.TripResponse;
import com.tripplanner.citybreak.entity.TripStatus;
import com.tripplanner.citybreak.service.TripService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping
    public ResponseEntity<TripResponse> createTrip(
            @RequestParam Long userId,
            @Valid @RequestBody TripRequest request){

        TripResponse response = tripService.createTrip(userId,request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping("/{tripId}")
    public ResponseEntity<TripResponse> getTrip(
            @PathVariable Long tripId,
            @RequestParam Long userId){
        return ResponseEntity.ok(tripService.getTrip(userId, tripId));
    }

    @GetMapping
    public ResponseEntity<List<TripResponse>> getTrips(
            @RequestParam Long userId,
            @RequestParam(required = false) TripStatus status){
        List<TripResponse> tripResponses = (status != null) ?
                tripService.getTripUsingStatus(userId, status) :
                tripService.getAllTrips(userId);

        return  ResponseEntity.ok(tripResponses);
    }

    @PutMapping("/{tripId}")
    public ResponseEntity<TripResponse> updateTrip(
            @RequestParam Long userId,
            @PathVariable Long tripId,
            @Valid @RequestBody TripRequest request){
        return ResponseEntity.ok(tripService.updateTrip(tripId,userId,request));
    }

    @DeleteMapping("/{tripId}")
    public ResponseEntity<String> deleteTrip(
            @PathVariable Long tripId,
            @RequestParam Long userId){
        tripService.deleteTrip(tripId, userId);
        return ResponseEntity.ok("Delete Successful");
    }
}
