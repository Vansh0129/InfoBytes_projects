package com.library.dto.request;

import com.library.enums.AuthorRating;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AuthorRequest {

    @NotBlank(message = "Author name is required")
    private String authorName;

    private LocalDate dob;

    @Email(message = "Invalid email format")
    private String gmail;

    private AuthorRating rating;
}
