package edu.unifalmg.monolithecommerce.cart.application.ports.in;

import edu.unifalmg.monolithecommerce.cart.application.dtos.CartDTO;
import edu.unifalmg.monolithecommerce.cart.application.dtos.commands.RemoveItemCommand;

public interface RemoveItemToCartPort {
    CartDTO execute(RemoveItemCommand cmd);
}
