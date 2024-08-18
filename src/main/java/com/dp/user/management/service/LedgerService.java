package com.dp.user.management.service;

import com.dp.user.management.dto.ExpenseSummary;
import com.dp.user.management.dto.IncomeSummary;
import com.dp.user.management.dto.response.DriverOverviewResponse;
import com.dp.user.management.dto.response.LedgerResponse;
import com.dp.user.management.dto.response.TransactionResponse;
import com.dp.user.management.model.AuthenticatedUser;
import com.dp.user.management.repository.AuthenticatedUserRepository;
import com.dp.user.management.repository.ExpenseRepository;
import com.dp.user.management.repository.IncomeRepository;
import com.dp.user.management.repository.RecentTransactionsRepository;
import com.dp.user.management.util.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LedgerService {
    private final AuthenticatedUserRepository authenticatedUserRepository;
    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;
    private final RecentTransactionsRepository recentTransactionsRepository;

    @Transactional
    public ResponseEntity<?> getLedger(String email) {
        AuthenticatedUser authenticatedUser;
        Optional<AuthenticatedUser> authenticatedUserOptional = authenticatedUserRepository.findByEmail(email);
        if (authenticatedUserOptional.isPresent()) {
            authenticatedUser = authenticatedUserOptional.get();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        List<TransactionResponse> transactionResponseList = recentTransactionsRepository.recentTransactions(authenticatedUser.getId()).stream()
                .map(TransactionMapper::mapToResponse)
                .toList();

        return ResponseEntity.ok(new LedgerResponse(transactionResponseList));
    }

    public ResponseEntity<?> getDriverOverview(String email, String start, String end) {
        AuthenticatedUser authenticatedUser;
        Optional<AuthenticatedUser> authenticatedUserOptional = authenticatedUserRepository.findByEmail(email);
        if (authenticatedUserOptional.isPresent()) {
            authenticatedUser = authenticatedUserOptional.get();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        return ResponseEntity.ok(getDriverOverviewResponse(authenticatedUser, start, end));
    }

    private DriverOverviewResponse getDriverOverviewResponse(AuthenticatedUser authenticatedUser, String start, String end) {
        DriverOverviewResponse driverOverviewResponse = new DriverOverviewResponse();
        IncomeSummary incomeSummary = new IncomeSummary();
        ExpenseSummary expenseSummary = new ExpenseSummary();
        double totalIncome;
        double totalExpense;
        try {
            totalIncome = incomeRepository.findSumOfAmountByUserIdAndDateRange(authenticatedUser.getId(), start, end);
            incomeSummary.setDriverIncome((70.0 * totalIncome) / 100);
        } catch (
                Exception e) { // When no income and expense are found between given date range return null value from db, so assigning 0.0 to following data.
            totalIncome = 0.0;
            incomeSummary.setDriverIncome(0.0);
        }
        try {
            totalExpense = expenseRepository.findSumOfAmountByUserIdAndDateRange(authenticatedUser.getId(), start, end);
        } catch (
                Exception e) { // When no income and expense are found between given date range return null value from db, so assigning 0.0 to following data.
            totalExpense = 0.0;
        }
        incomeSummary.setTotal(totalIncome);
        incomeSummary.setAvgRevenuePercentage(70.0);
        driverOverviewResponse.setIncome(incomeSummary);
        expenseSummary.setTotal(totalExpense);
        driverOverviewResponse.setExpense(expenseSummary);
        driverOverviewResponse.setPayment(totalIncome + totalExpense);

        return driverOverviewResponse;
    }
}
