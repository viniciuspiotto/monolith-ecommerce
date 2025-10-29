package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.persistence;

import edu.unifalmg.monolithecommerce.catalog.application.port.out.CategoryRepositoryPort;
import edu.unifalmg.monolithecommerce.catalog.domain.model.Category;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.mapper.CategoryPersistenceMapper;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.persistence.entity.CategoryEntity;
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
        if (categoryFound.isEmpty()) {
            throw new RuntimeException("Category with this id not found");
        }
        return categoryPersistenceMapper.toDomain(categoryFound.get());
    }

    @Override
    public Boolean existsByName(String name) {
        return JpaRepository.existsByName(name);
    }
}
