package edu.unifalmg.monolithecommerce.catalog.application.port.out;

import edu.unifalmg.monolithecommerce.catalog.domain.model.Category;

import java.util.UUID;

public interface CategoryRepositoryPort {
    Category save(Category category);
    Category findById(UUID id);
    Boolean existsByName(String name);
}
