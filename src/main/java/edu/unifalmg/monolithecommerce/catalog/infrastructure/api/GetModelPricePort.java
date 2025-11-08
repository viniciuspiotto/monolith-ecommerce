package edu.unifalmg.monolithecommerce.catalog.infrastructure.api;

import edu.unifalmg.monolithecommerce.shared.domain.model.Money;

import java.util.Optional;

public interface GetModelPricePort {
    Optional<Money> execute(ModelId modelId);
}
