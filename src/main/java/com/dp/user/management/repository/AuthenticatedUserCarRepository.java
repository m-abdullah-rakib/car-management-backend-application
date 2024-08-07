package com.dp.user.management.repository;

import com.dp.user.management.model.AuthenticatedUserCar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthenticatedUserCarRepository extends JpaRepository<AuthenticatedUserCar, Integer> {
    List<AuthenticatedUserCar> findByUserIdIsNull();
}
