package com.library.dto.response;

import com.library.enums.BookStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class BookResponse {
    private Long bookId;
    private String bookName;
    private String description;
    private LocalDate publishedDate;
    private String authorName;
    private BookStatus bookStatus;
    private List<String> reviewList;
}
