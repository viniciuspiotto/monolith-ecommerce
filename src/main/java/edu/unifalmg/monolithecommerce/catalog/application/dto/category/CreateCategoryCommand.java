package edu.unifalmg.monolithecommerce.catalog.application.dto.category;

public record CreateCategoryCommand(
        String name,
        String description
) {
}
