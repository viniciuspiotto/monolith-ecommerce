package edu.unifalmg.monolithecommerce.cart.infrastructure.adapter.out.persistence.redis;


import edu.unifalmg.monolithecommerce.cart.domain.model.CartStatus;
import edu.unifalmg.monolithecommerce.cart.infrastructure.adapter.out.persistence.entities.RedisCart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRedisRepository extends CrudRepository<RedisCart, UUID> {
    Optional<RedisCart> findByCustomerIdAndStatus(UUID customerId, CartStatus status);
}
