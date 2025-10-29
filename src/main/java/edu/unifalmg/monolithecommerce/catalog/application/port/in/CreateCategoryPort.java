package edu.unifalmg.monolithecommerce.catalog.application.port.in;

import edu.unifalmg.monolithecommerce.catalog.application.dto.CategoryDTO;
import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.CreateCategoryCommand;

public interface CreateCategoryPort {
    CategoryDTO execute(CreateCategoryCommand command);
}
