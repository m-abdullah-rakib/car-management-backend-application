package com.dp.user.management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncomeResponse {
    private Long id;
    private String type; // "CASH", "CREDIT", etc.
    private Double tips;
    private Double offer;
    private Long userId;
    private Integer carId;
}
