package edu.unifalmg.monolithecommerce.iam.application.DTO.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record UpdatePasswordRequest(
                @NotBlank(message = "Old Password is required")
                @Pattern(
                        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                        message = "Old Password must be at least 8 characters long, with uppercase, lowercase, number, and special character"
                )
                String oldPassword,
                @NotBlank(message = "New Password is required")
                @Pattern(
                        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                        message = "New Password must be at least 8 characters long, with uppercase, lowercase, number, and special character"
                )
                String newPassword,
                @NotBlank(message = "Confirm of New Password is required")
                @Pattern(
                        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                        message = "Confirm of New Password must be at least 8 characters long, with uppercase, lowercase, number, and special character"
                )
                String newConfirmPassword
        ){
}
