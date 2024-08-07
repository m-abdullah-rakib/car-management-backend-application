package com.dp.user.management.controller;

import com.dp.user.management.dto.request.LoginRequest;
import com.dp.user.management.dto.request.UserRegistrationRequest;
import com.dp.user.management.service.JwtService;
import com.dp.user.management.service.UserService;
import com.dp.user.management.util.validation.RequestValidationHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RequestValidationHandler requestValidationHandler;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationRequest registrationRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return requestValidationHandler.handleInvalidRequest(bindingResult);
        }

        return userService.register(registrationRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return requestValidationHandler.handleInvalidRequest(bindingResult);
        }

        return userService.login(loginRequest);
    }

    @GetMapping("/authenticated/user")
    public ResponseEntity<?> getAuthenticatedUser(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7); // Remove "Bearer " prefix
        String email = jwtService.extractUsername(jwtToken);

        return userService.getAuthenticatedUser(email);
    }

}
