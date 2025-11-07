package edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.mapper;

import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.UpdateEmailCommand;
import edu.unifalmg.monolithecommerce.iam.application.DTO.requests.UpdateEmailRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateEmailRequestMapper {
    UpdateEmailCommand toCommand(String email, UpdateEmailRequest request);
}
