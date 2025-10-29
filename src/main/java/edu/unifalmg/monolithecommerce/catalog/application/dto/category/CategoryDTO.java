package edu.unifalmg.monolithecommerce.catalog.application.dto.category;

import java.util.UUID;

public record CategoryDTO(
        UUID categoryId,
        String name,
        String description
) {
}
