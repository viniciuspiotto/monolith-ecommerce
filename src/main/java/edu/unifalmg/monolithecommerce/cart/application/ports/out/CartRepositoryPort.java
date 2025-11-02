package edu.unifalmg.monolithecommerce.cart.application.ports.out;

import edu.unifalmg.monolithecommerce.cart.domain.model.Cart;

import java.util.Optional;
import java.util.UUID;

public interface CartRepositoryPort {
    Optional<Cart> findByCustomerIdAndStatusOpen(UUID customerId);
    Optional<Cart> findBySessionIdAndStatusOpen(String sessionId);
    Cart save(Cart cart);
}
