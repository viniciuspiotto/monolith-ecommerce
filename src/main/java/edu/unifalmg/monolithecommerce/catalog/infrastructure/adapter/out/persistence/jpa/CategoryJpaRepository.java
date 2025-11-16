package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.jpa;

import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, UUID> {
    Optional<CategoryEntity> findByName(String name);
}
