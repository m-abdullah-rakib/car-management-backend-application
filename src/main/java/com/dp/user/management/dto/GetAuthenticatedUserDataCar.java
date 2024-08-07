package com.dp.user.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAuthenticatedUserDataCar {
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
