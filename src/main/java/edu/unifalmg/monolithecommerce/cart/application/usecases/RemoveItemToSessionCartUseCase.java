package edu.unifalmg.monolithecommerce.cart.application.usecases;

import edu.unifalmg.monolithecommerce.cart.application.dtos.CartDTO;
import edu.unifalmg.monolithecommerce.cart.application.dtos.commands.RemoveItemToSessionCartCommand;
import edu.unifalmg.monolithecommerce.cart.application.mappers.CartMapper;
import edu.unifalmg.monolithecommerce.cart.application.ports.in.RemoveItemToSessionCartPort;
import edu.unifalmg.monolithecommerce.cart.application.ports.out.SessionCartRepositoryPort;
import edu.unifalmg.monolithecommerce.cart.domain.model.Cart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RemoveItemToSessionCartUseCase implements RemoveItemToSessionCartPort {

    private final SessionCartRepositoryPort sessionCartRepositoryPort;
    private final CartMapper cartMapper;

    @Override
    public CartDTO execute(RemoveItemToSessionCartCommand cmd) {
        log.info("Removing item {} from session cart", cmd.modelId().id());

        Cart cart = sessionCartRepositoryPort.getCart();

        cart.removeItem(cmd.modelId());

        Cart savedCart = sessionCartRepositoryPort.save(cart);
        log.info("Item removed successfully from SESSION cart: {}", savedCart.getCartId());

        return cartMapper.toDTO(savedCart);
    }
}
