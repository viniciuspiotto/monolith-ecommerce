package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.in.messengers;

import edu.unifalmg.monolithecommerce.catalog.domain.event.ModelRemovedEvent;
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
public class ModelEventsListener {

    private final ModelSearchRepository modelSearchRepository;
    private final ModelSearchMapper modelSearchMapper;
    private final ModelJpaRepository modelJpaRepository;
    private final ModelPersistenceMapper persistenceMapper;

    @TransactionalEventListener
    public void handleModelUpdatedEvent(ModelUpdatedEvent event) {
        try {
            ModelEntity entity = modelJpaRepository.findById(event.modelId())
                    .orElseThrow(() -> new ResourceNotFoundException("Model not found: " + event.modelId()));

            Model model = persistenceMapper.toDomain(entity);

            ModelSearchDocument document = modelSearchMapper.toDocument(model);

            modelSearchRepository.save(document);
            log.info("Model indexed successfully: {}", document.id());

        } catch (Exception e) {
            log.error("Failed to index model with ID [{}]: {}", event.modelId(), e.getMessage(), e);
        }
    }

    @TransactionalEventListener
    public void handleModelRemovedEvent(ModelRemovedEvent event) {
        try {
            modelSearchRepository.deleteById(event.modelId().toString());

            log.info("Model removed from index successfully: {}", event.modelId());
        } catch (Exception e) {
            log.error("Failed to remove model from index with ID [{}]: {}", event.modelId(), e.getMessage(), e);
        }
    }
}
