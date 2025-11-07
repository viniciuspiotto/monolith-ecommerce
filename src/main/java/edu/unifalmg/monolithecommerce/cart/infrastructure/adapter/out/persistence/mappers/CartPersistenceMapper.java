package edu.unifalmg.monolithecommerce.cart.infrastructure.adapter.out.persistence.mappers;

import edu.unifalmg.monolithecommerce.cart.domain.model.Cart;
import edu.unifalmg.monolithecommerce.cart.domain.model.CartItem;
import edu.unifalmg.monolithecommerce.cart.domain.model.CartStatus;
import edu.unifalmg.monolithecommerce.cart.infrastructure.adapter.out.persistence.entities.RedisCart;
import edu.unifalmg.monolithecommerce.cart.infrastructure.adapter.out.persistence.entities.RedisCartItem;
import edu.unifalmg.monolithecommerce.shared.domain.model.Money;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CartPersistenceMapper {

    default RedisCart toEntity(Cart domain) {
        if (domain == null) {
            return null;
        }

        RedisCart.RedisCartBuilder entityBuilder = RedisCart.builder()
                .cartId(domain.getCartId())
                .customerId(domain.getCustomerId())
                .status(domain.getStatus())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt());

        if (domain.getItems() != null) {
            entityBuilder.items(
                    domain.getItems().stream()
                            .map(this::toEntityItem)
                            .collect(Collectors.toSet())
            );
        }

        if (domain.getStatus() == CartStatus.OPEN) {
            entityBuilder.timeToLive(2592000L);
        } else {
            entityBuilder.timeToLive(3600L);
        }

        return entityBuilder.build();
    }

    @Mapping(source = "unitPrice.amount", target = "unitPrice")
    @Mapping(source = "modelId.id", target = "modelId")
    RedisCartItem toEntityItem(CartItem domainItem);

    Cart toDomain(RedisCart entity);

    CartItem toDomainItem(RedisCartItem entityItem);

    @ObjectFactory
    default Cart createDomain(RedisCart entity) {
        Set<CartItem> domainItems = new HashSet<>();
        if (entity.getItems() != null) {
            domainItems = entity.getItems().stream()
                    .map(this::createDomainItem)
                    .collect(Collectors.toSet());
        }

        return Cart.rehydrate(
                entity.getCartId(),
                entity.getCustomerId(),
                domainItems,
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    @ObjectFactory
    default CartItem createDomainItem(RedisCartItem entityItem) {
        return CartItem.rehydrate(
                entityItem.getModelId(),
                map(entityItem.getUnitPrice()),
                entityItem.getQuantity()
        );
    }

    default Money map(BigDecimal decimal) {
        if (decimal == null) {
            return null;
        }
        return new Money(decimal);
    }
}