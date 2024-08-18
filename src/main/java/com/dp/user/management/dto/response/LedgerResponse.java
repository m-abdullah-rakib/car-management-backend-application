package com.dp.user.management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LedgerResponse {
    private List<TransactionResponse> data;
}
