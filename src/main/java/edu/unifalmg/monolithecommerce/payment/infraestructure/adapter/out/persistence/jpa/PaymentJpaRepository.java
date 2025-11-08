package edu.unifalmg.monolithecommerce.payment.infraestructure.adapter.out.persistence.jpa;

import edu.unifalmg.monolithecommerce.payment.domain.model.Payment;
import edu.unifalmg.monolithecommerce.payment.infraestructure.adapter.out.persistence.jpa.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentJpaRepository extends JpaRepository<PaymentEntity, UUID> {
    Optional<PaymentEntity> findByOrderId(UUID orderId);
    Boolean existsByOrderId(UUID orderId);
}
