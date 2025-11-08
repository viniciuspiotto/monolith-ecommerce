package edu.unifalmg.monolithecommerce.payment.application.port.in;

import edu.unifalmg.monolithecommerce.payment.application.dto.commands.ProcessPaymentNotificationCommand;

public interface ProcessPaymentNotificationPort {
    void execute(ProcessPaymentNotificationCommand cmd);
}
