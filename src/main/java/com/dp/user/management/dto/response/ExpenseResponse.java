package com.dp.user.management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseResponse {
    private Long id;
    private String type; // "MAINTENANCE", "FUEL", etc.
    private int parkingDurationMin;
    private String fuelVendor;
    private int fuelVolume;
    private String fuelDetails;
}
