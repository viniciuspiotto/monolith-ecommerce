package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.persistence.category;

import edu.unifalmg.monolithecommerce.catalog.domain.category.Category;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.persistence.category.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, UUID> {
}
