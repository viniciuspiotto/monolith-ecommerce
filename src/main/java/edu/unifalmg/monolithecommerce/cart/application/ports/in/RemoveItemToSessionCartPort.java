package edu.unifalmg.monolithecommerce.cart.application.ports.in;

import edu.unifalmg.monolithecommerce.cart.application.dtos.CartDTO;
import edu.unifalmg.monolithecommerce.cart.application.dtos.commands.RemoveItemToSessionCartCommand;

public interface RemoveItemToSessionCartPort {
    CartDTO execute(RemoveItemToSessionCartCommand cmd);
}
