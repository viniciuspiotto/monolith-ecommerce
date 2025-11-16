package edu.unifalmg.monolithecommerce.cart.infrastructure.adapter.in.messengers;

import edu.unifalmg.monolithecommerce.cart.application.ports.in.MergeCartsPort;
import edu.unifalmg.monolithecommerce.iam.infraestructure.api.UserLoggedInEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class AuthenticationEventListener {

    private final MergeCartsPort mergeCartsPort;

    @EventListener
    public void onUserLogin(UserLoggedInEvent event) {
        log.info("Authentication event received for customerId: {}", event.customerId());

        try {
            mergeCartsPort.execute(event.customerId());
        } catch (Exception e) {
            log.error("Failed to merge carts for customerId: {}", event.customerId(), e);
        }
    }
}
