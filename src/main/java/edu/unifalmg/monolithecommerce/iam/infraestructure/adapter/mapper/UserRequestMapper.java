package edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.mapper;

import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.CreateUserCommand;
import edu.unifalmg.monolithecommerce.iam.application.DTO.requests.CreateUserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserRequestMapper {
    @Mapping(source = "nationalId", target = "nationalId")
    @Mapping(source = "address", target = "address")
    CreateUserCommand toCommand(CreateUserRequest request);
    CreateUserCommand.AddressCommand toCommand(CreateUserRequest.Address address);
}
