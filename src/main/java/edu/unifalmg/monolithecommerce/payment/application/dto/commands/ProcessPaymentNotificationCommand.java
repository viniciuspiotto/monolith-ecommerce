package edu.unifalmg.monolithecommerce.payment.application.dto.commands;

public record ProcessPaymentNotificationCommand (long paymentId, String resourceType) {
}
