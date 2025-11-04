package edu.unifalmg.monolithecommerce.iam.application.DTO.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LoginRequest (
        @NotBlank(message = "Email is required.")
        @Pattern(
                regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                message = "Email address is not in a valid format"
        )
        String email,
        @NotBlank(message = "Password is required.")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "Password must be at least 8 characters long, with uppercase, lowercase, number, and special character"
        )
        String password
){
}
