package com.dp.user.management.repository;

import com.dp.user.management.model.AuthenticatedUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthenticatedUserRepository extends JpaRepository<AuthenticatedUser, Integer> {
    Optional<AuthenticatedUser> findByEmail(String email);
}
