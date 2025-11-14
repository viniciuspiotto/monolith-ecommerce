package edu.unifalmg.monolithecommerce.payment.infraestructure.adapter.out.persistence.jpa;

import edu.unifalmg.monolithecommerce.payment.application.port.out.PaymentRepositoryPort;
import edu.unifalmg.monolithecommerce.payment.domain.model.Payment;
import edu.unifalmg.monolithecommerce.payment.infraestructure.adapter.out.persistence.jpa.entities.PaymentEntity;
import edu.unifalmg.monolithecommerce.payment.infraestructure.adapter.out.persistence.jpa.mapper.PaymentPersistentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentRepositoryAdapter implements PaymentRepositoryPort {

    private final PaymentJpaRepository paymentJpaRepository;
    private final PaymentPersistentMapper paymentPersistentMapper;

    @Override
    public Payment save(Payment payment){
           PaymentEntity paymentEntity = paymentPersistentMapper.toEntity(payment);
           PaymentEntity paymentEntityReturn = paymentJpaRepository.save(paymentEntity);
           return paymentPersistentMapper.toDomain(paymentEntityReturn);
    }

    @Override
    public Boolean existsByOrderId (UUID orderId){
        return paymentJpaRepository.existsByOrderId(orderId);
    }

    @Override
    public Optional<Payment> findByOrderId(UUID orderId){
        Optional<PaymentEntity> paymentEntityOptional = paymentJpaRepository.findByOrderId(orderId);
        return paymentEntityOptional.map(paymentPersistentMapper::toDomain);
    }

    @Override
    public Optional<Payment> findById(UUID id){
        Optional<PaymentEntity> paymentEntityOptional = paymentJpaRepository.findById(id);
        return paymentEntityOptional.map(paymentPersistentMapper::toDomain);
    }

}
