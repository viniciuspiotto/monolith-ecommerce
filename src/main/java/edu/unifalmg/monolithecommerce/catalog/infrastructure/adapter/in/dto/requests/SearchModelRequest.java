package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.in.dto.requests;

import java.util.UUID;

public record SearchModelRequest(
        String textQuery,
        UUID categoryId,
        Double minPrice,
        Double maxPrice,
        Double minRate
) {
}
