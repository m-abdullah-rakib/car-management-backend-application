package com.dp.user.management.repository;

import com.dp.user.management.model.RecentTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecentTransactionsRepository extends JpaRepository<RecentTransactions, Long> {
    @Procedure(name = "RecentTransactions")
    List<RecentTransactions> recentTransactions(@Param("userId") Long userId);

}
