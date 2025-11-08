package edu.unifalmg.monolithecommerce.payment.infraestructure.adapter.out.paymentclient;

import edu.unifalmg.monolithecommerce.payment.domain.model.enums.PaymentStatus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MercadoPagoMapper {
    default PaymentStatus toDomain(String status) {
        if (status == null) return PaymentStatus.WAITING;
        return switch (status.toLowerCase()) {
            case "approved", "authorized" -> PaymentStatus.APPROVED;
            case "pending", "in_process", "in_mediation" -> PaymentStatus.WAITING;
            case "rejected", "cancelled", "refunded", "charged_back" -> PaymentStatus.REJECTED;
            default -> PaymentStatus.WAITING;
        };
    }
}
