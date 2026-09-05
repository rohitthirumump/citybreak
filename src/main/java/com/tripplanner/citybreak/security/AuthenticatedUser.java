package com.tripplanner.citybreak.security;

public record AuthenticatedUser(Long userId, String email) {
}
