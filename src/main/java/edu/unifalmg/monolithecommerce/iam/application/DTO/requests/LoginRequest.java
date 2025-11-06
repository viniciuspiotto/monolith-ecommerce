package edu.unifalmg.monolithecommerce.iam.application.DTO.requests;

import edu.unifalmg.monolithecommerce.shared.infraestructure.web.validation.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest (
        @NotBlank(message = "Email is required.")
        @Email
        String email,

        @NotBlank(message = "Password is required.")
        @Password
        String password
){
}
