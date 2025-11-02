package edu.unifalmg.monolithecommerce.cart.infrastructure.adapter.in.dtos.requests;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AddItemRequest(
    UUID customerId,

    String sessionId,

    @NotNull(message = "ModelId cannot be null")
    UUID modelId,

    @Min(value = 1, message = "Quantity must be at least 1")
    int quantity
) {
    @AssertTrue(message = "Either customerId or sessionId must be provided, but not both")
    private boolean isCustomerIdOrSessionIdExclusive() {
        return (customerId != null) ^ (sessionId != null);
    }
}
