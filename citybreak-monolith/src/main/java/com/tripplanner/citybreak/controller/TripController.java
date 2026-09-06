package com.tripplanner.citybreak.controller;

import com.tripplanner.citybreak.dto.TripRequest;
import com.tripplanner.citybreak.dto.TripResponse;
import com.tripplanner.citybreak.entity.TripStatus;
import com.tripplanner.citybreak.security.AuthenticatedUser;
import com.tripplanner.citybreak.service.TripService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            @AuthenticationPrincipal AuthenticatedUser principal,
            @Valid @RequestBody TripRequest request){

        TripResponse response = tripService.createTrip(principal.userId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping("/{tripId}")
    public ResponseEntity<TripResponse> getTrip(
            @PathVariable Long tripId,
            @AuthenticationPrincipal AuthenticatedUser principal){
        return ResponseEntity.ok(tripService.getTrip(principal.userId(), tripId));
    }

    @GetMapping
    public ResponseEntity<List<TripResponse>> getTrips(
            @AuthenticationPrincipal AuthenticatedUser principal,
            @RequestParam(required = false) TripStatus status){
        List<TripResponse> tripResponses = (status != null) ?
                tripService.getTripUsingStatus(principal.userId(), status) :
                tripService.getAllTrips(principal.userId());

        return  ResponseEntity.ok(tripResponses);
    }

    @PutMapping("/{tripId}")
    public ResponseEntity<TripResponse> updateTrip(
            @AuthenticationPrincipal AuthenticatedUser principal,
            @PathVariable Long tripId,
            @Valid @RequestBody TripRequest request){
        return ResponseEntity.ok(tripService.updateTrip(tripId,principal.userId(),request));
    }

    @DeleteMapping("/{tripId}")
    public ResponseEntity<String> deleteTrip(
            @PathVariable Long tripId,
            @AuthenticationPrincipal AuthenticatedUser principal){
        tripService.deleteTrip(tripId, principal.userId());
        return ResponseEntity.ok("Delete Successful");
    }
}
