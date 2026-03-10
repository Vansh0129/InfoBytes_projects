package com.library.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookRequest {

    @NotBlank(message = "Book name is required")
    private String bookName;

    private String description;

    private LocalDate publishedDate;

    @NotNull(message = "Author ID is required")
    private Long authorId;
}
