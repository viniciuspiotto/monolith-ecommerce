package edu.unifalmg.monolithecommerce.order.application.dto.commands;

import edu.unifalmg.monolithecommerce.shared.domain.model.Money;

import java.util.List;
import java.util.UUID;

public record CreateOrderCommand (
        UUID cartId,
        UUID customerId,
        Money totalAmount,
        List<OrderItemCommand> orderItemCommandList
) {
    public record OrderItemCommand (
            UUID modelId,
            Money value) {}
}
