package edu.unifalmg.monolithecommerce.iam.application.port.out;

import edu.unifalmg.monolithecommerce.iam.domain.model.Role;

import java.util.UUID;

public interface RoleRepositoryPort {
    Role save(Role role);
    Role findById(UUID id);
    Boolean existsByName(String name);
    Role findByName(String name);
}
