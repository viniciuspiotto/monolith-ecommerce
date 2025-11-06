package edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.persistence;

import edu.unifalmg.monolithecommerce.iam.domain.model.Role;
import edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.persistence.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleJpaRepository extends JpaRepository<RoleEntity, UUID> {
    boolean existsByName(String name);
    Optional<RoleEntity> findByName(String name);
}
