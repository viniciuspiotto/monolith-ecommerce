package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.mapper;

import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.CreateCategoryCommand;
import edu.unifalmg.monolithecommerce.catalog.application.dto.request.CreateCategoryRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryRequestMapper {
    CreateCategoryCommand toCommand(CreateCategoryRequest request);
}
