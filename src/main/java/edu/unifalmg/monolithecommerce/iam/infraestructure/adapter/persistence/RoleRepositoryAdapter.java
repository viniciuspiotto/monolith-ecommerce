package edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.persistence;

import edu.unifalmg.monolithecommerce.iam.application.port.out.RoleRepositoryPort;
import edu.unifalmg.monolithecommerce.iam.domain.model.Role;
import edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.mapper.RolePersistenceMapper;
import edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.persistence.entity.RoleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RoleRepositoryAdapter implements RoleRepositoryPort {
    private final RoleJpaRepository JpaRepository;
    private final RolePersistenceMapper rolePersistenceMapper;

    @Override
    public Role save(Role role) {
        RoleEntity entityToSave = rolePersistenceMapper.toEntity(role);
        RoleEntity savedEntity = JpaRepository.save(entityToSave);
        return rolePersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public Role findById(UUID id) {
        Optional<RoleEntity> categoryFound = JpaRepository.findById(id);
        if (categoryFound.isEmpty()) {
            throw new RuntimeException("Role with this id not found");
        }
        return rolePersistenceMapper.toDomain(categoryFound.get());
    }

    @Override
    public Boolean existsByName(String name) {
        return JpaRepository.existsByName(name);
    }

}
