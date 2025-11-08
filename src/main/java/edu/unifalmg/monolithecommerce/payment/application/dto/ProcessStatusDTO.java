package edu.unifalmg.monolithecommerce.payment.application.dto;

import edu.unifalmg.monolithecommerce.payment.domain.model.enums.PaymentStatus;

public record ProcessStatusDTO (
        String orderId,
        PaymentStatus status
){
}
