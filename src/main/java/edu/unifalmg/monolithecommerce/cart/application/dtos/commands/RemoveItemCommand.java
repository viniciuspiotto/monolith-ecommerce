package edu.unifalmg.monolithecommerce.cart.application.dtos.commands;

import edu.unifalmg.monolithecommerce.catalog.infrastructure.api.ModelId;

import java.util.UUID;

public record RemoveItemCommand(
        UUID customerId,
        String sessionId,
        ModelId modelId
) {
}
