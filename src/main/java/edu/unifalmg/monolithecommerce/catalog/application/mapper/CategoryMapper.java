package edu.unifalmg.monolithecommerce.catalog.application.mapper;

import edu.unifalmg.monolithecommerce.catalog.application.dto.CategoryDTO;
import edu.unifalmg.monolithecommerce.catalog.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(source = "categoryId.id", target = "categoryId")
    CategoryDTO toDTO(Category category);
}
