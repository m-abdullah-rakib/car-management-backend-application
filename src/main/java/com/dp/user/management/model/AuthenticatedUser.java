package com.dp.user.management.model;

import com.dp.user.management.util.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class AuthenticatedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
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
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id")
    private AuthenticatedUserCar authenticatedUserCar;
}
