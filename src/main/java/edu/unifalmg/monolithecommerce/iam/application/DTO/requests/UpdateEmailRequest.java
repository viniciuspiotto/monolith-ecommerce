package edu.unifalmg.monolithecommerce.iam.application.DTO.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateEmailRequest(
        @NotBlank(message = "Password to confirm is required.")
        String passwordToConfirm,

        @NotBlank(message = "Old Email is required.")
        @Email
        String oldEmail,

        @NotBlank(message = "New Email is required.")
        @Email
        String newEmail
    ){
}
