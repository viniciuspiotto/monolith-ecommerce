package edu.unifalmg.monolithecommerce.cart.application.mappers;

import edu.unifalmg.monolithecommerce.cart.application.dtos.CartDTO;
import edu.unifalmg.monolithecommerce.cart.domain.model.Cart;
import edu.unifalmg.monolithecommerce.cart.domain.model.CartItem;
import edu.unifalmg.monolithecommerce.cart.infrastructure.api.CartCheckoutEvent;
import edu.unifalmg.monolithecommerce.cart.infrastructure.api.CartItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(source = "totalAmount", target = "totalAmount")
    @Mapping(source = "status", target = "status")
    CartDTO toDTO(Cart cart);

    default CartCheckoutEvent toEvent(Cart cart){

        Set<CartItemDTO> cartItemDTOSet = cart.getItems().stream()
                .map(item -> new CartItemDTO(item.getModelId().id(), item.getUnitPrice()))
                .collect(Collectors.toSet());

        return new CartCheckoutEvent(
                cart.getCartId(),
                cart.getCustomerId(),
                Collections.unmodifiableSet(cartItemDTOSet),
                cart.getTotalAmount(),
                cart.getUpdatedAt()
        );

    }

    @Mapping(
            target = "total",
            expression = "java(cartItem.getUnitPrice().multiply(cartItem.getQuantity()))"
    )
    CartDTO.CartItemDTO toItemDTO(CartItem cartItem);
}
