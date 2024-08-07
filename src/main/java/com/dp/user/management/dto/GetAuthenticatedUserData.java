package com.dp.user.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAuthenticatedUserData {
    private Integer id;
    private String name;
    private String email;
    private String role;
    private String phone;
    private String dateOfBirth;
    private String image;
    private String driverLicense;
    private Integer revenuePercentage;
    private String drivingLicenceExpiryDate;
    private String drivingLicenceNumber;
    private String rouloFilePath;
    private String kbNumber;
    private String kbExpiryDate;
    private String professionalDriverLicense;
    private String status;
    private GetAuthenticatedUserDataCar car;
}
