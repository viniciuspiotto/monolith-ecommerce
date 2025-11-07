package edu.unifalmg.monolithecommerce.cart.infrastructure.adapter.in.dtos.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AddItemRequest(
    @NotNull(message = "ModelId cannot be null")
    UUID modelId,

    @Min(value = 1, message = "Quantity must be at least 1")
    int quantity
) {}
