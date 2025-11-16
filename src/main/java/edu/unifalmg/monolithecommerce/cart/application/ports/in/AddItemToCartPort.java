package edu.unifalmg.monolithecommerce.cart.application.ports.in;

import edu.unifalmg.monolithecommerce.cart.application.dtos.CartDTO;
import edu.unifalmg.monolithecommerce.cart.application.dtos.commands.AddItemToCartCommand;

public interface AddItemToCartPort {
    CartDTO execute(AddItemToCartCommand command);
}
