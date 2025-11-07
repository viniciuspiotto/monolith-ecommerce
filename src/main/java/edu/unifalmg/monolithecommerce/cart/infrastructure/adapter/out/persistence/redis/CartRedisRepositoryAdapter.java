package edu.unifalmg.monolithecommerce.cart.infrastructure.adapter.out.persistence.redis;

import edu.unifalmg.monolithecommerce.cart.application.ports.out.CartRepositoryPort;
import edu.unifalmg.monolithecommerce.cart.domain.model.Cart;
import edu.unifalmg.monolithecommerce.cart.domain.model.CartStatus;
import edu.unifalmg.monolithecommerce.cart.infrastructure.adapter.out.persistence.entities.RedisCart;
import edu.unifalmg.monolithecommerce.cart.infrastructure.adapter.out.persistence.mappers.CartPersistenceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Log4j2
@Component
@RequiredArgsConstructor
public class CartRedisRepositoryAdapter implements CartRepositoryPort {
    private final CartRedisRepository redisRepository;

    private final CartPersistenceMapper mapper;

    @Override
    public Optional<Cart> findByCustomerIdAndStatusOpen(UUID customerId) {
        log.info("Finding cart by customerId: {}", customerId);

        Optional<RedisCart> entity = redisRepository.findByCustomerIdAndStatus(
                customerId, CartStatus.OPEN
        );

        return entity.map(mapper::toDomain);
    }

    @Override
    public Cart save(Cart cart) {
        log.info("Saving cartId: {}", cart.getCartId());

        RedisCart entity = mapper.toEntity(cart);

        RedisCart savedEntity = redisRepository.save(entity);

        return mapper.toDomain(savedEntity);
    }
}
