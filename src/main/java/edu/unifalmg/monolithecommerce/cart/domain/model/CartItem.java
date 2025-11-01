package edu.unifalmg.monolithecommerce.cart.domain.model;

import edu.unifalmg.monolithecommerce.shared.domain.model.Money;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem {
    private final ModelId modelId;
    private int quantity;
    private final Money unitPrice;

    protected CartItem(ModelId productId, Money unitPrice, int quantity) {
        if (productId == null || unitPrice == null) {
            throw new IllegalArgumentException("Model and price must not be null");
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
}
