package edu.unifalmg.monolithecommerce.cart.infrastructure.adapter.in.dtos.requests;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RemoveItemRequest(
        @NotNull(message = "ModelId cannot be null")
        UUID modelId
) {}
