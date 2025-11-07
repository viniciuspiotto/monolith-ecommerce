package edu.unifalmg.monolithecommerce.cart.infrastructure.adapter.in.mappers;

import edu.unifalmg.monolithecommerce.cart.application.dtos.commands.AddItemToCartCommand;
import edu.unifalmg.monolithecommerce.cart.application.dtos.commands.AddItemToSessionCartCommand;
import edu.unifalmg.monolithecommerce.cart.application.dtos.commands.RemoveItemCommand;
import edu.unifalmg.monolithecommerce.cart.infrastructure.adapter.in.dtos.requests.AddItemRequest;
import edu.unifalmg.monolithecommerce.cart.infrastructure.adapter.in.dtos.requests.RemoveItemRequest;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.api.ModelId;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CartRequestMapper {
    AddItemToCartCommand toCommand(AddItemRequest request, UUID customerId);
    AddItemToSessionCartCommand toCommand(AddItemRequest request);

    RemoveItemCommand toCommand(RemoveItemRequest request);

    default ModelId toModelId(UUID id) {
        if (id == null) {
            return null;
        }
        return new ModelId(id);
    }
}
