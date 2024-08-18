package com.dp.user.management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "RecentTransactions")
public class RecentTransactions {

    @Id
    private Long id;

    private String date;
    private Double amount;
    private String remark;

    @Column(name = "invoice_file_path")
    private String invoiceFilePath;

    private double lat;
    private double lon;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "car_id")
    private Integer carId;

    private String status;

    private String type;
    private Double tips;
    private Double offer;

    @Column(name = "parking_duration_min")
    private Integer parkingDurationMin;

    @Column(name = "fuel_vendor")
    private String fuelVendor;

    @Column(name = "fuel_volume")
    private Integer fuelVolume;

    @Column(name = "fuel_details")
    private String fuelDetails;

    private String source; // 'INCOME' or 'EXPENSE'
}

