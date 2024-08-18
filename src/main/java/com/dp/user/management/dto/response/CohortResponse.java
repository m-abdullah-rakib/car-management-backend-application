package com.dp.user.management.dto.response;

import com.dp.user.management.dto.CohortData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CohortResponse {
    private List<CohortData> data;
}
