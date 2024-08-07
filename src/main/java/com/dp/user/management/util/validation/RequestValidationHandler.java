package com.dp.user.management.util.validation;

import com.dp.user.management.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Component
public class RequestValidationHandler {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public ResponseEntity<?> handleInvalidRequest(BindingResult bindingResult) {
        // Log validation errors
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            logger.warn("Validation error: Field '{}', {}", fieldError.getField(), fieldError.getDefaultMessage());
        }

        // Return a custom error response
        return ResponseEntity.badRequest().body("Validation failed. Please check the input fields.");
    }

}
