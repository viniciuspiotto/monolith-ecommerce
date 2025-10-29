package edu.unifalmg.monolithecommerce.catalog.application.port.in.category;

import edu.unifalmg.monolithecommerce.catalog.application.dto.category.CategoryDTO;
import edu.unifalmg.monolithecommerce.catalog.application.dto.category.CreateCategoryCommand;
import edu.unifalmg.monolithecommerce.catalog.application.dto.category.GetCategoryByIdCommand;

public interface GetCategoryByIdPort {
    CategoryDTO execute(GetCategoryByIdCommand command);
}
