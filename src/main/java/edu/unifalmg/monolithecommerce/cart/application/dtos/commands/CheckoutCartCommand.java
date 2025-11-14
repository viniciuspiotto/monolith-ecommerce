package edu.unifalmg.monolithecommerce.cart.application.dtos.commands;

import java.util.UUID;

public record CheckoutCartCommand (UUID customerId) { }
