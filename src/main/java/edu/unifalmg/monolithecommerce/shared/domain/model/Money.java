package edu.unifalmg.monolithecommerce.shared.domain.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Currency;

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

    private Money(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Money add(Money other) {
        return new Money(amount.add(other.amount), currency);
    }

    public Money multiply(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        return new Money(this.amount.multiply(BigDecimal.valueOf(quantity)));
    }
}
