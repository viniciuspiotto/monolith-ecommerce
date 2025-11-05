package edu.unifalmg.monolithecommerce.iam.application.DTO.requests;

import jakarta.validation.constraints.NotBlank;

public record CreateRoleRequest (
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Description is required")
        String description) {
}
