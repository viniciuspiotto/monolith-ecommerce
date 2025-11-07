package edu.unifalmg.monolithecommerce.catalog.infrastructure.api;

import java.io.Serializable;
import java.util.UUID;

public record ModelId(UUID id) implements Serializable {
}
