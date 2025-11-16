package edu.unifalmg.monolithecommerce.order.infratestructure.adapter.in.controllers;

import edu.unifalmg.monolithecommerce.order.application.dto.OrderDTO;
import edu.unifalmg.monolithecommerce.order.application.dto.commands.GetModelDownloadCommand;
import edu.unifalmg.monolithecommerce.order.application.dto.commands.GetOrdersByCustomerIdCommand;
import edu.unifalmg.monolithecommerce.order.application.port.in.GetModelDownloadPort;
import edu.unifalmg.monolithecommerce.order.application.port.in.GetOrdersByCustomerIdPort;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
@Log4j2
public class OrderController {

    private final GetModelDownloadPort getModelDownloadUseCase;
    private final GetOrdersByCustomerIdPort getOrdersByCustomerIdUseCase;

    @GetMapping("{id}/models/{model_id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getDownloadLinkModelLink (@PathVariable UUID id, @PathVariable UUID model_id){
        GetModelDownloadCommand getModelDownloadCommand = new GetModelDownloadCommand(id, model_id);
        URL url = getModelDownloadUseCase.execute(getModelDownloadCommand);
        return ResponseEntity.ok(url);
    }

    @GetMapping()
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getOrdersByCustomer (Authentication authentication){
        String principal = (String) authentication.getPrincipal();
        UUID customerId = UUID.fromString(principal);
        GetOrdersByCustomerIdCommand command = new GetOrdersByCustomerIdCommand(customerId);
        List<OrderDTO> orders = getOrdersByCustomerIdUseCase.execute(command);
        return ResponseEntity.ok(orders);
    }
}
