package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.jpa;

import edu.unifalmg.monolithecommerce.catalog.application.port.out.CategoryRepositoryPort;
import edu.unifalmg.monolithecommerce.catalog.domain.model.Category;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.mapper.CategoryPersistenceMapper;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.entities.CategoryEntity;
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
    public Optional<Category> findById(UUID id) {
        Optional<CategoryEntity> categoryFound = JpaRepository.findById(id);

        return categoryFound.map(categoryPersistenceMapper::toDomain);
    }

    @Override
    public Optional<Category> findByName(String name) {
        Optional<CategoryEntity> categoryFound = JpaRepository.findByName(name);

        return categoryFound.map(categoryPersistenceMapper::toDomain);
    }
}