package com.dp.user.management.service;

import com.dp.user.management.dto.request.ExpenseRequest;
import com.dp.user.management.dto.request.IncomeRequest;
import com.dp.user.management.model.AuthenticatedUser;
import com.dp.user.management.model.Expense;
import com.dp.user.management.model.Income;
import com.dp.user.management.repository.AuthenticatedUserRepository;
import com.dp.user.management.repository.ExpenseRepository;
import com.dp.user.management.repository.IncomeRepository;
import com.dp.user.management.util.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;
    private final AuthenticatedUserRepository authenticatedUserRepository;
    private static final String UPLOAD_DIR = "/home/rakib/Desktop/DriveImage/";

    @Transactional
    public ResponseEntity<?> createIncome(IncomeRequest request, String email) {

        AuthenticatedUser authenticatedUser;
        Optional<AuthenticatedUser> authenticatedUserOptional = authenticatedUserRepository.findByEmail(email);
        if (authenticatedUserOptional.isPresent()) {
            authenticatedUser = authenticatedUserOptional.get();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        Path imagePath = Paths.get(UPLOAD_DIR + request.invoiceFilePath());
        if (Files.notExists(imagePath)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invoice file did not upload successfully.");
        }

        Income income = getIncome(request, authenticatedUser);

        Income savedIncome = incomeRepository.save(income);
        return ResponseEntity.ok(TransactionMapper.mapToResponse(savedIncome));

    }

    private Income getIncome(IncomeRequest request, AuthenticatedUser authenticatedUser) {
        Income income = new Income();
        income.setType(request.type());
        income.setDate(request.date());
        income.setAmount(request.amount());
        income.setTips(request.tips());
        income.setOffer(request.offer());
        income.setRemark(request.remark());
        income.setInvoiceFilePath(request.invoiceFilePath());
        income.setLat(request.lat());
        income.setLon(request.lon());
        income.setUserId(authenticatedUser.getId());
        income.setCarId(authenticatedUser.getAuthenticatedUserCar().getId());
        income.setStatus("PENDING");
        return income;
    }

    @Transactional
    public ResponseEntity<?> createExpense(ExpenseRequest request, String email) {

        AuthenticatedUser authenticatedUser;
        Optional<AuthenticatedUser> authenticatedUserOptional = authenticatedUserRepository.findByEmail(email);
        if (authenticatedUserOptional.isPresent()) {
            authenticatedUser = authenticatedUserOptional.get();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        Path imagePath = Paths.get(UPLOAD_DIR + request.invoiceFilePath());
        if (Files.notExists(imagePath)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invoice file did not upload successfully.");
        }

        Expense expense = getExpense(request, authenticatedUser);

        Expense savedExpense = expenseRepository.save(expense);
        return ResponseEntity.ok(TransactionMapper.mapToResponse(savedExpense));

    }

    private Expense getExpense(ExpenseRequest request, AuthenticatedUser authenticatedUser) {
        Expense expense = new Expense();
        expense.setType(request.type());
        expense.setDate(request.date());
        expense.setAmount(request.amount());
        expense.setParkingDurationMin(request.parkingDurationMin());
        expense.setFuelVendor(request.fuelVendor());
        expense.setFuelVolume(request.fuelVolume());
        expense.setFuelDetails(request.fuelDetails());
        expense.setRemark(request.remark());
        expense.setInvoiceFilePath(request.invoiceFilePath());
        expense.setLat(request.lat());
        expense.setLon(request.lon());
        expense.setUserId(authenticatedUser.getId());
        expense.setCarId(authenticatedUser.getAuthenticatedUserCar().getId());
        expense.setStatus("PENDING");
        return expense;
    }

}

