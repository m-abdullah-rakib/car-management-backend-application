package com.dp.user.management.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String date;
    private Double amount;
    private String remark;
    private String invoiceFilePath;
    private double lat;
    private double lon;
    private Long userId;
    private Integer carId;
    private String status = "PENDING";

    // Expense-specific fields
    private String type; // MAINTENANCE, FUEL, etc.
    private int parkingDurationMin;
    private String fuelVendor;
    private int fuelVolume;
    private String fuelDetails;
}
