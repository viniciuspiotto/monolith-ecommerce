package edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.mapper;

import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.UpdateUserInformationCommand;
import edu.unifalmg.monolithecommerce.iam.application.DTO.requests.UpdateUserInformationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UpdateUserInformationRequestMapper {
    @Mappings({
            @Mapping(target = "token", source = "token")
    })
    UpdateUserInformationCommand toCommand(String token, UpdateUserInformationRequest request);
}
