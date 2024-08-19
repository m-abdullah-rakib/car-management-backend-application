package com.dp.user.management.controller;

import com.dp.user.management.dto.request.ChangePasswordRequest;
import com.dp.user.management.dto.request.ResetPasswordSendEmailStepRequest;
import com.dp.user.management.service.JwtService;
import com.dp.user.management.service.PasswordService;
import com.dp.user.management.util.validation.RequestValidationHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/password")
@RequiredArgsConstructor
public class PasswordController {

    private final RequestValidationHandler requestValidationHandler;
    private final PasswordService passwordService;
    private final JwtService jwtService;

    @PatchMapping("/change")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest, @RequestHeader("Authorization") String token, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return requestValidationHandler.handleInvalidRequest(bindingResult);
        }

        String jwtToken = token.substring(7); // Remove "Bearer " prefix
        String email = jwtService.extractUsername(jwtToken);

        return passwordService.changePassword(changePasswordRequest, email);
    }

    @PostMapping("/reset/send-email-step")
    public ResponseEntity<?> resetPasswordSendEmailStep(@Valid @RequestBody ResetPasswordSendEmailStepRequest resetPasswordSendEmailStepRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return requestValidationHandler.handleInvalidRequest(bindingResult);
        }

        return passwordService.resetPasswordSendEmailStep(resetPasswordSendEmailStepRequest);
    }

}
