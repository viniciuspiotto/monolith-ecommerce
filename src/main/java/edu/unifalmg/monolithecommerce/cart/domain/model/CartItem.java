package edu.unifalmg.monolithecommerce.cart.domain.model;

import edu.unifalmg.monolithecommerce.catalog.infrastructure.api.ModelId;
import edu.unifalmg.monolithecommerce.shared.domain.model.Money;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CartItem {
    private final ModelId modelId;
    private int quantity;
    private final Money unitPrice;

    protected CartItem(ModelId productId, Money unitPrice, int quantity) {
        if (productId == null || unitPrice == null) {
            throw new IllegalArgumentException("Model or price must not be null");
        }
        if  (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        this.modelId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    protected void setQuantity(int newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        this.quantity = newQuantity;
    }

    protected Money getTotal() {
        return this.unitPrice.multiply(this.quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return modelId.equals(cartItem.modelId);
    }

    @Override
    public int hashCode() {
        return modelId.hashCode();
    }

    public static CartItem rehydrate(UUID modelId, Money unitPrice, int quantity) {
        return new CartItem(new ModelId(modelId), unitPrice, quantity);
    }
}
