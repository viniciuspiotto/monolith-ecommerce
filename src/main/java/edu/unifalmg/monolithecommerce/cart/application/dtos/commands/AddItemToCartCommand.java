package edu.unifalmg.monolithecommerce.cart.application.dtos.commands;

import edu.unifalmg.monolithecommerce.catalog.infrastructure.api.ModelId;

import java.util.UUID;

public record AddItemToCartCommand(
        UUID customerId,
        ModelId modelId,
        int quantity
) {}
