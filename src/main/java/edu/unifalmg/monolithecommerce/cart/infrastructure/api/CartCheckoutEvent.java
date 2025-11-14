package edu.unifalmg.monolithecommerce.cart.infrastructure.api;

import edu.unifalmg.monolithecommerce.cart.domain.model.CartItem;
import edu.unifalmg.monolithecommerce.shared.domain.model.Money;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record CartCheckoutEvent(
        UUID cartId,
        UUID customerId,
        Set<CartItemDTO> items,
        Money totalAmount,
        Instant checkoutAt
) {
}
