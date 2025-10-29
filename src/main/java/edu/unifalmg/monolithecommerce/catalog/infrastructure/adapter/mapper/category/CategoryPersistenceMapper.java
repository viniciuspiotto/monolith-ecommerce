package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.mapper.category;

import edu.unifalmg.monolithecommerce.catalog.domain.category.Category;
import edu.unifalmg.monolithecommerce.catalog.domain.category.vo.CategoryId;
import edu.unifalmg.monolithecommerce.catalog.domain.model.Model;
import edu.unifalmg.monolithecommerce.catalog.domain.model.vo.*;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.persistence.category.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CategoryPersistenceMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    CategoryEntity toEntity(Category category);

    default Category toDomain(CategoryEntity entity) {
        if (entity == null) {
            return null;
        }

        CategoryId categoryId = map(entity.getId());

        return Category.rehydrate(
                categoryId,
                entity.getName(),
                entity.getDescription()
        );
    }

    default CategoryId map(UUID id) {
        return new CategoryId(id);
    }
}
