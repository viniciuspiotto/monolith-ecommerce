package edu.unifalmg.monolithecommerce.iam.application.DTO.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record UpdateEmailRequest(
                @NotBlank(message = "Old Email is required.")
                @Email
                String oldEmail,

                @NotBlank(message = "New Email is required.")
                @Email
                String newEmail
        ){
}
