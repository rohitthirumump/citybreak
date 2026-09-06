package com.citybreak.user_service.security;

public record AuthenticatedUser(Long userId, String email) {
}
