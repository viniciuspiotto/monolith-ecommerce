package edu.unifalmg.monolithecommerce.payment.infraestructure.adapter.in.controllers;

import edu.unifalmg.monolithecommerce.payment.application.dto.CreatePaymentResponseDTO;
import edu.unifalmg.monolithecommerce.payment.application.dto.PaymentDTO;
import edu.unifalmg.monolithecommerce.payment.application.dto.commands.CreatePaymentCommand;
import edu.unifalmg.monolithecommerce.payment.application.dto.commands.GetPaymentByIdCommand;
import edu.unifalmg.monolithecommerce.payment.application.port.in.CreatePaymentPort;
import edu.unifalmg.monolithecommerce.payment.application.port.in.GetPaymentByIdPort;
import edu.unifalmg.monolithecommerce.payment.domain.model.enums.PaymentClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Log4j2
public class PaymentController {

    private final CreatePaymentPort createPaymentUseCase;
    private final GetPaymentByIdPort getPaymentByIdUseCase;

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> createPayment(@PathVariable UUID id) {

        log.info("Creating a new payment to Order paymentId: {}", id);

        CreatePaymentCommand createPaymentCommand = new CreatePaymentCommand(id, PaymentClient.MERCADO_PAGO);
        CreatePaymentResponseDTO createPaymentResponseDTO = createPaymentUseCase.execute(createPaymentCommand);
        return ResponseEntity.status(HttpStatus.OK).body(createPaymentResponseDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getPaymentById(@PathVariable UUID id) {

        log.info("Getting a payment to Order paymentId: {}", id);

        GetPaymentByIdCommand getPaymentByIdCommand = new GetPaymentByIdCommand(id);
        PaymentDTO paymentDTO = getPaymentByIdUseCase.execute(getPaymentByIdCommand);
        return ResponseEntity.status(HttpStatus.OK).body(paymentDTO);
    }

}
