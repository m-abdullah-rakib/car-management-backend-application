package com.dp.user.management.repository;

import com.dp.user.management.model.Cohort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CohortRepository extends JpaRepository<Cohort, Long> {
    List<Cohort> findAllByYear(int year);
}
