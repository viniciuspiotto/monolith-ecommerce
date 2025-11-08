package edu.unifalmg.monolithecommerce.payment.infraestructure.adapter.in.controllers;

import edu.unifalmg.monolithecommerce.payment.application.dto.commands.ProcessPaymentNotificationCommand;
import edu.unifalmg.monolithecommerce.payment.infraestructure.adapter.in.requests.MercadoPagoConfigRequest;
import edu.unifalmg.monolithecommerce.payment.application.port.in.ProcessPaymentNotificationPort;
import edu.unifalmg.monolithecommerce.payment.infraestructure.adapter.in.mapper.PaymentRequestMapper;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/webhooks")
@Log4j2
public class WebHookController {

    private final PaymentRequestMapper paymentRequestMapper;
    private final ProcessPaymentNotificationPort processPaymentUsePort;

    @PostMapping("/mercadopago")
    @PermitAll
    public ResponseEntity<?> webWookController(@RequestBody MercadoPagoConfigRequest request) {

        log.info("The Payment Client (Mercado Pago) will update the payment based on the order ID: {}", request.data().id());
        ProcessPaymentNotificationCommand cmd = paymentRequestMapper.toCommand(request);
        processPaymentUsePort.execute(cmd);
        return ResponseEntity.ok().build();
    }

}
