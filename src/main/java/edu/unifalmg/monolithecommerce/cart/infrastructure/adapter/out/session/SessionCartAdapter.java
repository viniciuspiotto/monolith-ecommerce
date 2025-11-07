package edu.unifalmg.monolithecommerce.cart.infrastructure.adapter.out.session;

import edu.unifalmg.monolithecommerce.cart.application.ports.out.SessionCartRepositoryPort;
import edu.unifalmg.monolithecommerce.cart.domain.model.Cart;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;

@Component
@SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Log4j2
public class SessionCartAdapter implements SessionCartRepositoryPort, Serializable {

    private Cart cart;

    public SessionCartAdapter() {
        this.cart = Cart.create();
        log.info("New session cart created. Cart ID: {}", this.cart.getCartId());
    }

    @Override
    public Cart getCart() {
        log.debug("Retrieving cart from session. Cart ID: {}", this.cart.getCartId());
        return this.cart;
    }

    @Override
    public Cart save(Cart cart) {
        this.cart = cart;
        log.debug("Saving cart to session. Cart ID: {}", this.cart.getCartId());
        return this.cart;
    }

    @Override
    public void delete() {
        log.info("Deleting cart from session. Cart ID: {}", this.cart.getCartId());
        this.cart = Cart.create();
        log.info("New empty cart created for session. New Cart ID: {}", this.cart.getCartId());
    }
}
