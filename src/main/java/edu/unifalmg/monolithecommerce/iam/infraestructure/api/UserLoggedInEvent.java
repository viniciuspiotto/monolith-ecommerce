package edu.unifalmg.monolithecommerce.iam.infraestructure.api;

import java.util.UUID;

public record UserLoggedInEvent(
        UUID customerId
) {}
