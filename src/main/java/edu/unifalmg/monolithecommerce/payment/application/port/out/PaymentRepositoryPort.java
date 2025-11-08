package edu.unifalmg.monolithecommerce.payment.application.port.out;

import edu.unifalmg.monolithecommerce.catalog.domain.model.Model;
import edu.unifalmg.monolithecommerce.payment.domain.model.Payment;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepositoryPort {
    Payment create(Payment model);
    Optional<Payment> findById(UUID id);
    Payment update(Payment model);
    Optional<Payment> findByOrderId(UUID orderId);
    Boolean existsByOrderId (UUID orderId);
}
