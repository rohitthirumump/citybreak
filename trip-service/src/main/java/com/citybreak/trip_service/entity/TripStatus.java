package com.citybreak.trip_service.entity;

public enum TripStatus {
    PLANNED,
    VISITED;

    public static TripStatus from(String value){
        try {
            return TripStatus.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Invalid status: '" + value + "'. Must be PLANNED or VISITED.");
        }
    }
}
