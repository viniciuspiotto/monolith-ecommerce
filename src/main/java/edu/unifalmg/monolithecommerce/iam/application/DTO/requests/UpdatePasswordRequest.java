package edu.unifalmg.monolithecommerce.iam.application.DTO.requests;

import edu.unifalmg.monolithecommerce.shared.infraestructure.web.validation.Password;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record UpdatePasswordRequest(
                @NotBlank(message = "Old Password is required")
                @Password
                String oldPassword,
                @NotBlank(message = "New Password is required")
                @Password
                String newPassword,
                @NotBlank(message = "Confirm of New Password is required")
                @Password
                String newConfirmPassword
        ){
}
