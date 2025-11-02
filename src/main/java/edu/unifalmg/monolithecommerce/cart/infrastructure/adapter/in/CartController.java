package edu.unifalmg.monolithecommerce.cart.infrastructure.adapter.in;

import edu.unifalmg.monolithecommerce.cart.application.dtos.CartDTO;
import edu.unifalmg.monolithecommerce.cart.application.dtos.commands.AddItemCommand;
import edu.unifalmg.monolithecommerce.cart.application.ports.in.AddItemToCartPort;
import edu.unifalmg.monolithecommerce.cart.infrastructure.adapter.in.dtos.requests.AddItemRequest;
import edu.unifalmg.monolithecommerce.cart.infrastructure.adapter.in.mappers.CartRequestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("cart")
@RequiredArgsConstructor
public class CartController {

    private final AddItemToCartPort addItemToCartPort;

    private final CartRequestMapper cartRequestMapper;

    @PostMapping("/items")
    public ResponseEntity<CartDTO> addItem(
            @Valid @RequestBody AddItemRequest request
    ) {
        log.info("Received request to add item: {}", request.modelId());

        AddItemCommand cmd = cartRequestMapper.toCommand(request);

        CartDTO updatedCart = addItemToCartPort.execute(cmd);

        return ResponseEntity.ok(updatedCart);
    }
}
