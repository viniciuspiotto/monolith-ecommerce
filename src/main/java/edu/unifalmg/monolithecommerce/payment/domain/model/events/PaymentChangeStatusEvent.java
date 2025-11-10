package edu.unifalmg.monolithecommerce.payment.domain.model.events;

import edu.unifalmg.monolithecommerce.payment.domain.model.enums.PaymentStatus;

import java.util.UUID;

public record PaymentChangeStatusEvent (
        UUID orderId,
        PaymentStatus paymentStatus
) {
}
