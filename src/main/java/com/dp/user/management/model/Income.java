package com.dp.user.management.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Income {
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

    // Income-specific fields
    private String type; // CASH, CREDIT, etc.
    private Double tips;
    private Double offer;
}
