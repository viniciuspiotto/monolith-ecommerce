package edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.mapper;

import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.UpdateUserInformationCommand;
import edu.unifalmg.monolithecommerce.iam.application.DTO.requests.UpdateUserInformationRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateUserInformationRequestMapper {
    UpdateUserInformationCommand toCommand(String email, UpdateUserInformationRequest request);
}
