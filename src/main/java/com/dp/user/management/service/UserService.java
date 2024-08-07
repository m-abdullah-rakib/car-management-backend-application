package com.dp.user.management.service;

import com.dp.user.management.dto.GetAuthenticatedUserData;
import com.dp.user.management.dto.GetAuthenticatedUserDataCar;
import com.dp.user.management.model.AuthenticatedUser;
import com.dp.user.management.model.AuthenticatedUserCar;
import com.dp.user.management.model.User;
import com.dp.user.management.repository.AuthenticatedUserRepository;
import com.dp.user.management.repository.UserRepository;
import com.dp.user.management.dto.request.LoginRequest;
import com.dp.user.management.dto.request.UserRegistrationRequest;
import com.dp.user.management.dto.response.AuthenticationResponse;
import com.dp.user.management.util.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticatedUserRepository authenticatedUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> register(UserRegistrationRequest registrationRequest) {

        try {
            // Validate if user with the same email already exists
            if (userRepository.existsByUsername(registrationRequest.email())) {
                return ResponseEntity.badRequest().body("Email is already registered.");
            }

            // Create a new user entity from the registration request
            User newUser = new User(registrationRequest.username(),
                    registrationRequest.email(),
                    passwordEncoder.encode(registrationRequest.password()),
                    Role.USER);

            // Save the new user
            userRepository.save(newUser);

            var jwtToken = jwtService.generateToken(newUser);

            return ResponseEntity.ok(
                    AuthenticationResponse
                            .builder()
                            .token(jwtToken)
                            .build()
            );
        } catch (Exception e) {
            // Handle registration errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register user.");
        }

    }

    public ResponseEntity<?> login(LoginRequest loginRequest) {

        try {
            // Retrieve user once
            var user = userRepository.findByUsername(loginRequest.email())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found."));

            // Create UsernamePasswordAuthenticationToken
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), loginRequest.password());

            // Authenticate user
            authenticationManager.authenticate(authenticationToken);

            // Generate JWT token
            var jwtToken = jwtService.generateToken(user);

            return ResponseEntity.ok(
                    AuthenticationResponse
                            .builder()
                            .token(jwtToken)
                            .build()
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    public ResponseEntity<?> getAuthenticatedUser(String email) {

        try {
            Optional<AuthenticatedUser> authenticatedUserOptional = authenticatedUserRepository.findByEmail(email);
            if (authenticatedUserOptional.isPresent()) {
                AuthenticatedUser authenticatedUser = authenticatedUserOptional.get();

                // Convert authenticatedUser entity to response DTO and return
                return ResponseEntity.ok(convertToAuthenticatedUserDataDto(authenticatedUser));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    private GetAuthenticatedUserData convertToAuthenticatedUserDataDto(AuthenticatedUser authenticatedUser) {
        return new GetAuthenticatedUserData(
                authenticatedUser.getId().intValue(),
                authenticatedUser.getName(),
                authenticatedUser.getEmail(),
                authenticatedUser.getRole().name(),
                authenticatedUser.getPhone(),
                authenticatedUser.getDateOfBirth(),
                authenticatedUser.getImage(),
                authenticatedUser.getDriverLicense(),
                authenticatedUser.getRevenuePercentage(),
                authenticatedUser.getDrivingLicenceExpiryDate(),
                authenticatedUser.getDrivingLicenceNumber(),
                authenticatedUser.getRouloFilePath(),
                authenticatedUser.getKbNumber(),
                authenticatedUser.getKbExpiryDate(),
                authenticatedUser.getProfessionalDriverLicense(),
                authenticatedUser.getStatus(),
                authenticatedUser.getAuthenticatedUserCar() != null ? convertToCarDto(authenticatedUser.getAuthenticatedUserCar()) : null
        );
    }

    private GetAuthenticatedUserDataCar convertToCarDto(AuthenticatedUserCar authenticatedUserCar) {
        return new GetAuthenticatedUserDataCar(
                authenticatedUserCar.getId(),
                authenticatedUserCar.getModel(),
                authenticatedUserCar.getLicense(),
                authenticatedUserCar.getImage(),
                authenticatedUserCar.getStatus(),
                authenticatedUserCar.getLicenseExpiryDate(),
                authenticatedUserCar.getInsuranceNumber(),
                authenticatedUserCar.getInsuranceExpiryDate(),
                authenticatedUserCar.getFitnessExpiryDate(),
                authenticatedUserCar.getNccLicenseExpiryDate(),
                authenticatedUserCar.getPlateNumber(),
                authenticatedUserCar.getUserId()
        );
    }
}
