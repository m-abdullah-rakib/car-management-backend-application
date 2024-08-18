package com.dp.user.management.controller;

import com.dp.user.management.service.JwtService;
import com.dp.user.management.service.LedgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/ledger")
public class LedgerController {

    private final JwtService jwtService;
    private final LedgerService ledgerService;

    @GetMapping
    public ResponseEntity<?> getLedger(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7); // Remove "Bearer " prefix
        String email = jwtService.extractUsername(jwtToken);

        return ledgerService.getLedger(email);
    }

    @GetMapping("/driver-overview")
    public ResponseEntity<?> driverOverview(@RequestParam String start, @RequestParam String end, @RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7); // Remove "Bearer " prefix
        String email = jwtService.extractUsername(jwtToken);

        return ledgerService.getDriverOverview(email, start, end);
    }

}
