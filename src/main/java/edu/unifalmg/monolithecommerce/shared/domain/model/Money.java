package edu.unifalmg.monolithecommerce.shared.domain.model;

import java.math.BigDecimal;
import java.util.Currency;

public class Money {
    private static final Currency BRL = Currency.getInstance("BRL");

    BigDecimal amount;
    Currency currency;

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
}
