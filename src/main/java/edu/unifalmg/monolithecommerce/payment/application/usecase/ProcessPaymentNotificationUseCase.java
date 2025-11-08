package edu.unifalmg.monolithecommerce.payment.application.usecase;

import edu.unifalmg.monolithecommerce.payment.application.dto.ProcessStatusDTO;
import edu.unifalmg.monolithecommerce.payment.application.dto.commands.ProcessPaymentNotificationCommand;
import edu.unifalmg.monolithecommerce.payment.application.port.in.ProcessPaymentNotificationPort;
import edu.unifalmg.monolithecommerce.payment.application.port.out.PaymentClientPort;
import edu.unifalmg.monolithecommerce.payment.application.port.out.PaymentRepositoryPort;
import edu.unifalmg.monolithecommerce.payment.domain.model.Payment;
import edu.unifalmg.monolithecommerce.payment.domain.model.enums.PaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProcessPaymentNotificationUseCase implements ProcessPaymentNotificationPort {

    private final PaymentClientPort paymentClient;
    private final PaymentRepositoryPort paymentRepository;

    @Override
    @Transactional
    public void execute(ProcessPaymentNotificationCommand cmd){

        log.info("Getting a status for payment with paymentId: {}", cmd.paymentId());
        ProcessStatusDTO status = paymentClient.getStatus(cmd.paymentId());
        log.info("Getting a payment with order paymentId: {}", cmd.paymentId());
        Optional<Payment> payment = paymentRepository.findByOrderId(UUID.fromString(status.orderId()));

        if(payment.isEmpty()){
            log.info("Payment with this order paymentId not found.");
            throw new IllegalArgumentException("Payment with this preference paymentId not found");
        }

        log.info("Updating payment status for paymentId {}: from {} to {}", cmd.paymentId(), payment.get().getStatus(), status.status());
        payment.get().changeStatus(status.status());
        paymentRepository.update(payment.get());

    }
}
