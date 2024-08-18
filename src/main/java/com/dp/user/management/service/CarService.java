package com.dp.user.management.service;

import com.dp.user.management.dto.FreeCarResponseData;
import com.dp.user.management.dto.response.ChangeCarResponse;
import com.dp.user.management.dto.response.FreeCarsResponse;
import com.dp.user.management.model.AuthenticatedUser;
import com.dp.user.management.model.AuthenticatedUserCar;
import com.dp.user.management.repository.AuthenticatedUserRepository;
import com.dp.user.management.repository.AuthenticatedUserCarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarService {

    private final AuthenticatedUserCarRepository authenticatedUserCarRepository;
    private final AuthenticatedUserRepository authenticatedUserRepository;

    public ResponseEntity<FreeCarsResponse> getFreeCars() {
        List<AuthenticatedUserCar> freeCars = authenticatedUserCarRepository.findByUserIdIsNull();
        List<FreeCarResponseData> freeCarResponseDataList = freeCars.stream()
                .map(this::convertToFreeCarResponseDataDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new FreeCarsResponse(freeCarResponseDataList));
    }

    private FreeCarResponseData convertToFreeCarResponseDataDto(AuthenticatedUserCar authenticatedUserCar) {
        return new FreeCarResponseData(
                authenticatedUserCar.getId(),
                authenticatedUserCar.getModel(),
                authenticatedUserCar.getLicense(),
                authenticatedUserCar.getImage(),
                authenticatedUserCar.getStatus(),
                authenticatedUserCar.getUserId()
        );
    }

    @Transactional
    public ResponseEntity<ChangeCarResponse> changeCar(String username, Integer newCarId) {
        AuthenticatedUser authenticatedUser = authenticatedUserRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        AuthenticatedUserCar newCar = authenticatedUserCarRepository.findById(newCarId)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        // Set the old car's userId to null
        if (authenticatedUser.getAuthenticatedUserCar() != null) {
            AuthenticatedUserCar oldCar = authenticatedUser.getAuthenticatedUserCar();
            oldCar.setUserId(null);
            authenticatedUserCarRepository.save(oldCar);
        }

        // Update the authenticatedUser with the new car
        authenticatedUser.setAuthenticatedUserCar(newCar);
        authenticatedUserRepository.save(authenticatedUser);

        // Update the new car with the userId
        newCar.setUserId(authenticatedUser.getId());
        authenticatedUserCarRepository.save(newCar);

        return ResponseEntity.ok(new ChangeCarResponse("Car changed successfully."));
    }
}
