package edu.unifalmg.monolithecommerce.catalog.domain.event;

import java.util.UUID;

public record ModelUpdatedEvent(UUID modelId) {
}
