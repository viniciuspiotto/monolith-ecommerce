package edu.unifalmg.monolithecommerce.payment.application.dto.commands;

import edu.unifalmg.monolithecommerce.payment.domain.model.enums.PaymentClient;

import java.util.UUID;

public record CreatePaymentCommand (UUID orderId, PaymentClient payment) {
}
