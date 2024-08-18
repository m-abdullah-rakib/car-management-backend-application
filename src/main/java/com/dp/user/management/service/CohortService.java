package com.dp.user.management.service;

import com.dp.user.management.dto.CohortData;
import com.dp.user.management.dto.response.CohortResponse;
import com.dp.user.management.model.Cohort;
import com.dp.user.management.repository.CohortRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CohortService {

    private final CohortRepository cohortRepository;

    public ResponseEntity<CohortResponse> getCohort(int cohortYear) {
        List<Cohort> cohortList = cohortRepository.findAllByYear(cohortYear);
        List<CohortData> cohortDataList = cohortList.stream()
                .map(this::convertToCohortDataDto)
                .toList();

        return ResponseEntity.ok(new CohortResponse(cohortDataList));
    }

    private CohortData convertToCohortDataDto(Cohort cohort) {
        return new CohortData(
                cohort.getMonth(),
                cohort.getStartDate(),
                cohort.getEndDate()
        );
    }

}
