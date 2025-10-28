package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.persistence;

import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.persistence.entity.ModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ModelJpaRepository extends JpaRepository<ModelEntity, UUID> {
}
