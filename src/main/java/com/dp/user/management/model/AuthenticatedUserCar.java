package com.dp.user.management.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class AuthenticatedUserCar {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String model;
    private String license;
    private String image;
    private String status;
    private String licenseExpiryDate;
    private String insuranceNumber;
    private String insuranceExpiryDate;
    private String fitnessExpiryDate;
    private String nccLicenseExpiryDate;
    private String plateNumber;
    private Long userId;
}
