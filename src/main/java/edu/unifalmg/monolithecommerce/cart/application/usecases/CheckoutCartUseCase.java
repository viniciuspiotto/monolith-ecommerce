package edu.unifalmg.monolithecommerce.cart.application.usecases;

import edu.unifalmg.monolithecommerce.cart.application.dtos.CartDTO;
import edu.unifalmg.monolithecommerce.cart.application.dtos.commands.CheckoutCartCommand;
import edu.unifalmg.monolithecommerce.cart.application.mappers.CartMapper;
import edu.unifalmg.monolithecommerce.cart.application.ports.in.CheckoutCartPort;
import edu.unifalmg.monolithecommerce.cart.application.ports.out.CartRepositoryPort;
import edu.unifalmg.monolithecommerce.cart.infrastructure.api.CartCheckoutEvent;
import edu.unifalmg.monolithecommerce.cart.domain.model.Cart;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class CheckoutCartUseCase implements CheckoutCartPort {

    private final CartRepositoryPort cartRepositoryPort;
    private final CartMapper cartMapper;
    private final ApplicationEventPublisher publisher;

    @Override
    @Transactional
    public CartDTO execute(CheckoutCartCommand cmd){
        log.info("Get a cart by customer id: {}", cmd.customerId());
        Optional<Cart> cart = cartRepositoryPort.findByCustomerIdAndStatusOpen(cmd.customerId());

        if(cart.isEmpty()){
            throw new IllegalArgumentException("This user does not have a cart yet");
        }
        log.info("Do a checkout in a cart");

        cart.get().checkout();

        publisher.publishEvent(cartMapper.toEvent(cart.get()));
        CartDTO cartDTO = cartMapper.toDTO(cart.get());

        cart.get().clearCart();
        cartRepositoryPort.save(cart.get());

        return cartDTO;
    }
}
