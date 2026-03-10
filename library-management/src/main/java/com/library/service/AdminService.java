package com.library.service;

import com.library.dto.request.AuthorRequest;
import com.library.dto.request.RegisterRequest;
import com.library.dto.response.ApiResponse;
import com.library.entity.Author;
import com.library.entity.UserEntity;

import java.util.List;

public interface AdminService {
    Author createAuthor(AuthorRequest request);
    Author updateAuthor(Long authorId, AuthorRequest request);
    void deleteAuthor(Long authorId);
    List<Author> getAllAuthors();
    Author getAuthorById(Long authorId);

    UserEntity createUser(RegisterRequest request);
    UserEntity updateUser(Long userId, RegisterRequest request);
    void deleteUser(Long userId);
    List<UserEntity> getAllUsers();
    UserEntity getUserById(Long userId);
}
