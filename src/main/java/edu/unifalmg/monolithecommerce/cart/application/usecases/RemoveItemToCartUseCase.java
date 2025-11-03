package edu.unifalmg.monolithecommerce.cart.application.usecases;

import edu.unifalmg.monolithecommerce.cart.application.dtos.CartDTO;
import edu.unifalmg.monolithecommerce.cart.application.dtos.commands.RemoveItemCommand;
import edu.unifalmg.monolithecommerce.cart.application.mappers.CartMapper;
import edu.unifalmg.monolithecommerce.cart.application.ports.in.RemoveItemToCartPort;
import edu.unifalmg.monolithecommerce.cart.application.ports.out.CartRepositoryPort;
import edu.unifalmg.monolithecommerce.cart.domain.model.Cart;
import edu.unifalmg.monolithecommerce.shared.infraestructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class RemoveItemToCartUseCase implements RemoveItemToCartPort {

    private final CartRepositoryPort cartRepositoryPort;
    private final CartMapper cartMapper;

    @Override
    public CartDTO execute(RemoveItemCommand cmd) {
        Cart cart = findCart(cmd.customerId(), cmd.sessionId());

        cart.removeItem(cmd.modelId());

        Cart updatedCart = cartRepositoryPort.save(cart);

        return cartMapper.toDTO(updatedCart);
    }

    private Cart findCart(UUID customerId, String sessionId) {
        Optional<Cart> cartOptional;

        if (customerId != null) {
            log.info("Finding cart for customerId: {}", customerId);
            cartOptional = cartRepositoryPort.findByCustomerIdAndStatusOpen(customerId);
        } else if (sessionId != null) {
            log.info("Finding cart for sessionId: {}", sessionId);
            cartOptional = cartRepositoryPort.findBySessionIdAndStatusOpen(sessionId);
        } else {
            throw new IllegalArgumentException("Command must specify customerId or sessionId.");
        }

        return cartOptional.orElseThrow(() -> {
            log.warn("No open cart found for customerId [{}] or sessionId [{}]", customerId, sessionId);
            return new ResourceNotFoundException("Cart not found.");
        });
    }
}
