package com.library.controller;

import com.library.dto.request.AuthorRequest;
import com.library.dto.request.RegisterRequest;
import com.library.dto.response.ApiResponse;
import com.library.entity.Author;
import com.library.entity.UserEntity;
import com.library.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin", description = "Admin-only APIs: Manage Authors and Users")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    private final AdminService adminService;

    // AUTHOR ENDPOINTS

    @PostMapping("/authors")
    @Operation(summary = "Create Author", description = "ADMIN only - Create a new author.")
    public ResponseEntity<ApiResponse<Author>> createAuthor(@Valid @RequestBody AuthorRequest request) {
        Author author = adminService.createAuthor(request);
        return new ResponseEntity<>(ApiResponse.success("Author created successfully", author), HttpStatus.CREATED);
    }

    @GetMapping("/authors")
    @Operation(summary = "Get All Authors", description = "ADMIN only - Retrieve all authors.")
    public ResponseEntity<ApiResponse<List<Author>>> getAllAuthors() {
        List<Author> authors = adminService.getAllAuthors();
        return ResponseEntity.ok(ApiResponse.success("Authors fetched successfully", authors));
    }

    @GetMapping("/authors/{authorId}")
    @Operation(summary = "Get Author by ID", description = "ADMIN only - Retrieve a specific author.")
    public ResponseEntity<ApiResponse<Author>> getAuthorById(@PathVariable Long authorId) {
        Author author = adminService.getAuthorById(authorId);
        return ResponseEntity.ok(ApiResponse.success("Author fetched successfully", author));
    }

    @PutMapping("/authors/{authorId}")
    @Operation(summary = "Update Author", description = "ADMIN only - Update an existing author.")
    public ResponseEntity<ApiResponse<Author>> updateAuthor(
            @PathVariable Long authorId,
            @Valid @RequestBody AuthorRequest request) {
        Author author = adminService.updateAuthor(authorId, request);
        return ResponseEntity.ok(ApiResponse.success("Author updated successfully", author));
    }

    @DeleteMapping("/authors/{authorId}")
    @Operation(summary = "Delete Author", description = "ADMIN only - Delete an author.")
    public ResponseEntity<ApiResponse<Void>> deleteAuthor(@PathVariable Long authorId) {
        adminService.deleteAuthor(authorId);
        return ResponseEntity.ok(ApiResponse.success("Author deleted successfully", null));
    }

    //  USER ENDPOINTS

    @PostMapping("/users")
    @Operation(summary = "Create User", description = "ADMIN only - Create a new library user.")
    public ResponseEntity<ApiResponse<UserEntity>> createUser(@Valid @RequestBody RegisterRequest request) {
        UserEntity user = adminService.createUser(request);
        return new ResponseEntity<>(ApiResponse.success("User created successfully", user), HttpStatus.CREATED);
    }

    @GetMapping("/users")
    @Operation(summary = "Get All Users", description = "ADMIN only - Retrieve all library users.")
    public ResponseEntity<ApiResponse<List<UserEntity>>> getAllUsers() {
        List<UserEntity> users = adminService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success("Users fetched successfully", users));
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "Get User by ID", description = "ADMIN only - Retrieve a specific user.")
    public ResponseEntity<ApiResponse<UserEntity>> getUserById(@PathVariable Long userId) {
        UserEntity user = adminService.getUserById(userId);
        return ResponseEntity.ok(ApiResponse.success("User fetched successfully", user));
    }

    @PutMapping("/users/{userId}")
    @Operation(summary = "Update User", description = "ADMIN only - Update an existing user.")
    public ResponseEntity<ApiResponse<UserEntity>> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody RegisterRequest request) {
        UserEntity user = adminService.updateUser(userId, request);
        return ResponseEntity.ok(ApiResponse.success("User updated successfully", user));
    }

    @DeleteMapping("/users/{userId}")
    @Operation(summary = "Delete User", description = "ADMIN only - Delete a user.")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long userId) {
        adminService.deleteUser(userId);
        return ResponseEntity.ok(ApiResponse.success("User deleted successfully", null));
    }
}
