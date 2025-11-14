package edu.unifalmg.monolithecommerce.order.infratestructure.adapter.in.messengers;

import edu.unifalmg.monolithecommerce.cart.infrastructure.api.CartCheckoutEvent;
import edu.unifalmg.monolithecommerce.cart.domain.model.CartItem;
import edu.unifalmg.monolithecommerce.cart.infrastructure.api.CartItemDTO;
import edu.unifalmg.monolithecommerce.order.application.dto.commands.CreateOrderCommand;
import org.mapstruct.Mapper;
import java.util.List;

import java.util.ArrayList;

@Mapper(componentModel = "spring")
public interface CartsMapper {

    default CreateOrderCommand toCommand(CartCheckoutEvent cartCheckoutEvent) {
        List<CreateOrderCommand.OrderItemCommand> orderItemCommand = new ArrayList<>();

        for (CartItemDTO item : cartCheckoutEvent.items()) {
            orderItemCommand.add(
                    new CreateOrderCommand.OrderItemCommand(
                            item.modelId(),
                            item.unitPrice()
                    )
            );
        }
        return new CreateOrderCommand(cartCheckoutEvent.cartId(), cartCheckoutEvent.customerId(), cartCheckoutEvent.totalAmount(), orderItemCommand);
    }

}
