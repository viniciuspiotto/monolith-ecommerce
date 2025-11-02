package edu.unifalmg.monolithecommerce.cart.application.dtos.commands;

import edu.unifalmg.monolithecommerce.catalog.infrastructure.api.ModelId;

import java.util.UUID;

public record AddItemCommand(
        UUID customerId,
        String sessionId,

        ModelId modelId,
        int quantity
) {
}
