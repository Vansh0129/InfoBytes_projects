package com.library.service;

import com.library.dto.request.BookRequest;
import com.library.dto.response.BookResponse;
import com.library.enums.BookStatus;

import java.util.List;

public interface BookService {
    BookResponse createBook(BookRequest request, String authorUsername);
    BookResponse updateBook(Long bookId, BookRequest request, String authorUsername);
    void deleteBook(Long bookId, String authorUsername);
    BookResponse getBookById(Long bookId);
    List<BookResponse> getAllBooks();
    List<BookResponse> getBooksByStatus(BookStatus status);
    List<BookResponse> searchBooksByName(String name);
    List<BookResponse> getBooksByAuthor(Long authorId);
    BookResponse addReview(Long bookId, String review, String username);
}
