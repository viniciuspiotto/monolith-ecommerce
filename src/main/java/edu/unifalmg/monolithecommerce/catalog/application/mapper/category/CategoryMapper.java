package edu.unifalmg.monolithecommerce.catalog.application.mapper.category;

import edu.unifalmg.monolithecommerce.catalog.application.dto.category.CategoryDTO;
import edu.unifalmg.monolithecommerce.catalog.domain.category.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(source = "categoryId.id", target = "categoryId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    CategoryDTO toDTO(Category category);
}
