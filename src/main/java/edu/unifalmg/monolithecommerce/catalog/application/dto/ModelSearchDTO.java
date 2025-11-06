package edu.unifalmg.monolithecommerce.catalog.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ModelSearchDTO(
        String id,
        String title,
        String description,
        BigDecimal price,
        UUID categoryId,
        Double averageRate,
        String thumbnailUrl
) {
}
