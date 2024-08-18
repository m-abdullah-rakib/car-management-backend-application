package com.dp.user.management.controller;

import com.dp.user.management.dto.response.CohortResponse;
import com.dp.user.management.service.CohortService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cohort")
public class CohortController {

    private final CohortService cohortService;

    @GetMapping("/{cohortYear}")
    public ResponseEntity<CohortResponse> getCohort(@PathVariable int cohortYear) {
        return cohortService.getCohort(cohortYear);
    }

}
