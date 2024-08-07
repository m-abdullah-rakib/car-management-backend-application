package com.dp.user.management.dto.response;

import com.dp.user.management.dto.FreeCarResponseData;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class FreeCarsResponse {
    private List<FreeCarResponseData> data;
}

