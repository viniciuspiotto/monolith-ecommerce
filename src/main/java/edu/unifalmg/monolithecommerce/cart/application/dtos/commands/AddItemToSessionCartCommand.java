package edu.unifalmg.monolithecommerce.cart.application.dtos.commands;

import edu.unifalmg.monolithecommerce.catalog.infrastructure.api.ModelId;

public record AddItemToSessionCartCommand(
        ModelId modelId,
        int quantity
) {}
