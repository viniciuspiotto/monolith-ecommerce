package edu.unifalmg.monolithecommerce.order.domain.model;

import edu.unifalmg.monolithecommerce.order.domain.model.enums.OrderStatus;
import edu.unifalmg.monolithecommerce.order.infratestructure.api.OrderId;
import edu.unifalmg.monolithecommerce.order.infratestructure.api.OrderItem;
import edu.unifalmg.monolithecommerce.shared.domain.model.Money;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class Order {

    private UUID cartId;
    private OrderId orderId;
    private UUID customerId;
    private OrderStatus orderStatus;
    private Money totalAmount;
    private Date createdAt;
    private final List<OrderItem> orderItems = new ArrayList<>();

    public static Order create (UUID cartId, UUID customerId, Money totalAmount){

        if(cartId == null){
            throw new IllegalArgumentException("Card id cannot be null or blank");
        }

        if(customerId == null){
            throw new IllegalArgumentException("Customer cannot be null or blank");
        }

        return Order.builder()
                .cartId(cartId)
                .orderId(new OrderId(UUID.randomUUID()))
                .orderStatus(OrderStatus.PROCESSING)
                .customerId(customerId)
                .totalAmount(totalAmount)
                .createdAt(new Date())
                .build();

    }

    public void changeOrderStatus (OrderStatus orderStatus){
        if(orderStatus == null){
            throw new IllegalArgumentException("Order Status cannot be null or blank");
        }
        this.orderStatus = orderStatus;
    }

    public void addItem (UUID id, String name, Money value){
        if (id == null || name == null) {
            throw new IllegalArgumentException("The id and name cannot be null");
        }

        OrderItem orderItem = OrderItem.rehydrate(id, name, value);
        this.orderItems.add(orderItem);
    }

    public static Order rehydrate(
            UUID cartId,
            OrderId orderId,
            UUID customerId,
            OrderStatus orderStatus,
            Money totalAmount,
            Date createdAt,
            List<OrderItem> orderItems
    ) {
        Order order = Order.builder()
                .cartId(cartId)
                .orderId(orderId)
                .customerId(customerId)
                .orderStatus(orderStatus)
                .totalAmount(totalAmount)
                .createdAt(createdAt)
                .build();

        if (orderItems != null) {
            for (OrderItem item : orderItems) {
                order.getOrderItems().add(item);
            }
        }

        return order;
    }

}