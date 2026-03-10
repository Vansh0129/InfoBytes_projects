package com.library.service.impl;

import com.library.entity.UserAccess;
import com.library.entity.UserEntity;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.UserAccessRepository;
import com.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserAccessRepository userAccessRepository;

    private UserEntity resolveUser(String username) {
        UserAccess access = userAccessRepository.findByUserName(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
        if (access.getUser() == null) {
            throw new ResourceNotFoundException("User profile not linked for: " + username);
        }
        return access.getUser();
    }

    @Override
    public UserEntity getMyProfile(String username) {
        return resolveUser(username);
    }

    @Override
    public double getMyFine(String username) {
        return resolveUser(username).getFineAmount();
    }
}
