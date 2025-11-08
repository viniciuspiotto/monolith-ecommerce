package edu.unifalmg.monolithecommerce.payment.application.usecase;

import edu.unifalmg.monolithecommerce.order.infratestructure.adapter.out.api.Item;
import edu.unifalmg.monolithecommerce.order.infratestructure.adapter.out.api.OrderId;
import edu.unifalmg.monolithecommerce.order.infratestructure.adapter.out.api.Payer;
import edu.unifalmg.monolithecommerce.payment.application.dto.CreatePaymentResponseDTO;
import edu.unifalmg.monolithecommerce.payment.application.dto.commands.CreatePaymentCommand;
import edu.unifalmg.monolithecommerce.payment.application.port.in.CreatePaymentPort;
import edu.unifalmg.monolithecommerce.payment.application.port.out.OrderServicePort;
import edu.unifalmg.monolithecommerce.payment.application.port.out.PaymentClientPort;
import edu.unifalmg.monolithecommerce.payment.application.port.out.PaymentRepositoryPort;
import edu.unifalmg.monolithecommerce.payment.domain.model.Payment;
import edu.unifalmg.monolithecommerce.shared.domain.model.Money;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class CreatePaymentUseCase implements CreatePaymentPort {

    private final OrderServicePort orderService;
    private final PaymentClientPort paymentClient;
    private final PaymentRepositoryPort paymentRepository;

    @Transactional
    @Override
    public CreatePaymentResponseDTO execute(CreatePaymentCommand cmd){

        if (paymentRepository.existsByOrderId(cmd.orderId())){
            log.info("A payment with this order paymentId already exists");
            throw new IllegalArgumentException("A payment with this order paymentId already exists");
        }

        OrderId orderId = new OrderId(cmd.orderId());

        Payer payer = orderService.getPayerByOrderId(orderId);
        log.info("Getting a payer with order paymentId {}: {}", cmd.orderId(), payer.email());
        List<Item> items = orderService.getItemListByOrderId(orderId);
        log.info("Getting a list of items with order paymentId: {}", cmd.orderId());
        Money amount = orderService.getTotalAmountOrderByOrderId(orderId);
        log.info("Getting a amount of order with order paymentId {}: {}", cmd.orderId(), amount.getAmount());

        Payment payment = Payment.create(cmd.orderId(), amount, cmd.payment());

        CreatePaymentResponseDTO createPaymentResponseDTO = paymentClient.createPreference(payment, items, payer);

        payment.setPreferenceId(createPaymentResponseDTO.preferenceId());
        log.info("Save the payment to database");
        paymentRepository.create(payment);

        return createPaymentResponseDTO;

    }
}
