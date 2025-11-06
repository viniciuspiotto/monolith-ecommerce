package edu.unifalmg.monolithecommerce.catalog.application.port.in;

import edu.unifalmg.monolithecommerce.catalog.application.dto.CategoryDTO;
import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.GetCategoryByIdCommand;

public interface GetCategoryByIdPort {
    CategoryDTO execute(GetCategoryByIdCommand command);
}
