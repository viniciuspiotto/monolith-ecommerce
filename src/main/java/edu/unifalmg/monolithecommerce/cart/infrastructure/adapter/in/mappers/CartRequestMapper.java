package edu.unifalmg.monolithecommerce.cart.infrastructure.adapter.in.mappers;

import edu.unifalmg.monolithecommerce.cart.application.dtos.commands.AddItemCommand;
import edu.unifalmg.monolithecommerce.cart.infrastructure.adapter.in.dtos.requests.AddItemRequest;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.api.ModelId;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CartRequestMapper {
    AddItemCommand toCommand(AddItemRequest request);

    default ModelId toModelId(UUID id) {
        if (id == null) {
            return null;
        }
        return new ModelId(id);
    }
}
