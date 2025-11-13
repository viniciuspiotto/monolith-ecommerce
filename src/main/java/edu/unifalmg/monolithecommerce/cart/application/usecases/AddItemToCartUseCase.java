package edu.unifalmg.monolithecommerce.cart.application.usecases;

import edu.unifalmg.monolithecommerce.cart.application.dtos.CartDTO;
import edu.unifalmg.monolithecommerce.cart.application.dtos.commands.AddItemToCartCommand;
import edu.unifalmg.monolithecommerce.cart.application.mappers.CartMapper;
import edu.unifalmg.monolithecommerce.cart.application.ports.in.AddItemToCartPort;
import edu.unifalmg.monolithecommerce.cart.application.ports.out.CartRepositoryPort;
import edu.unifalmg.monolithecommerce.cart.application.ports.out.CatalogServicePort;
import edu.unifalmg.monolithecommerce.cart.domain.model.Cart;
import edu.unifalmg.monolithecommerce.shared.domain.model.Money;
import edu.unifalmg.monolithecommerce.shared.infraestructure.exception.ResourceNotFoundException;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class AddItemToCartUseCase implements AddItemToCartPort {

    private final CatalogServicePort catalogServicePort;
    private final CartRepositoryPort cartRepositoryPort;

    private final CartMapper cartMapper;

    private final Timer addItemFlowTimer;

    public AddItemToCartUseCase(CatalogServicePort catalogServicePort, CartRepositoryPort cartRepositoryPort, CartMapper cartMapper, MeterRegistry meterRegistry) {
        this.catalogServicePort = catalogServicePort;
        this.cartRepositoryPort = cartRepositoryPort;
        this.cartMapper = cartMapper;
        this.addItemFlowTimer = Timer.builder("ecommerce.cart.add_item.flow")
                .description("Measures the duration of adding an item to a persistent cart")
                .publishPercentileHistogram(true)
                .register(meterRegistry);
    }

    @Transactional
    @Override
    public CartDTO execute(AddItemToCartCommand cmd) {

        return addItemFlowTimer.record(() -> {

            Money unitPrice = catalogServicePort.getModelPrice(cmd.modelId())
                    .orElseThrow(() -> new ResourceNotFoundException("Model not found."));

            Cart cart = cartRepositoryPort.findByCustomerIdAndStatusOpen(cmd.customerId())
                    .orElseGet(() -> {
                        log.info("Creating new cart for customerId: {}", cmd.customerId());
                        return Cart.create(cmd.customerId());
                    });

            cart.addItem(cmd.modelId(), unitPrice, cmd.quantity());

            Cart savedCart = cartRepositoryPort.save(cart);
            log.info("Item added successfully to cartId: {}", savedCart.getCartId());

            return cartMapper.toDTO(savedCart);
        });
    }
}
