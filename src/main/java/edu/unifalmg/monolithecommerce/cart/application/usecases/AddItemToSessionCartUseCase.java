package edu.unifalmg.monolithecommerce.cart.application.usecases;

import edu.unifalmg.monolithecommerce.cart.application.dtos.CartDTO;
import edu.unifalmg.monolithecommerce.cart.application.dtos.commands.AddItemToSessionCartCommand;
import edu.unifalmg.monolithecommerce.cart.application.mappers.CartMapper;
import edu.unifalmg.monolithecommerce.cart.application.ports.in.AddItemToSessionCartPort;
import edu.unifalmg.monolithecommerce.cart.application.ports.out.CatalogServicePort;
import edu.unifalmg.monolithecommerce.cart.application.ports.out.SessionCartRepositoryPort;
import edu.unifalmg.monolithecommerce.cart.domain.model.Cart;
import edu.unifalmg.monolithecommerce.shared.domain.model.Money;
import edu.unifalmg.monolithecommerce.shared.infraestructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class AddItemToSessionCartUseCase implements AddItemToSessionCartPort {

    private final SessionCartRepositoryPort sessionCartRepositoryPort;
    private final CatalogServicePort catalogServicePort;

    private final CartMapper cartMapper;

    @Override
    public CartDTO execute(AddItemToSessionCartCommand cmd) {
        Money unitPrice = catalogServicePort.getModelPrice(cmd.modelId())
                .orElseThrow(() -> new ResourceNotFoundException("Model not found."));

        Cart cart = sessionCartRepositoryPort.getCart();

        cart.addItem(cmd.modelId(), unitPrice, cmd.quantity());

        Cart savedCart = sessionCartRepositoryPort.save(cart);
        log.info("Item added successfully to session cart: {}", savedCart.getCartId());

        return cartMapper.toDTO(savedCart);
    }
}
