package edu.unifalmg.monolithecommerce.cart.application.mappers;

import edu.unifalmg.monolithecommerce.cart.application.dtos.CartDTO;
import edu.unifalmg.monolithecommerce.cart.domain.model.Cart;
import edu.unifalmg.monolithecommerce.cart.domain.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(source = "totalAmount", target = "totalAmount")
    @Mapping(source = "status", target = "status")
    CartDTO toDTO(Cart cart);

    @Mapping(
            target = "total",
            expression = "java(cartItem.getUnitPrice().multiply(cartItem.getQuantity()))"
    )
    CartDTO.CartItemDTO toItemDTO(CartItem cartItem);
}
