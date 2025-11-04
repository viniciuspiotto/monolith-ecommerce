package edu.unifalmg.monolithecommerce.iam.application.DTO.requests;

import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.CreateUserCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record CreateUserRequest (
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Last Name is required")
        String lastName,
        @NotBlank(message = "Email is required")
        @Pattern(
                regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                message = "Email address is not in a valid format"
        )
        String email,
        @NotBlank(message = "Password is required")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "Password must be at least 8 characters long, with uppercase, lowercase, number, and special character"
        )
        String password,
        @NotBlank(message = "Confirm Password is required")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "Password must be at least 8 characters long, with uppercase, lowercase, number, and special character"
        )
        String confirmPassword,
        @NotBlank(message = "National Id is required")
        String nationalId,
        @NotNull(message = "Address is required")
        Address address
){
    public record Address(
            @NotNull(message = "Country is required")
            @NotBlank(message = "Country cannot be blank.")
            String country,
            @NotNull(message = "City is required")
            @NotBlank(message = "City cannot be blank.")
            String city,
            @NotNull(message = "State is required")
            @NotBlank(message = "State cannot be blank.")
            String state,
            @NotNull(message = "Zip is required")
            @NotBlank(message = "Zip cannot be blank.")
            String zip,
            @NotNull(message = "Street is required")
            @NotBlank(message = "Street cannot be blank.")
            String street,
            @NotNull(message = "Number is required")
            @NotBlank(message = "Number cannot be blank.")
            Integer number,
            String neighborhood,
            String complement) {}
}
