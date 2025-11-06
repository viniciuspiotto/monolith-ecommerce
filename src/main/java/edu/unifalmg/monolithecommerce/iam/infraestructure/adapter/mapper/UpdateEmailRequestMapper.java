package edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.mapper;

import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.UpdateEmailCommand;
import edu.unifalmg.monolithecommerce.iam.application.DTO.requests.UpdateEmailRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UpdateEmailRequestMapper {
    @Mappings({
            @Mapping(target = "token", source = "token")
    })
    UpdateEmailCommand toCommand(String token, UpdateEmailRequest request);
}
