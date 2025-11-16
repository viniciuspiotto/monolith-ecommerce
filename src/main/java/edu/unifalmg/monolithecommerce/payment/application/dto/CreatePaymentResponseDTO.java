package edu.unifalmg.monolithecommerce.payment.application.dto;

import java.util.UUID;

public record CreatePaymentResponseDTO(
        UUID paymentId,
        String preferenceId,
        String url
) {
}
