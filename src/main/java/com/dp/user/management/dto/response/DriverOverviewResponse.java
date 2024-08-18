package com.dp.user.management.dto.response;

import com.dp.user.management.dto.ExpenseSummary;
import com.dp.user.management.dto.IncomeSummary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverOverviewResponse {
    private ExpenseSummary expense;
    private IncomeSummary income;
    private double payment;
}
