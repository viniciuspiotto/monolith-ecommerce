package edu.unifalmg.monolithecommerce.order.infratestructure.adapter.in.messengers;

import edu.unifalmg.monolithecommerce.order.application.dto.commands.UpdateOrderStatusCommand;
import edu.unifalmg.monolithecommerce.order.application.port.in.UpdateOrderStatusPort;
import edu.unifalmg.monolithecommerce.order.infratestructure.api.PaymentChangeStatusEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventsListener {

    private final PaymentStatusMapper paymentStatusMapper;
    private final UpdateOrderStatusPort updateOrderStatusPort;

    @EventListener
    public void handlePaymentStatusUpdateEvent(PaymentChangeStatusEvent event) {

        try {
            log.info("Update a order status by payment id: {}", event.orderId());
            UpdateOrderStatusCommand updateOrderStatusCommand = paymentStatusMapper.toCommand(event);
            updateOrderStatusPort.execute(updateOrderStatusCommand);
        } catch (Exception e) {
            log.error("Failed to change status with order with Id: {} {}", event.orderId(), e.getMessage(), e);
        }
    }

}
