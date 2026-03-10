package com.library.service;

import com.library.entity.UserEntity;

public interface UserService {
    UserEntity getMyProfile(String username);
    double getMyFine(String username);
}
