package edu.unifalmg.monolithecommerce.catalog.application.port.in.category;

import edu.unifalmg.monolithecommerce.catalog.application.dto.category.CategoryDTO;
import edu.unifalmg.monolithecommerce.catalog.application.dto.category.CreateCategoryCommand;

public interface CreateCategoryPort {
    CategoryDTO execute(CreateCategoryCommand command);
}
