package edu.unifalmg.monolithecommerce.cart.application.ports.out;

import edu.unifalmg.monolithecommerce.catalog.infrastructure.api.ModelId;
import edu.unifalmg.monolithecommerce.shared.domain.model.Money;

import java.util.Optional;

public interface CatalogServicePort {
    Optional<Money> getModelPrice(ModelId modelId);
}
