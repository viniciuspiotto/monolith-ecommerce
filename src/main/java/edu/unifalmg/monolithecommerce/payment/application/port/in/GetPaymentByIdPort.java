package edu.unifalmg.monolithecommerce.payment.application.port.in;

import edu.unifalmg.monolithecommerce.payment.application.dto.PaymentDTO;
import edu.unifalmg.monolithecommerce.payment.application.dto.commands.GetPaymentByIdCommand;

public interface GetPaymentByIdPort {
    PaymentDTO execute(GetPaymentByIdCommand cmd);
}
