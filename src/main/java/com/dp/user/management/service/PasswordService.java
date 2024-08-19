package com.dp.user.management.service;

import com.dp.user.management.dto.request.ChangePasswordRequest;
import com.dp.user.management.dto.request.ResetPasswordSendEmailStepRequest;
import com.dp.user.management.dto.response.PasswordCommonResponse;
import com.dp.user.management.model.User;
import com.dp.user.management.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PasswordService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> changePassword(ChangePasswordRequest changePasswordRequest, String email) {
        User user;

        try {
            user = userRepository.findByUsername(email).orElseThrow(() -> new UsernameNotFoundException("User not found."));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password.");
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        // Check if the provided old password matches the current password
        if (passwordEncoder.matches(changePasswordRequest.password(), user.getPassword())) {
            // If it matches, update the user's password in the database
            user.setPassword(passwordEncoder.encode(changePasswordRequest.newPassword()));
            userRepository.save(user);

            return ResponseEntity.ok(new PasswordCommonResponse("Password updated."));
        } else {
            // If the passwords don't match
            return ResponseEntity.badRequest().body(new PasswordCommonResponse("Incorrect password."));
        }
    }

    public ResponseEntity<?> resetPasswordSendEmailStep(ResetPasswordSendEmailStepRequest resetPasswordSendEmailStepRequest) {
        User user;

        try {
            user = userRepository.findByUsername(resetPasswordSendEmailStepRequest.email()).orElseThrow(() -> new UsernameNotFoundException("User not found."));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        // todo: Send email to this valid user

        return ResponseEntity.ok(new PasswordCommonResponse("An email has been sent to your account. Please check your inbox for further instructions."));
    }
}
