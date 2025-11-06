package edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.mapper;

import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.LoginCommand;
import edu.unifalmg.monolithecommerce.iam.application.DTO.requests.LoginRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginRequestMapper {
    LoginCommand toCommand(LoginRequest loginRequest);
}
