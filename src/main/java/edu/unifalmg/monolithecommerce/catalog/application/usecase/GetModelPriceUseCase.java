package edu.unifalmg.monolithecommerce.catalog.application.usecase;

import edu.unifalmg.monolithecommerce.catalog.infrastructure.api.GetModelPricePort;
import edu.unifalmg.monolithecommerce.catalog.application.port.out.ModelRepositoryPort;
import edu.unifalmg.monolithecommerce.catalog.domain.model.Model;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.api.ModelId;
import edu.unifalmg.monolithecommerce.shared.domain.model.Money;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetModelPriceUseCase implements GetModelPricePort {

    private final ModelRepositoryPort modelRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    public Optional<Money> execute(ModelId modelId) {
        Optional<Model> modelOptional = modelRepositoryPort.findById(modelId.id());

        Optional<Money> priceOptional = modelOptional.map(Model::getPrice);

        if (priceOptional.isEmpty()) {
            log.warn("Model with id {} not found.", modelId);
        }

        return priceOptional;
    }
}
