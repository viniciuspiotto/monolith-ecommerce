package edu.unifalmg.monolithecommerce.order.infratestructure.adapter.in.messengers;

import edu.unifalmg.monolithecommerce.cart.infrastructure.api.CartCheckoutEvent;
import edu.unifalmg.monolithecommerce.order.application.dto.commands.CreateOrderCommand;
import edu.unifalmg.monolithecommerce.order.application.port.in.CreateOrderPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CartEventsListener {

    private final CartsMapper cartsMapper;
    private final CreateOrderPort createOrderPort;

    @EventListener
    public void handleCartCheckoutEvent(CartCheckoutEvent event) {
        try {
             CreateOrderCommand createOrderCommand = cartsMapper.toCommand(event);
             createOrderPort.execute(createOrderCommand);
             log.info("Order successfully created from cart [{}]", event.cartId());
        } catch (Exception e) {
            log.error("Failed to create a order with cart Id: {} {}", event.cartId(), e.getMessage(), e);
        }
    }

}
