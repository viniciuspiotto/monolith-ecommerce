package edu.unifalmg.monolithecommerce.payment.application.dto;

import edu.unifalmg.monolithecommerce.payment.domain.model.enums.PaymentClient;
import edu.unifalmg.monolithecommerce.payment.domain.model.enums.PaymentStatus;
import edu.unifalmg.monolithecommerce.payment.domain.model.vo.PaymentId;
import edu.unifalmg.monolithecommerce.shared.domain.model.Money;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record PaymentDTO (
         UUID paymentId,
         UUID orderId,
         String preferenceId,
         Money amount,
         PaymentStatus status,
         Date createdAt,
         PaymentClient client
){ }
