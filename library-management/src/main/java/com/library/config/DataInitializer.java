package com.library.config;

import com.library.entity.UserAccess;
import com.library.entity.UserEntity;
import com.library.enums.Role;
import com.library.repository.UserAccessRepository;
import com.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserAccessRepository userAccessRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        createDefaultAdmin();
    }

    private void createDefaultAdmin() {
        if (userAccessRepository.existsByUserName("admin")) {
            log.info("✅ Admin already exists, skipping creation.");
            return;
        }

        UserEntity adminUser = UserEntity.builder()
                .name("System Admin")
                .dob(LocalDate.of(1990, 1, 1))
                .adhaarNo(000000000000L)
                .gmail("admin@library.com")
                .contactNo("0000000000")
                .fineAmount(0.0)
                .build();

        UserEntity savedAdmin = userRepository.save(adminUser);

        UserAccess adminAccess = UserAccess.builder()
                .userName("admin")
                .password(passwordEncoder.encode("admin@123"))
                .role(Role.ADMIN)
                .user(savedAdmin)
                .build();

        userAccessRepository.save(adminAccess);

        log.info("========================================");
        log.info("✅ Default Admin Created Successfully!");
        log.info("   Username : admin");
        log.info("   Password : admin@123");
        log.info("   Role     : ADMIN");
        log.info("========================================");
    }
}