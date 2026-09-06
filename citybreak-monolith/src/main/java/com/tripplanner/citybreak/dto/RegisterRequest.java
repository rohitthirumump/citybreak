package com.tripplanner.citybreak.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterRequest {
    @Email(message = "Invalid Email Format")
    @NotBlank(message = "Email is Required")
    private String email;

    @NotBlank(message = "Password is Required")
    @Size(min = 6,message = "Password must at least be 6 char long")
    private String password;

    @NotBlank(message = "Full Name is Required")
    private String fullName;
}
