package com.dp.user.management.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ExpenseRequest(@NotBlank(message = "Income type is required") String type,
                             @NotBlank(message = "Date is required") String date,
                             @NotNull(message = "Amount is required") @Min(value = 1, message = "Amount must be greater than 0") Double amount,
                             String remark, int parkingDurationMin, String fuelVendor, int fuelVolume,
                             String fuelDetails, String invoiceFilePath, Long carId, Double lat, Double lon) {
}
