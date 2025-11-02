package edu.unifalmg.monolithecommerce.cart.application.usecases;

import edu.unifalmg.monolithecommerce.cart.application.dtos.CartDTO;
import edu.unifalmg.monolithecommerce.cart.application.dtos.commands.AddItemCommand;
import edu.unifalmg.monolithecommerce.cart.application.mappers.CartMapper;
import edu.unifalmg.monolithecommerce.cart.application.ports.in.AddItemToCartPort;
import edu.unifalmg.monolithecommerce.cart.application.ports.out.CartRepositoryPort;
import edu.unifalmg.monolithecommerce.cart.application.ports.out.CatalogServicePort;
import edu.unifalmg.monolithecommerce.cart.domain.model.Cart;
import edu.unifalmg.monolithecommerce.shared.domain.model.Money;
import edu.unifalmg.monolithecommerce.shared.infraestructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Service
public class AddItemToCartUseCase implements AddItemToCartPort {

    private final CatalogServicePort catalogServicePort;
    private final CartRepositoryPort cartRepositoryPort;

    private final CartMapper cartMapper;

    @Transactional
    @Override
    public CartDTO execute(AddItemCommand command) {
        Money unitPrice = catalogServicePort.getModelPrice(command.modelId())
                .orElseThrow(() -> new ResourceNotFoundException("Model not found."));

        log.info("unit price: {}", unitPrice.getAmount());

        Cart cart = findOrCreateCart(command.customerId(), command.sessionId());

        log.info("cart {}", cart);

        cart.addItem(command.modelId(), unitPrice, command.quantity());

        Cart savedCart = cartRepositoryPort.save(cart);
        log.info("Item added successfully to cartId: {}", savedCart.getCartId());

        return cartMapper.toDTO(savedCart);
    }

    private Cart findOrCreateCart(UUID customerId, String sessionId) {
        if (customerId != null) {
            log.info("Finding cart for customerId: {}", customerId);
            return cartRepositoryPort.findByCustomerIdAndStatusOpen(customerId)
                    .orElseGet(() -> new Cart(customerId, null));
        }
        if (sessionId != null) {
            log.info("Finding cart for sessionId: {}", sessionId);
            return cartRepositoryPort.findBySessionIdAndStatusOpen(sessionId)
                    .orElseGet(() -> new Cart(null, sessionId));
        }

        throw new IllegalArgumentException("Command must specify customerId or sessionId.");
    }
}
