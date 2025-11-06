package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.jpa;

import edu.unifalmg.monolithecommerce.catalog.application.port.out.ModelRepositoryPort;
import edu.unifalmg.monolithecommerce.catalog.domain.model.Model;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.entities.ModelEntity;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.mapper.ModelPersistenceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ModelRepositoryAdapter implements ModelRepositoryPort {

    private final ModelJpaRepository jpaRepository;
    private final ModelPersistenceMapper modelPersistenceMapper;

    @Override
    public Model create(Model model) {
        ModelEntity entityToSave = modelPersistenceMapper.toEntity(model);

        ModelEntity savedEntity = jpaRepository.save(entityToSave);

        return modelPersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Model> findById(UUID id) {
        Optional<ModelEntity> optionalEntity = jpaRepository.findById(id);

        return optionalEntity.map(modelPersistenceMapper::toDomain);
    }

    @Override
    public Model update(Model model) {
        ModelEntity entityToUpdate = modelPersistenceMapper.toEntity(model);
        ModelEntity updatedEntity = jpaRepository.save(entityToUpdate);

        return modelPersistenceMapper.toDomain(updatedEntity);
    }

    @Override
    public void delete(Model model) {
        log.info("Events to remove {}", model.getDomainEvents());

        ModelEntity entityToRemove = modelPersistenceMapper.toEntity(model);

        jpaRepository.delete(entityToRemove);

        return modelPersistenceMapper.toDomain(entityToRemove);
    }
}
