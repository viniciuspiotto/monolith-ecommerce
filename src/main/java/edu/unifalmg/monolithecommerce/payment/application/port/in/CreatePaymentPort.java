package edu.unifalmg.monolithecommerce.payment.application.port.in;

import edu.unifalmg.monolithecommerce.payment.application.dto.CreatePaymentResponseDTO;
import edu.unifalmg.monolithecommerce.payment.application.dto.commands.CreatePaymentCommand;

public interface CreatePaymentPort {
    CreatePaymentResponseDTO execute(CreatePaymentCommand cmd);
}
