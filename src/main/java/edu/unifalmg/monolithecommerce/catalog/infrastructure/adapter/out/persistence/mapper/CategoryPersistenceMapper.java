package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.mapper;

import edu.unifalmg.monolithecommerce.catalog.domain.model.Category;
import edu.unifalmg.monolithecommerce.catalog.domain.model.vo.CategoryId;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.entities.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CategoryPersistenceMapper {
    @Mapping(target = "id", ignore = true)
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
