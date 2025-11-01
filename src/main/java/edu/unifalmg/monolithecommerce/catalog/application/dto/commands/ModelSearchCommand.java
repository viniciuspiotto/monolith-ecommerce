package edu.unifalmg.monolithecommerce.catalog.application.dto.commands;

import java.util.UUID;

public record ModelSearchCommand(
        String stringText,

        UUID categoryId,

        Double minPrice,
        Double maxPrice,

        Double minRate
) {
}
