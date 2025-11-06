package edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.mapper;

import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.UpdatePasswordCommand;
import edu.unifalmg.monolithecommerce.iam.application.DTO.requests.UpdatePasswordRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdatePasswordRequestMapper {
    UpdatePasswordCommand toCommand(String email, UpdatePasswordRequest request);
}
