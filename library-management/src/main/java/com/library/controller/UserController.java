package com.library.controller;

import com.library.dto.response.ApiResponse;
import com.library.entity.UserEntity;
import com.library.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
@Tag(name = "User Profile", description = "Logged-in User's own profile and fine info")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    @Operation(summary = "Get My Profile", description = "Returns profile of the currently logged-in user.")
    public ResponseEntity<ApiResponse<UserEntity>> getMyProfile(
            @AuthenticationPrincipal UserDetails userDetails) {
        UserEntity user = userService.getMyProfile(userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success("Profile fetched", user));
    }

    @GetMapping("/fine")
    @Operation(summary = "Get My Fine", description = "Returns total pending fine amount for the logged-in user.")
    public ResponseEntity<ApiResponse<Double>> getMyFine(
            @AuthenticationPrincipal UserDetails userDetails) {
        double fine = userService.getMyFine(userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success("Fine amount fetched", fine));
    }
}
