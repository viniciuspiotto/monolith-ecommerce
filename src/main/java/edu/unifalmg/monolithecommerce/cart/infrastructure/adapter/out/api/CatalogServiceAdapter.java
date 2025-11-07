package edu.unifalmg.monolithecommerce.cart.infrastructure.adapter.out.api;

import edu.unifalmg.monolithecommerce.cart.application.ports.out.CatalogServicePort;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.api.GetModelPricePort;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.api.ModelId;
import edu.unifalmg.monolithecommerce.shared.domain.model.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CatalogServiceAdapter implements CatalogServicePort {

    private final GetModelPricePort getModelPricePort;

    @Override
    public Optional<Money> getModelPrice(ModelId modelId) {
        return getModelPricePort.execute(modelId);
    }
}
