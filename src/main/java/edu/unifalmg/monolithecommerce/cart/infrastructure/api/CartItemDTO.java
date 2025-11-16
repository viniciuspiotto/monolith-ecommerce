package edu.unifalmg.monolithecommerce.cart.infrastructure.api;

import edu.unifalmg.monolithecommerce.shared.domain.model.Money;

import java.util.UUID;

public record CartItemDTO (UUID modelId, Money unitPrice) { }
