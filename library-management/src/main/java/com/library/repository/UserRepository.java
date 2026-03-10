package com.library.repository;

import com.library.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByGmail(String gmail);
    boolean existsByGmail(String gmail);
    boolean existsByAdhaarNo(Long adhaarNo);
}
