package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.in.messengers;

import edu.unifalmg.monolithecommerce.catalog.domain.event.ModelUpdatedEvent;
import edu.unifalmg.monolithecommerce.catalog.domain.model.Model;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.elastic.ModelSearchRepository;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.entities.ModelEntity;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.entities.ModelSearchDocument;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.jpa.ModelJpaRepository;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.mapper.ModelPersistenceMapper;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.mapper.ModelSearchMapper;
import edu.unifalmg.monolithecommerce.shared.infraestructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;


@Slf4j
@Component
@RequiredArgsConstructor
public class CatalogSearchIndexerListener {

    private final ModelSearchRepository modelSearchRepository;
    private final ModelSearchMapper modelSearchMapper;
    private final ModelJpaRepository modelJpaRepository;
    private final ModelPersistenceMapper persistenceMapper;

    @TransactionalEventListener
    public void handleModelUpdatedEvent(ModelUpdatedEvent event) {
        try {
            ModelEntity entity = modelJpaRepository.findById(event.getModelId())
                    .orElseThrow(() -> new ResourceNotFoundException("Model not found: " + event.getModelId()));

            Model model = persistenceMapper.toDomain(entity);

            ModelSearchDocument document = modelSearchMapper.toDocument(model);

            modelSearchRepository.save(document);
            log.info("Model indexed successfully: {}", document.id());

        } catch (Exception e) {
            log.error("Failed to index model with ID [{}]: {}", event.getModelId(), e.getMessage(), e);
        }
    }
}
