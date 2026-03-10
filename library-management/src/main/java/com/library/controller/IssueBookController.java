package com.library.controller;

import com.library.dto.response.ApiResponse;
import com.library.entity.IssueBook;
import com.library.service.IssueBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
@Tag(name = "Issue Book", description = "Book issuing and returning APIs")
@SecurityRequirement(name = "bearerAuth")
public class IssueBookController {

    private final IssueBookService issueBookService;

    @PostMapping("/issue/{bookId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Issue a Book (User only)", description = "Authenticated user can issue an available book. Return deadline is 14 days.")
    public ResponseEntity<ApiResponse<IssueBook>> issueBook(
            @PathVariable Long bookId,
            @AuthenticationPrincipal UserDetails userDetails) {
        IssueBook issueBook = issueBookService.issueBook(bookId, userDetails.getUsername());
        return new ResponseEntity<>(ApiResponse.success("Book issued successfully. Return by: " + issueBook.getReturnDate(), issueBook), HttpStatus.CREATED);
    }

    @PutMapping("/return/{issueId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Return a Book (User only)", description = "Return an issued book. Fine of ₹5/day applied if overdue.")
    public ResponseEntity<ApiResponse<IssueBook>> returnBook(
            @PathVariable Long issueId,
            @AuthenticationPrincipal UserDetails userDetails) {
        IssueBook issueBook = issueBookService.returnBook(issueId, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success("Book returned successfully", issueBook));
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "My Issued Books (User only)", description = "Get all books currently or previously issued by the logged-in user.")
    public ResponseEntity<ApiResponse<List<IssueBook>>> getMyIssuedBooks(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<IssueBook> books = issueBookService.getMyIssuedBooks(userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success("Your issued books", books));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "All Issued Books (Admin only)", description = "Admin can view all issued book records.")
    public ResponseEntity<ApiResponse<List<IssueBook>>> getAllIssuedBooks() {
        List<IssueBook> books = issueBookService.getAllIssuedBooks();
        return ResponseEntity.ok(ApiResponse.success("All issued books", books));
    }

    @GetMapping("/overdue")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Overdue Books (Admin only)", description = "Admin can view all overdue book records.")
    public ResponseEntity<ApiResponse<List<IssueBook>>> getOverdueBooks() {
        List<IssueBook> books = issueBookService.getOverdueBooks();
        return ResponseEntity.ok(ApiResponse.success("Overdue books", books));
    }

    @PostMapping("/check-overdue")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Mark Overdue Books (Admin only)", description = "Admin triggers a manual scan to mark overdue books.")
    public ResponseEntity<ApiResponse<Void>> checkAndMarkOverdue() {
        issueBookService.checkAndMarkOverdue();
        return ResponseEntity.ok(ApiResponse.success("Overdue check completed", null));
    }
}
