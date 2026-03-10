package com.library.service.impl;

import com.library.dto.request.AuthorRequest;
import com.library.dto.request.RegisterRequest;
import com.library.entity.Author;
import com.library.entity.UserAccess;
import com.library.entity.UserEntity;
import com.library.enums.Role;
import com.library.exception.AlreadyExistsException;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.AuthorRepository;
import com.library.repository.UserAccessRepository;
import com.library.repository.UserRepository;
import com.library.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;
    private final UserAccessRepository userAccessRepository;
    private final PasswordEncoder passwordEncoder;

    // AUTHOR MANAGEMENT

    @Override
    @Transactional
    public Author createAuthor(AuthorRequest request) {
        if (authorRepository.existsByGmail(request.getGmail())) {
            throw new AlreadyExistsException("Author with email already exists: " + request.getGmail());
        }

        Author author = Author.builder()
                .authorName(request.getAuthorName())
                .dob(request.getDob())
                .gmail(request.getGmail())
                .rating(request.getRating())
                .build();

        return authorRepository.save(author);
    }

    @Override
    @Transactional
    public Author updateAuthor(Long authorId, AuthorRequest request) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with ID: " + authorId));

        author.setAuthorName(request.getAuthorName());
        author.setDob(request.getDob());
        author.setGmail(request.getGmail());
        author.setRating(request.getRating());

        return authorRepository.save(author);
    }

    @Override
    @Transactional
    public void deleteAuthor(Long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with ID: " + authorId));
        authorRepository.delete(author);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author getAuthorById(Long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with ID: " + authorId));
    }

    // USER MANAGEMENT

    @Override
    @Transactional
    public UserEntity createUser(RegisterRequest request) {
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
        return savedUser;
    }

    @Override
    @Transactional
    public UserEntity updateUser(Long userId, RegisterRequest request) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        user.setName(request.getName());
        user.setDob(request.getDob());
        user.setGmail(request.getGmail());
        user.setContactNo(request.getContactNo());

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        userRepository.delete(user);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
    }

}
