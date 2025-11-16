package edu.unifalmg.monolithecommerce.iam.application.mapper;

import edu.unifalmg.monolithecommerce.iam.application.DTO.RoleDTO;
import edu.unifalmg.monolithecommerce.iam.domain.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper (componentModel = "spring")
public interface RoleMapper {
    @Mapping(source = "roleId.id", target = "roleId")
    RoleDTO toDTO(Role role);
}
