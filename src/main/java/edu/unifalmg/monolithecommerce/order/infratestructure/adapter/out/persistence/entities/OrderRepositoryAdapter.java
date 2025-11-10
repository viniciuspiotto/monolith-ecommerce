package edu.unifalmg.monolithecommerce.order.infratestructure.adapter.out.persistence.entities;

import edu.unifalmg.monolithecommerce.order.application.port.out.OrderRepositoryPort;
import edu.unifalmg.monolithecommerce.order.domain.model.Order;
import edu.unifalmg.monolithecommerce.order.infratestructure.adapter.mapper.OrderPersistenceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepositoryPort {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderPersistenceMapper orderPersistenceMapper;

    @Override
    public Order findById(UUID orderId){
        Optional<OrderEntity> orderFound = orderJpaRepository.findById(orderId);
        if (orderFound.isEmpty()) {
            throw new RuntimeException("Order whit this id not found");
        }
        return orderPersistenceMapper.toDomain(orderFound.get());
    }

    @Override
    public Optional<Order> findByCartId(UUID cartId){
        OrderEntity orderFound = orderJpaRepository.findByCartId(cartId);
        return Optional.of(orderPersistenceMapper.toDomain(orderFound));
    }

    @Override
    public Order save(Order Order){
        OrderEntity orderEntity = orderPersistenceMapper.toEntity(Order);
        OrderEntity orderSaved= orderJpaRepository.save(orderEntity);
        return orderPersistenceMapper.toDomain(orderSaved);
    }

}
