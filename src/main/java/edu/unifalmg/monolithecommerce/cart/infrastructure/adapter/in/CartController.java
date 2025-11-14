package edu.unifalmg.monolithecommerce.cart.infrastructure.adapter.in;

import edu.unifalmg.monolithecommerce.cart.application.dtos.CartDTO;
import edu.unifalmg.monolithecommerce.cart.application.dtos.commands.*;
import edu.unifalmg.monolithecommerce.cart.application.ports.in.*;
import edu.unifalmg.monolithecommerce.cart.infrastructure.adapter.in.dtos.requests.AddItemRequest;
import edu.unifalmg.monolithecommerce.cart.infrastructure.adapter.in.dtos.requests.RemoveItemRequest;
import edu.unifalmg.monolithecommerce.cart.infrastructure.adapter.in.mappers.CartRequestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("carts")
@RequiredArgsConstructor
public class CartController {

    private final AddItemToCartPort addItemToCartPort;
    private final AddItemToSessionCartPort addItemToSessionCartPort;
    private final RemoveItemToCartPort removeItemToCartPort;
    private final RemoveItemToSessionCartPort removeItemToSessionCartPort;
    private final CheckoutCartPort checkoutCartPort;

    private final CartRequestMapper cartRequestMapper;

    @PostMapping("/items")
    public ResponseEntity<CartDTO> addItem(
        @Valid @RequestBody AddItemRequest request,
        Authentication authentication
    ) {
        log.info("Received request to add item: {}", request.modelId());

        CartDTO updatedCart;

        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            String principal = (String) authentication.getPrincipal();
            log.debug("User is authenticated. Principal (Subject): {}", principal);

            UUID customerId;
            try {
                customerId = UUID.fromString(principal);
            } catch (IllegalArgumentException e) {
                log.error("Authenticated principal is not a valid UUID: {}", principal, e);
                return ResponseEntity.badRequest().build();
            }

            AddItemToCartCommand cmd = cartRequestMapper.toCommand(request, customerId);

            updatedCart = addItemToCartPort.execute(cmd);
            log.info("Added a new item to USER cart: {}", updatedCart.cartId());
        } else {
            log.debug("Session user, using session cart");

            AddItemToSessionCartCommand cmd = cartRequestMapper.toCommand(request);

            updatedCart = addItemToSessionCartPort.execute(cmd);
            log.info("Added a new item to SESSION cart: {}", updatedCart.cartId());
        }

        return ResponseEntity.ok(updatedCart);

    }

    @DeleteMapping("/items")
    public ResponseEntity<CartDTO> removeItem(
            @Valid @RequestBody RemoveItemRequest request,
            Authentication authentication
    ) {
        log.info("Received request to remove item: {}", request.modelId());

        CartDTO updatedCart;

        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            String principal = (String) authentication.getPrincipal();
            log.debug("User is authenticated. Principal (Subject): {}", principal);
            UUID customerId;
            try {
                customerId = UUID.fromString(principal);
            } catch (IllegalArgumentException e) {
                log.error("Authenticated principal is not a valid UUID: {}", principal, e);
                return ResponseEntity.badRequest().build();
            }

            RemoveItemToCartCommand cmd = cartRequestMapper.toCommand(request, customerId);
            updatedCart = removeItemToCartPort.execute(cmd);
            log.info("Removed item from USER cart: {}", updatedCart.cartId());

        } else {
            log.debug("Session user, using session cart");

            RemoveItemToSessionCartCommand cmd = cartRequestMapper.toCommand(request);

            updatedCart = removeItemToSessionCartPort.execute(cmd);
            log.info("Removed item from SESSION cart: {}", updatedCart.cartId());
        }

        return ResponseEntity.ok(updatedCart);
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkoutCart(
            Authentication authentication
    ){

        log.info("Received request to checkout cart:");
        if (authentication == null || !authentication.isAuthenticated())
            return ResponseEntity.status(401).body("Authentication required");

        String principal = (String) authentication.getPrincipal();
        UUID customerId = UUID.fromString(principal);
        CheckoutCartCommand command = new CheckoutCartCommand(customerId);
        CartDTO cartDTO = checkoutCartPort.execute(command);
        return ResponseEntity.ok(cartDTO);

    }
}
