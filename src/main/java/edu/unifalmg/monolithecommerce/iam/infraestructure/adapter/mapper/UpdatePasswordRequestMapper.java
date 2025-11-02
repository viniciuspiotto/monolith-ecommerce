package edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.mapper;

import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.UpdatePasswordCommand;
import edu.unifalmg.monolithecommerce.iam.application.DTO.requests.UpdatePasswordRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UpdatePasswordRequestMapper {
    @Mappings({
            @Mapping(target = "token", source = "token")
    })
    UpdatePasswordCommand toCommand(String token, UpdatePasswordRequest request);
}
