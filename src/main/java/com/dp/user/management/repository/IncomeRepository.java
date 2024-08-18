package com.dp.user.management.repository;

import com.dp.user.management.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    @Query("SELECT SUM(i.amount) FROM Income i WHERE i.userId = :userId AND i.date BETWEEN :startDate AND :endDate")
    Double findSumOfAmountByUserIdAndDateRange(Long userId, String startDate, String endDate);
}
