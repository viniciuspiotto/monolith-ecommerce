package edu.unifalmg.monolithecommerce.cart.infrastructure.adapter.out.persistence.mappers;

import edu.unifalmg.monolithecommerce.cart.domain.model.Cart;
import edu.unifalmg.monolithecommerce.cart.domain.model.CartItem;
import edu.unifalmg.monolithecommerce.cart.infrastructure.adapter.out.persistence.entities.RedisCart;
import edu.unifalmg.monolithecommerce.cart.infrastructure.adapter.out.persistence.entities.RedisCartItem;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.api.ModelId;
import edu.unifalmg.monolithecommerce.shared.domain.model.Money;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CartPersistenceMapper {

    RedisCart toEntity(Cart domain);

    @Mapping(source = "unitPrice.amount", target = "unitPrice")
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
                entity.getSessionId(),
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

    default UUID map(ModelId modelId) {
        if (modelId == null) {
            return null;
        }
        return modelId.id();
    }
}