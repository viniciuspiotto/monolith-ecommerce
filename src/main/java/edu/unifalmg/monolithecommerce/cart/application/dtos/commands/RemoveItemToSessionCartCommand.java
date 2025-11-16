package edu.unifalmg.monolithecommerce.cart.application.dtos.commands;

import edu.unifalmg.monolithecommerce.catalog.infrastructure.api.ModelId;

public record RemoveItemToSessionCartCommand(
        ModelId modelId
) {}
