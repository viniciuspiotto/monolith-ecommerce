package edu.unifalmg.monolithecommerce.order.application.dto.commands;

import edu.unifalmg.monolithecommerce.order.domain.model.enums.OrderStatus;

import java.util.UUID;

public record UpdateOrderStatusCommand (UUID orderId, OrderStatus orderStatus){
}
