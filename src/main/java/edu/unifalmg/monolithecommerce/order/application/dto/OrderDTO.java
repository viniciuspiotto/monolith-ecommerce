package edu.unifalmg.monolithecommerce.order.application.dto;

import edu.unifalmg.monolithecommerce.order.domain.model.enums.OrderStatus;
import edu.unifalmg.monolithecommerce.order.infratestructure.api.OrderItem;
import edu.unifalmg.monolithecommerce.shared.domain.model.Money;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record OrderDTO (
        UUID orderId,
        UUID customerId,
        OrderStatus orderStatus,
        Money totalAmount,
        Date createdAt,
        List<OrderItem> orderItems
){
    public record OrderItemDTO (
            UUID id,
            String name,
            Money price
    ){}

}
