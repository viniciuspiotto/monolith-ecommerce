package edu.unifalmg.monolithecommerce.shared.domain.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

@Getter
public class Money {
    private static final Currency BRL = Currency.getInstance("BRL");
    public static final Money ZERO = new Money(BigDecimal.ZERO);

    private final BigDecimal amount;
    private final Currency currency;

    public Money(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be null or negative");
        }
        this.amount = amount;
        this.currency = BRL;
    }

    public Money add(Money other) {
        if (!Objects.equals(this.currency, other.currency)) {
            throw new IllegalArgumentException("Cannot add Money of different currencies");
        }
        return new Money(this.amount.add(other.amount));
    }

    public Money multiply(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        return new Money(this.amount.multiply(BigDecimal.valueOf(quantity)));
    }
}
