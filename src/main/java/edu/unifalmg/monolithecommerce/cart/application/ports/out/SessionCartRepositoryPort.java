package edu.unifalmg.monolithecommerce.cart.application.ports.out;

import edu.unifalmg.monolithecommerce.cart.domain.model.Cart;

public interface SessionCartRepositoryPort {
    Cart getCart();
    Cart save(Cart cart);
    void delete();
}