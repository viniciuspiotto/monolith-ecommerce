package edu.unifalmg.monolithecommerce.order.application.port.out;

import edu.unifalmg.monolithecommerce.order.domain.model.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepositoryPort {
    Order findById(UUID orderId);
    Optional<Order> findByCartId(UUID cartId);
    Order save(Order order);
}
