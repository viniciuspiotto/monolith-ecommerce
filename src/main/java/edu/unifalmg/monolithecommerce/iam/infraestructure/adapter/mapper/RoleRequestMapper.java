package edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.mapper;

import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.CreateRoleCommand;
import edu.unifalmg.monolithecommerce.iam.application.DTO.requests.CreateRoleRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleRequestMapper {
    CreateRoleCommand toCommand(CreateRoleRequest request);
}
