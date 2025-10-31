package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.jpa;

import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.entities.ModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ModelJpaRepository extends JpaRepository<ModelEntity, UUID> {
}
