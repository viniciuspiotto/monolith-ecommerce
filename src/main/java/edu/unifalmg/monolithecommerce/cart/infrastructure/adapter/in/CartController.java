package edu.unifalmg.monolithecommerce.cart.infrastructure.adapter.in;

import edu.unifalmg.monolithecommerce.cart.application.dtos.CartDTO;
import edu.unifalmg.monolithecommerce.cart.application.dtos.commands.AddItemCommand;
import edu.unifalmg.monolithecommerce.cart.application.dtos.commands.RemoveItemCommand;
import edu.unifalmg.monolithecommerce.cart.application.ports.in.AddItemToCartPort;
import edu.unifalmg.monolithecommerce.cart.application.ports.in.RemoveItemToCartPort;
import edu.unifalmg.monolithecommerce.cart.infrastructure.adapter.in.dtos.requests.AddItemRequest;
import edu.unifalmg.monolithecommerce.cart.infrastructure.adapter.in.dtos.requests.RemoveItemRequest;
import edu.unifalmg.monolithecommerce.cart.infrastructure.adapter.in.mappers.CartRequestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("cart")
@RequiredArgsConstructor
public class CartController {

    private final AddItemToCartPort addItemToCartPort;
    private final RemoveItemToCartPort removeItemToCartPort;

    private final CartRequestMapper cartRequestMapper;

    @PostMapping("/items")
    public ResponseEntity<CartDTO> addItem(
            @Valid @RequestBody AddItemRequest request
    ) {
        log.info("Received request to add item: {}", request.modelId());

        AddItemCommand cmd = cartRequestMapper.toCommand(request);

        CartDTO updatedCart = addItemToCartPort.execute(cmd);

        log.info("Successfully added a new item to cart: {}", updatedCart.cartId());

        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/items")
    public ResponseEntity<CartDTO> removeItem(
            @Valid @RequestBody RemoveItemRequest request
    ) {
        log.info("Received request to remove item: {}", request.modelId());

        RemoveItemCommand cmd = cartRequestMapper.toCommand(request);

        CartDTO updatedCart = removeItemToCartPort.execute(cmd);

        return ResponseEntity.ok(updatedCart);
    }
}
