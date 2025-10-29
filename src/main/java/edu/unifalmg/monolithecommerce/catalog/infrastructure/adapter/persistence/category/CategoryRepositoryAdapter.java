package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.persistence.category;

import edu.unifalmg.monolithecommerce.catalog.application.port.out.category.CategoryRepositoryPort;
import edu.unifalmg.monolithecommerce.catalog.domain.category.Category;
import edu.unifalmg.monolithecommerce.catalog.domain.model.Model;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.mapper.ModelPersistenceMapper;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.mapper.category.CategoryPersistenceMapper;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.persistence.ModelJpaRepository;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.persistence.category.entity.CategoryEntity;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.persistence.entity.ModelEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CategoryRepositoryAdapter implements CategoryRepositoryPort {
    private final CategoryJpaRepository JpaRepository;
    private final CategoryPersistenceMapper categoryPersistenceMapper;

    @Override
    public Category save(Category category) {
        CategoryEntity entityToSave = categoryPersistenceMapper.toEntity(category);
        CategoryEntity savedEntity = JpaRepository.save(entityToSave);
        return categoryPersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public Category findById(UUID id) {
        Optional<CategoryEntity> categoryFound = JpaRepository.findById(id);
        if(categoryFound.isEmpty()) {
            throw new RuntimeException("Category with this id not found");
        }
        return categoryPersistenceMapper.toDomain(categoryFound.get());
    }
}
