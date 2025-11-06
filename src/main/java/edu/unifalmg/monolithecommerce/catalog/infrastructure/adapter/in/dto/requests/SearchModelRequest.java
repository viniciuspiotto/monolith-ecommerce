package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.in.dto.requests;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record SearchModelRequest(
        @Size(max = 255)
        String q,
        UUID categoryId,

        @DecimalMin(value = "0.0", message = "Minimum price must be high or equal to 0.0")
        Double minPrice,

        @DecimalMin(value = "0.0", message = "Maximum price must be high or equal to 0.0")
        Double maxPrice,

        @DecimalMin(value = "0.0", message = "Minimum rate must be high or equal to 0.0")
        @DecimalMax(value = "5.0", message = "Minimum rate don't be high to 5")
        Double minRate
) {
    @AssertTrue(message = "Maximum price must be high to minimum price")
    public boolean isPriceRangeValid() {
        if (minPrice == null || maxPrice == null) {
            return true;
        }
        return maxPrice > minPrice;
    }
}
