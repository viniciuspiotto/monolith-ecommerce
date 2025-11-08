package edu.unifalmg.monolithecommerce.cart.application.dtos;

import edu.unifalmg.monolithecommerce.catalog.infrastructure.api.ModelId;
import edu.unifalmg.monolithecommerce.shared.domain.model.Money;

import java.util.Set;
import java.util.UUID;

public record CartDTO(
        UUID cartId,
        String status,
        Set<CartItemDTO> items,
        Money totalAmount
) {
    public record CartItemDTO(
            ModelId modelId,
            Money unitPrice,
            int quantity,
            Money total
    ) {}
}
