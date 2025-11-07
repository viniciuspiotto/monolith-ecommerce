package edu.unifalmg.monolithecommerce.cart.application.ports.in;

import edu.unifalmg.monolithecommerce.cart.application.dtos.CartDTO;
import edu.unifalmg.monolithecommerce.cart.application.dtos.commands.AddItemToSessionCartCommand;

public interface AddItemToSessionCartPort {
    CartDTO execute(AddItemToSessionCartCommand cmd);
}
