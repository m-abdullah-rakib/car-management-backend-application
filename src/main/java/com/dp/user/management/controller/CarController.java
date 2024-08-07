package com.dp.user.management.controller;

import com.dp.user.management.dto.request.ChangeCarRequest;
import com.dp.user.management.dto.response.ChangeCarResponse;
import com.dp.user.management.dto.response.FreeCarsResponse;
import com.dp.user.management.service.CarService;
import com.dp.user.management.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/car")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;
    private final JwtService jwtService;

    @GetMapping("/free-cars")
    public ResponseEntity<FreeCarsResponse> getFreeCars() {
        return carService.getFreeCars();
    }

    @PatchMapping("/change-car")
    public ResponseEntity<ChangeCarResponse> changeCar(@RequestBody ChangeCarRequest changeCarRequest, @RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7); // Remove "Bearer " prefix
        String email = jwtService.extractUsername(jwtToken);

        return carService.changeCar(email, changeCarRequest.carId());
    }
}
