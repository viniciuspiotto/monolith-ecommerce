package edu.unifalmg.monolithecommerce.catalog.application.dto;

public record CreateCategoryCommand(
        String name,
        String description
) {
}
