package com.dp.user.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncomeSummary {
    private double total;
    private double driverIncome;
    private double avgRevenuePercentage;
}
