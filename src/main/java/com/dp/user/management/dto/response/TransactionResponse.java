package com.dp.user.management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private Long id;
    private String date;
    private double amount;
    private String remark;
    private String invoiceFilePath;
    private double lat;
    private double lon;
    private Long userId;
    private Integer carId;
    private String type; // "INCOME" or "EXPENSE"
    private Long incomeId; // If type is "INCOME", this field will be populated
    private Long expenseId; // If type is "EXPENSE", this field will be populated
    private String status; // "PENDING", "APPROVED", etc.
    private IncomeResponse income;
    private ExpenseResponse expense;
}

