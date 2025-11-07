package edu.unifalmg.monolithecommerce.cart.application.usecases;

import edu.unifalmg.monolithecommerce.cart.application.ports.in.MergeCartsPort;
import edu.unifalmg.monolithecommerce.cart.application.ports.out.CartRepositoryPort;
import edu.unifalmg.monolithecommerce.cart.application.ports.out.SessionCartRepositoryPort;
import edu.unifalmg.monolithecommerce.cart.domain.model.Cart;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class MergeCartsUseCase implements MergeCartsPort {

    private final CartRepositoryPort cartRepositoryPort;
    private final SessionCartRepositoryPort sessionCartRepositoryPort;

    @Override
    @Transactional
    public void execute(UUID customerId) {
        Cart sessionCart = sessionCartRepositoryPort.getCart();

        if (sessionCart.getItems().isEmpty()) {
            log.info("No session cart found to merge for customerId: {}", customerId);
            return;
        }

        log.info("Session cart found with items. Starting merge for customerId: {}", customerId);

        Cart userCart = cartRepositoryPort.findByCustomerIdAndStatusOpen(customerId)
                .orElseGet(() -> Cart.create(customerId));

        userCart.merge(sessionCart);
        cartRepositoryPort.save(userCart);
        sessionCartRepositoryPort.delete();

        log.info("Session cart merged for customerId: {}", customerId);
    }
}
