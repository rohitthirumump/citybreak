package com.tripplanner.citybreak.entity;

public enum TripStatus {
    VISITED,
    PLANNED;

    public static TripStatus from(String value){
        try {
            return TripStatus.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Invalid status: '" + value + "'. Must be PLANNED or VISITED.");
        }
    }
}
