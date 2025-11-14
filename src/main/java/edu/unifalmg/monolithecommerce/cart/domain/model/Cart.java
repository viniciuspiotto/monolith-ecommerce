package edu.unifalmg.monolithecommerce.cart.domain.model;

import edu.unifalmg.monolithecommerce.cart.domain.events.CartCheckoutEvent;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.api.ModelId;
import edu.unifalmg.monolithecommerce.shared.domain.model.Money;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.io.Serializable;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class Cart implements Serializable {
    private final UUID cartId;

    private UUID customerId;

    @Builder.Default
    private final Set<CartItem> items = new HashSet<>();
    private CartStatus status;

    private final Instant createdAt;
    private Instant updatedAt;

    public static Cart create(UUID customerId) {
        Instant now = Instant.now();

        return Cart.builder()
                .cartId(UUID.randomUUID())
                .customerId(customerId)
                .status(CartStatus.OPEN)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public static Cart create() {
        Instant now = Instant.now();

        return Cart.builder()
                .cartId(UUID.randomUUID())
                .status(CartStatus.OPEN)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public void addItem(ModelId modelId, Money unitPrice, int quantity) {
        if (this.status != CartStatus.OPEN) {
            throw new IllegalStateException("Cannot add items to a non-open cart");
        }

        CartItem newItem = new CartItem(modelId, unitPrice, quantity);

        this.items.remove(newItem);
        this.items.add(newItem);
        this.touch();
    }

    public void updateItemQuantity(ModelId modelId, int newQuantity) {
        if (newQuantity <= 0) {
            this.removeItem(modelId);
            return;
        }

        CartItem item = findItem(modelId);
        item.setQuantity(newQuantity);
        this.touch();
    }

    public void merge(Cart otherCart) {
        if (otherCart == null || otherCart.getItems().isEmpty()) {
            throw new IllegalStateException("Cannot merge null or empty cart");
        }

        Map<ModelId, CartItem> currentItemsCart = this.items.stream()
                .collect(Collectors.toMap(CartItem::getModelId, Function.identity()));

        for (CartItem item : otherCart.getItems()) {
            CartItem existingItem = currentItemsCart.get(item.getModelId());

            if (existingItem != null) {
                existingItem.setQuantity(item.getQuantity());
            } else {
                this.items.add(item);
            }
        }

        this.touch();
    }

    public void assignToCustomer(UUID customerId) {
        if (this.customerId != null) {
            throw new IllegalStateException("Cannot assign customer to a non-existing cart");
        }
        if (customerId == null) {
            throw new IllegalArgumentException("Customer ID cannot be null");
        }
        this.customerId = customerId;
        this.touch();
    }

    public void clearCart() {
        this.items.clear();
        this.touch();
    }

    public Money getTotalAmount() {
        return this.items.stream()
                .map(CartItem::getTotal)
                .reduce(Money.ZERO, Money::add);
    }

    public void checkout() {
        if (this.customerId == null) {
            throw new IllegalStateException("Cannot checkout an anonymous cart. Must be assigned to customer");
        }
        if (this.items.isEmpty()) {
            throw new IllegalStateException("Cannot checkout an empty cart");
        }
        this.status = CartStatus.CHECKED_OUT;
        this.touch();
    }

    private CartItem findItem(ModelId modelId) {
        return this.items.stream()
                .filter(item -> item.getModelId().equals(modelId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Cannot find item with id " + modelId));
    }

    public void removeItem(ModelId modelId) {
        if (this.status != CartStatus.OPEN) {
            throw new IllegalStateException("Cannot remove items from a non-open cart");
        }
        boolean removed = this.items.removeIf(item -> item.getModelId().equals(modelId));

        if (removed) {
            this.touch();
        }
    }

    private void touch() {
        this.updatedAt = Instant.now();
    }

    public static Cart rehydrate(
            UUID cartId,
            UUID customerId,
            Set<CartItem> items,
            CartStatus status,
            Instant createdAt,
            Instant updatedAt
    ) {
        Cart cart = Cart.builder()
                .cartId(cartId)
                .customerId(customerId)
                .status(status)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();

        if (items != null) {
            for (CartItem item : items) {
                cart.getItems().add(item);
            }
        }

        return cart;
    }
}
