package edu.unifalmg.monolithecommerce.order.infratestructure.adapter.out.persistence.entities;

import edu.unifalmg.monolithecommerce.order.application.port.out.OrderRepositoryPort;
import edu.unifalmg.monolithecommerce.order.domain.model.Order;
import edu.unifalmg.monolithecommerce.order.infratestructure.adapter.out.persistence.entities.mapper.OrderPersistenceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepositoryPort {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderPersistenceMapper orderPersistenceMapper;

    @Override
    public Optional<Order> findById(UUID orderId){
        Optional<OrderEntity> orderFound = orderJpaRepository.findById(orderId);
        if (orderFound.isEmpty()) {
            throw new RuntimeException("Order whit this id not found");
        }
        return Optional.of(orderPersistenceMapper.toDomain(orderFound.get()));
    }

    @Override
    public Optional<Order> findByCartId(UUID cartId){
        OrderEntity entity = orderJpaRepository.findByCartId(cartId);
        if (entity == null) return Optional.empty();
        return Optional.of(orderPersistenceMapper.toDomain(entity));
    }

    @Override
    public List<Order> findByCustomerId(UUID customerId){
        List<OrderEntity> ordersFounded = orderJpaRepository.findByCustomerId(customerId);
        return ordersFounded.stream()
                .map(orderPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Order save(Order Order){
        OrderEntity orderEntity = orderPersistenceMapper.toEntity(Order);
        OrderEntity orderSaved= orderJpaRepository.save(orderEntity);
        return orderPersistenceMapper.toDomain(orderSaved);
    }

}
