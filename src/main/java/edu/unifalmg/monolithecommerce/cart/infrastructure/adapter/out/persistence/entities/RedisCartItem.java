package edu.unifalmg.monolithecommerce.cart.infrastructure.adapter.out.persistence.entities;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class RedisCartItem {
    private UUID modelId;
    private int quantity;
    private BigDecimal unitPrice;
}
