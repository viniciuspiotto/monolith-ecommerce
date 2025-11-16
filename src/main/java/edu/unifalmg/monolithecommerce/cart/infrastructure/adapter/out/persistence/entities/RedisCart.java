package edu.unifalmg.monolithecommerce.cart.infrastructure.adapter.out.persistence.entities;

import edu.unifalmg.monolithecommerce.cart.domain.model.CartStatus;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Data
@Builder
@RedisHash(value = "cart")
public class RedisCart {

    @Id
    private UUID cartId;

    @Indexed
    private UUID customerId;

    @Indexed
    private CartStatus status;

    private Set<RedisCartItem> items;
    private Instant createdAt;
    private Instant updatedAt;

    @TimeToLive(unit = TimeUnit.SECONDS)
    private Long timeToLive;
}
