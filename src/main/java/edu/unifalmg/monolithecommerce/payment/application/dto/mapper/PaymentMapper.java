package edu.unifalmg.monolithecommerce.payment.application.dto.mapper;

import edu.unifalmg.monolithecommerce.payment.application.dto.PaymentDTO;
import edu.unifalmg.monolithecommerce.payment.domain.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    @Mapping(source = "paymentId.id", target = "paymentId")
    PaymentDTO toDTO(Payment payment);
}
