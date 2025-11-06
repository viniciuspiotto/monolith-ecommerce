package edu.unifalmg.monolithecommerce.catalog.application.dto.commands;

public record CreateCategoryCommand(
        String name,
        String description
) {
}
