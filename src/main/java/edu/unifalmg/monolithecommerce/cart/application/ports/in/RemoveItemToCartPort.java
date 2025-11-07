package edu.unifalmg.monolithecommerce.cart.application.ports.in;

import edu.unifalmg.monolithecommerce.cart.application.dtos.CartDTO;
import edu.unifalmg.monolithecommerce.cart.application.dtos.commands.RemoveItemToCartCommand;

public interface RemoveItemToCartPort {
    CartDTO execute(RemoveItemToCartCommand cmd);
}
