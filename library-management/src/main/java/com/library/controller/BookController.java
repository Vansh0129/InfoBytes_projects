package com.library.controller;

import com.library.dto.request.BookRequest;
import com.library.dto.response.ApiResponse;
import com.library.dto.response.BookResponse;
import com.library.enums.BookStatus;
import com.library.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Tag(name = "Books", description = "Book management - Authors create/edit books, all authenticated users can view")
@SecurityRequirement(name = "bearerAuth")
public class BookController {

    private final BookService bookService;

    //AUTHOR-ONLY

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Create Book (Author only via ADMIN role)",
            description = "Only a logged-in Author (whose gmail matches UserAccess username) can create a book. " +
                    "The author identity is resolved automatically from the JWT token."
    )
    public ResponseEntity<ApiResponse<BookResponse>> createBook(
            @Valid @RequestBody BookRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        BookResponse response = bookService.createBook(request, userDetails.getUsername());
        return new ResponseEntity<>(ApiResponse.success("Book created successfully", response), HttpStatus.CREATED);
    }

    @PutMapping("/{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update Book (Author only)", description = "An author can only update their own books.")
    public ResponseEntity<ApiResponse<BookResponse>> updateBook(
            @PathVariable Long bookId,
            @Valid @RequestBody BookRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        BookResponse response = bookService.updateBook(bookId, request, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success("Book updated successfully", response));
    }

    @DeleteMapping("/{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete Book (Author only)", description = "An author can only delete their own books.")
    public ResponseEntity<ApiResponse<Void>> deleteBook(
            @PathVariable Long bookId,
            @AuthenticationPrincipal UserDetails userDetails) {
        bookService.deleteBook(bookId, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success("Book deleted successfully", null));
    }

    //  ALL AUTHENTICATED USERS

    @GetMapping
    @Operation(summary = "Get All Books", description = "Accessible by any authenticated user.")
    public ResponseEntity<ApiResponse<List<BookResponse>>> getAllBooks() {
        return ResponseEntity.ok(ApiResponse.success("Books fetched successfully", bookService.getAllBooks()));
    }

    @GetMapping("/{bookId}")
    @Operation(summary = "Get Book by ID", description = "Accessible by any authenticated user.")
    public ResponseEntity<ApiResponse<BookResponse>> getBookById(@PathVariable Long bookId) {
        return ResponseEntity.ok(ApiResponse.success("Book fetched successfully", bookService.getBookById(bookId)));
    }

    @GetMapping("/search")
    @Operation(summary = "Search Books by Name", description = "Search books by partial name match.")
    public ResponseEntity<ApiResponse<List<BookResponse>>> searchBooks(@RequestParam String name) {
        return ResponseEntity.ok(ApiResponse.success("Search results", bookService.searchBooksByName(name)));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get Books by Status", description = "Filter books by AVAILABLE, ISSUED, or UNAVAILABLE.")
    public ResponseEntity<ApiResponse<List<BookResponse>>> getBooksByStatus(@PathVariable BookStatus status) {
        return ResponseEntity.ok(ApiResponse.success("Books fetched by status", bookService.getBooksByStatus(status)));
    }

    @GetMapping("/author/{authorId}")
    @Operation(summary = "Get Books by Author", description = "Get all books written by a specific author.")
    public ResponseEntity<ApiResponse<List<BookResponse>>> getBooksByAuthor(@PathVariable Long authorId) {
        return ResponseEntity.ok(ApiResponse.success("Books by author", bookService.getBooksByAuthor(authorId)));
    }

    @PostMapping("/{bookId}/review")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Add Review (User only)", description = "Authenticated users can add a review to a book.")
    public ResponseEntity<ApiResponse<BookResponse>> addReview(
            @PathVariable Long bookId,
            @RequestParam String review,
            @AuthenticationPrincipal UserDetails userDetails) {
        BookResponse response = bookService.addReview(bookId, review, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success("Review added successfully", response));
    }
}
