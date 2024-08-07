package com.dp.user.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FreeCarResponseData {
    private Integer id;
    private String model;
    private String license;
    private String image;
    private String status;
    private Long userId;
}
