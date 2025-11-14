package edu.unifalmg.monolithecommerce.order.infratestructure.api;

import edu.unifalmg.monolithecommerce.payment.domain.model.enums.PaymentStatus;

import java.util.UUID;

public record PaymentChangeStatusEvent (
        UUID orderId,
        String paymentStatus
) {
}
