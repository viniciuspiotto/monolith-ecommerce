package edu.unifalmg.monolithecommerce.order.infratestructure.api;

import edu.unifalmg.monolithecommerce.shared.domain.model.Money;
import lombok.Getter;

import java.util.UUID;

@Getter
public class OrderItem {

    private final UUID id;
    private final String name;
    private final Money price;

    private OrderItem(UUID id, String name, Money price) {
        if (id == null || name == null) {
            throw new IllegalArgumentException("The id and name cannot be null");
        }

        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static OrderItem rehydrate(UUID id, String name, Money price) {
        return new OrderItem(id, name, price);
    }
}
