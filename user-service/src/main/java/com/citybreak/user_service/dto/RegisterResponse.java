package com.citybreak.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponse {
    private long id;
    private String email;
    private String fullName;
}
