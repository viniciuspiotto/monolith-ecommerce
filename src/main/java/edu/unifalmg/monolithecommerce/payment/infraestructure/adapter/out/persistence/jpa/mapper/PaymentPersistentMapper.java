package edu.unifalmg.monolithecommerce.payment.infraestructure.adapter.out.persistence.jpa.mapper;

import edu.unifalmg.monolithecommerce.catalog.domain.model.vo.ModelId;
import edu.unifalmg.monolithecommerce.payment.domain.model.Payment;
import edu.unifalmg.monolithecommerce.payment.domain.model.vo.PaymentId;
import edu.unifalmg.monolithecommerce.payment.infraestructure.adapter.out.persistence.jpa.entities.PaymentEntity;
import edu.unifalmg.monolithecommerce.shared.domain.model.Money;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface PaymentPersistentMapper {

    @Mapping(source = "paymentId.id", target = "id")
    @Mapping(source = "amount.amount", target = "amount")
    PaymentEntity toEntity(Payment payment);

    default Payment toDomain(PaymentEntity paymentEntity){
        if(paymentEntity == null) return null;
        PaymentId paymentId = map(paymentEntity.getId());
        Money amount = map(paymentEntity.getAmount());

        return Payment.rehydrate(
                paymentId,
                paymentEntity.getPreferenceId(),
                paymentEntity.getOrderId(),
                amount,
                paymentEntity.getStatus(),
                paymentEntity.getCreatedAt(),
                paymentEntity.getClient());
    };

    default PaymentId map(UUID id) {
        return new PaymentId(id);
    }
    default Money map(BigDecimal decimal) {
        return new Money(decimal);
    }
}
