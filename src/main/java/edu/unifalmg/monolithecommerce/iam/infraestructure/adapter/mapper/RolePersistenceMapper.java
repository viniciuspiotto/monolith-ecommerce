package edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.mapper;

import edu.unifalmg.monolithecommerce.iam.domain.model.Role;
import edu.unifalmg.monolithecommerce.iam.domain.model.vo.RoleId;
import edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.persistence.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface RolePersistenceMapper {
    @Mapping(target = "id", ignore = true)
    RoleEntity toEntity(Role role);

    default Role toDomain(RoleEntity entity) {
        if (entity == null) {
            return null;
        }

        RoleId roleId = map(entity.getId());

        return Role.rehydrate(
                roleId,
                entity.getName(),
                entity.getDescription()
        );
    }

    default RoleId map(UUID id) {
        return new RoleId(id);
    }
}
