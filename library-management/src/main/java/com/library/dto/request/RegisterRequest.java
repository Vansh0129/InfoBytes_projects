package com.library.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Date of birth is required")
    private LocalDate dob;

    @NotNull(message = "Aadhaar number is required")
    private Long adhaarNo;

    @NotBlank(message = "Gmail is required")
    @Email(message = "Invalid email format")
    private String gmail;

    @NotBlank(message = "Contact number is required")
    private String contactNo;

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String userName;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
}
