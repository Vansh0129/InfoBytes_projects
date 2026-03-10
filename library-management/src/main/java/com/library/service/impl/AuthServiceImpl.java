package com.library.service.impl;

import com.library.dto.request.LoginRequest;
import com.library.dto.request.RegisterRequest;
import com.library.dto.response.AuthResponse;
import com.library.entity.UserAccess;
import com.library.entity.UserEntity;
import com.library.enums.Role;
import com.library.exception.AlreadyExistsException;
import com.library.repository.UserAccessRepository;
import com.library.repository.UserRepository;
import com.library.security.JwtService;
import com.library.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserAccessRepository userAccessRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUserName());
        String token = jwtService.generateToken(userDetails);

        UserAccess userAccess = userAccessRepository.findByUserName(request.getUserName()).orElseThrow();

        return AuthResponse.builder()
                .token(token)
                .username(userAccess.getUserName())
                .role(userAccess.getRole().name())
                .message("Login successful")
                .build();
    }

    @Override
    @Transactional
    public AuthResponse registerUser(RegisterRequest request) {
        if (userRepository.existsByGmail(request.getGmail())) {
            throw new AlreadyExistsException("Email already registered: " + request.getGmail());
        }
        if (userAccessRepository.existsByUserName(request.getUserName())) {
            throw new AlreadyExistsException("Username already taken: " + request.getUserName());
        }
        if (userRepository.existsByAdhaarNo(request.getAdhaarNo())) {
            throw new AlreadyExistsException("Aadhaar number already registered");
        }

        UserEntity user = UserEntity.builder()
                .name(request.getName())
                .dob(request.getDob())
                .adhaarNo(request.getAdhaarNo())
                .gmail(request.getGmail())
                .contactNo(request.getContactNo())
                .fineAmount(0.0)
                .build();

        UserEntity savedUser = userRepository.save(user);

        UserAccess userAccess = UserAccess.builder()
                .userName(request.getUserName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .user(savedUser)
                .build();

        userAccessRepository.save(userAccess);

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUserName());
        String token = jwtService.generateToken(userDetails);

        return AuthResponse.builder()
                .token(token)
                .username(request.getUserName())
                .role(Role.USER.name())
                .message("User registered successfully")
                .build();
    }
}
