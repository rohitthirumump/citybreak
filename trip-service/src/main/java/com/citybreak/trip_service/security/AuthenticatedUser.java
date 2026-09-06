package com.citybreak.trip_service.security;

public record AuthenticatedUser(Long userId, String email) {
}
