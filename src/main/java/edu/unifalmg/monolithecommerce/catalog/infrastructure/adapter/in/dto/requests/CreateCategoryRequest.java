package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.in.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCategoryRequest(
        @NotBlank(message = "Category name is required")
        @Size(min = 3, max = 100, message = "Category name must be up to 255 characters")
        String name,

        @NotBlank(message = "Category description is required")
        @Size(max = 255, message = "Category description must be up to 255 characters")
        String description
) {
}
