package edu.unifalmg.monolithecommerce.order.infratestructure.adapter.out.persistence.entities;

import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID> {
    OrderEntity findByCartId(UUID cartId);
    List<OrderEntity> findByCustomerId(UUID customerId);
}
