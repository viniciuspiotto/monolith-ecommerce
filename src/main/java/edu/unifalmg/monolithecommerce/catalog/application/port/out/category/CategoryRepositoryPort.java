package edu.unifalmg.monolithecommerce.catalog.application.port.out.category;

import edu.unifalmg.monolithecommerce.catalog.domain.category.Category;
import edu.unifalmg.monolithecommerce.catalog.domain.model.Model;

import java.util.UUID;

public interface CategoryRepositoryPort {
    Category save(Category category);
    Category findById(UUID id);
}
