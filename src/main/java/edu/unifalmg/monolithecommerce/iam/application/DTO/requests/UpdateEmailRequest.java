package edu.unifalmg.monolithecommerce.iam.application.DTO.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record UpdateEmailRequest(
                @NotBlank(message = "Old Email is required.")
                @Pattern(
                        regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                        message = "Email address is not in a valid format"
                )
                String oldEmail,
                @NotBlank(message = "New Email is required.")
                @Pattern(
                        regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                        message = "Email address is not in a valid format"
                )
                String newEmail
        ){
}
