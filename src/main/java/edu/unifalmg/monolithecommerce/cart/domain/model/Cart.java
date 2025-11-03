package edu.unifalmg.monolithecommerce.cart.domain.model;

import edu.unifalmg.monolithecommerce.cart.domain.events.CartCheckoutEvent;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.api.ModelId;
import edu.unifalmg.monolithecommerce.shared.domain.model.Money;
import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
public class Cart extends AbstractAggregateRoot<Cart> {
    private final UUID cartId;

    private final UUID customerId;
    private final String sessionId;

    private final Set<CartItem> items = new HashSet<>();
    private CartStatus status;

    private final Instant createdAt;
    private Instant updatedAt;

    public Cart(UUID customerId, String sessionId) {
        if (customerId == null && sessionId == null) {
            throw new IllegalArgumentException("Customer must have be an owner (customer or session)");
        }
        if (customerId != null && sessionId != null) {
            throw new IllegalArgumentException("Cart cannot have both customer and session ID");
        }

        this.cartId = UUID.randomUUID();
        this.customerId = customerId;
        this.sessionId = sessionId;
        this.status = CartStatus.OPEN;
        this.createdAt = Instant.now();
        this.updatedAt = createdAt;
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
        if (this.items.isEmpty()) {
            throw new IllegalStateException("Cannot checkout an empty cart");
        }
        this.status = CartStatus.CHECKED_OUT;
        this.touch();

        this.registerEvent(new CartCheckoutEvent(
                this.cartId,
                this.customerId,
                Collections.unmodifiableSet(this.items),
                this.getTotalAmount(),
                this.updatedAt
        ));
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

    private Cart(
            UUID cartId,
            UUID customerId,
            String sessionId,
            Set<CartItem> items,
            CartStatus status,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.cartId = cartId;
        this.customerId = customerId;
        this.sessionId = sessionId;
        this.items.addAll(items);
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Cart rehydrate(
            UUID cartId,
            UUID customerId,
            String sessionId,
            Set<CartItem> items,
            CartStatus status,
            Instant createdAt,
            Instant updatedAt
    ) {
        return new Cart(cartId, customerId, sessionId, items, status, createdAt, updatedAt);
    }
}
