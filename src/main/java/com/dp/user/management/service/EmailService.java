package com.dp.user.management.service;

import com.dp.user.management.util.exception.EmailSendException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void sendOtpInEmail(String to, String username) {
        try {
            String body = "Dear " + username + ",\n\n" +
                    "We received a request to reset the password for your account associated with this email address. " +
                    "To proceed, please use the One-Time Password (OTP) provided below:\n\n" +
                    "Your OTP: " + generateOtp() + "\n\n" +
                    "This OTP is valid for 10 minutes. Please do not share this OTP with anyone.\n\n" +
                    "If you did not request a password reset, please ignore this email or contact our support team immediately.\n\n" +
                    "Thank you,\n" +
                    "Secure Drive Support Team\n\n" +
                    "---\n" +
                    "Note: This is an automated message, please do not reply to this email.";

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Your OTP for Password Recovery");
            message.setText(body);
            message.setFrom("dynamicproject.org@gmail.com");

            javaMailSender.send(message);
        } catch (MailException e) {
            // Log the exception
            logger.error("Error sending email: {}", e.getMessage());

            // Optionally, rethrow the exception to be handled at a higher level
            throw new EmailSendException("Failed to send OTP email", e);
        }
    }

    private String generateOtp() {
        SecureRandom random = new SecureRandom();
        int otp = random.nextInt(9000) + 1000; // Generate a random number between 1000 and 9999
        return String.valueOf(otp);
    }

}
