package edu.unifalmg.monolithecommerce.cart.application.usecases;

import edu.unifalmg.monolithecommerce.cart.application.dtos.CartDTO;
import edu.unifalmg.monolithecommerce.cart.application.dtos.commands.RemoveItemToCartCommand;
import edu.unifalmg.monolithecommerce.cart.application.mappers.CartMapper;
import edu.unifalmg.monolithecommerce.cart.application.ports.in.RemoveItemToCartPort;
import edu.unifalmg.monolithecommerce.cart.application.ports.out.CartRepositoryPort;
import edu.unifalmg.monolithecommerce.cart.domain.model.Cart;
import edu.unifalmg.monolithecommerce.shared.infraestructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RemoveItemToCartUseCase implements RemoveItemToCartPort {

    private final CartRepositoryPort cartRepositoryPort;
    private final CartMapper cartMapper;

    @Override
    public CartDTO execute(RemoveItemToCartCommand cmd) {
        Optional<Cart> cartOptional = cartRepositoryPort.findByCustomerIdAndStatusOpen(cmd.customerId());

        if (cartOptional.isEmpty()) {
            log.warn("Not found cart with id: {}.", cmd.customerId());
            throw new ResourceNotFoundException("Not found cart with id: " + cmd.customerId());
        }

        cartOptional.get().removeItem(cmd.modelId());

        Cart updatedCart = cartRepositoryPort.save(cartOptional.get());

        return cartMapper.toDTO(updatedCart);
    }
}
