package edu.unifalmg.monolithecommerce.order.infratestructure.adapter.mapper;

import edu.unifalmg.monolithecommerce.order.domain.model.Order;
import edu.unifalmg.monolithecommerce.order.infratestructure.adapter.out.persistence.entities.OrderEntity;
import edu.unifalmg.monolithecommerce.order.infratestructure.adapter.out.persistence.entities.embeddable.ItemOrderEmbeddable;
import edu.unifalmg.monolithecommerce.order.infratestructure.api.OrderId;
import edu.unifalmg.monolithecommerce.order.infratestructure.api.OrderItem;
import edu.unifalmg.monolithecommerce.shared.domain.model.Money;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderPersistenceMapper {

    @Mapping(target = "id", source = "orderId.orderId")
    @Mapping(source = "orderItems", target = "orderItems")
    OrderEntity toEntity(Order order);

    ItemOrderEmbeddable itemOrderToEmbeddable(OrderItem orderItem);

    Order toDomain(OrderEntity orderEntity);

    OrderItem embeddableToOrder(ItemOrderEmbeddable embeddable);

    @ObjectFactory
    default Order createDomain(OrderEntity orderEntity) {
        if (orderEntity == null) {
            return null;
        }

        List<OrderItem> orderItems = (orderEntity.getOrderItems() == null) ? new ArrayList<>() :
                orderEntity.getOrderItems().stream()
                        .map(this::embeddableToOrder)
                        .collect(Collectors.toList());

        return Order.rehydrate(
                orderEntity.getCartId(),
                mapToOrderId(orderEntity.getId()),
                orderEntity.getCustomerId(),
                orderEntity.getOrderStatus(),
                mapToMoney(orderEntity.getTotalAmount()),
                orderEntity.getCreatedAt(),
                orderItems);
    }

    @ObjectFactory
    default OrderItem createDomainItem(ItemOrderEmbeddable embeddable) {
        if (embeddable == null) {
            return null;
        }

        return OrderItem.rehydrate(
                embeddable.getId(),
                embeddable.getName(),
                mapToMoney(embeddable.getPrice())
        );
    }

    default BigDecimal map(Money value) {
        if (value == null) {
            return null;
        }
        return value.getAmount();
    }

    default OrderId mapToOrderId(UUID id) {
        if (id == null) return null;
        return new OrderId(id);
    }

    default Money mapToMoney(BigDecimal value) {
        if (value == null) return null;
        return new Money(value);
    }
}