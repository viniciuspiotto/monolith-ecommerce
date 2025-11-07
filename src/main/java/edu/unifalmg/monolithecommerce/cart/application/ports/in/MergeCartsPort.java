package edu.unifalmg.monolithecommerce.cart.application.ports.in;

import java.util.UUID;

public interface MergeCartsPort {
    void execute(UUID customerId);
}
