package com.dp.user.management.repository;

import com.dp.user.management.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.userId = :userId AND e.date BETWEEN :startDate AND :endDate")
    Double findSumOfAmountByUserIdAndDateRange(Long userId, String startDate, String endDate);
}
