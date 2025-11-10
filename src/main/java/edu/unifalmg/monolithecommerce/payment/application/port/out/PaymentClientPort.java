package edu.unifalmg.monolithecommerce.payment.application.port.out;

import edu.unifalmg.monolithecommerce.order.infratestructure.api.OrderItem;
import edu.unifalmg.monolithecommerce.order.infratestructure.api.Payer;
import edu.unifalmg.monolithecommerce.payment.application.dto.CreatePaymentResponseDTO;
import edu.unifalmg.monolithecommerce.payment.application.dto.ProcessStatusDTO;
import edu.unifalmg.monolithecommerce.payment.domain.model.Payment;

import java.util.List;

public interface PaymentClientPort {
    CreatePaymentResponseDTO createPreference(Payment payment, List<OrderItem> ListItems, Payer payer);
    ProcessStatusDTO getStatus(long id);
}
