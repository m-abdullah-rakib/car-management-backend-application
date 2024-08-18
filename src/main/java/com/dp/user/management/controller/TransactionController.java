package com.dp.user.management.controller;

import com.dp.user.management.dto.request.ExpenseRequest;
import com.dp.user.management.dto.request.IncomeRequest;
import com.dp.user.management.service.JwtService;
import com.dp.user.management.service.TransactionService;
import com.dp.user.management.util.validation.RequestValidationHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final JwtService jwtService;
    private final RequestValidationHandler requestValidationHandler;

    @PostMapping("/create-income")
    public ResponseEntity<?> createIncome(@Valid @RequestBody IncomeRequest incomeRequest, @RequestHeader("Authorization") String token, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return requestValidationHandler.handleInvalidRequest(bindingResult);
        }

        String jwtToken = token.substring(7); // Remove "Bearer " prefix
        String email = jwtService.extractUsername(jwtToken);

        return transactionService.createIncome(incomeRequest, email);
    }

    @PostMapping("/create-expense")
    public ResponseEntity<?> createExpense(@Valid @RequestBody ExpenseRequest expenseRequest, @RequestHeader("Authorization") String token, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return requestValidationHandler.handleInvalidRequest(bindingResult);
        }

        String jwtToken = token.substring(7); // Remove "Bearer " prefix
        String email = jwtService.extractUsername(jwtToken);

        return transactionService.createExpense(expenseRequest, email);
    }

}
