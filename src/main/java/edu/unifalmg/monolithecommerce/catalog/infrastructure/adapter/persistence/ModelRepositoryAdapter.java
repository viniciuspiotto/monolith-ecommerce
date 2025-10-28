package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.persistence;

import edu.unifalmg.monolithecommerce.catalog.application.port.out.ModelRepositoryPort;
import edu.unifalmg.monolithecommerce.catalog.domain.model.Model;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.mapper.ModelPersistenceMapper;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.persistence.entity.ModelEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ModelRepositoryAdapter implements ModelRepositoryPort {

    private final ModelJpaRepository JpaRepository;
    private final ModelPersistenceMapper modelPersistenceMapper;

    @Override
    public Model save(Model model) {
        ModelEntity entityToSave = modelPersistenceMapper.toEntity(model);

        ModelEntity savedEntity = JpaRepository.save(entityToSave);

        return modelPersistenceMapper.toDomain(savedEntity);
    }
}
