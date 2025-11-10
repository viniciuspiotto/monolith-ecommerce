package edu.unifalmg.monolithecommerce.payment.infraestructure.adapter.out.paymentclient;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import edu.unifalmg.monolithecommerce.order.infratestructure.api.OrderItem;
import edu.unifalmg.monolithecommerce.order.infratestructure.api.Payer;
import edu.unifalmg.monolithecommerce.payment.application.dto.CreatePaymentResponseDTO;
import edu.unifalmg.monolithecommerce.payment.application.dto.ProcessStatusDTO;
import edu.unifalmg.monolithecommerce.payment.application.port.out.PaymentClientPort;
import edu.unifalmg.monolithecommerce.payment.domain.model.Payment;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
@RequiredArgsConstructor
public class MercadoPagoClient implements PaymentClientPort {

    @Value("${mercadoPago.accessToken}")
    private String accessToken;

    @Value("${mercadoPago.notificationURL}")
    private String notificationURL;

    private final MercadoPagoMapper mercadoPagoMapper;

    @PostConstruct
    public void init(){
        MercadoPagoConfig.setAccessToken(this.accessToken);
    }

    @Override
    public CreatePaymentResponseDTO createPreference(Payment payment, List<OrderItem> ListItems, Payer payer)   {

        try {

            PreferenceClient preferenceClient = new PreferenceClient();

            List<PreferenceItemRequest> items = ListItems.stream().map(
                    item -> PreferenceItemRequest.builder()
                            .id(item.getId().toString())
                            .title(item.getName())
                            .quantity(1)
                            .unitPrice(item.getPrice().getAmount())
                            .currencyId(item.getPrice().getCurrency().getCurrencyCode())
                            .build()
            ).toList();

            PreferencePayerRequest payerRequest = PreferencePayerRequest.builder()
                    .name(payer.name())
                    .email(payer.email())
                    .build();

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items)
                    .payer(payerRequest)
                    .notificationUrl(notificationURL)
                    .externalReference(payment.getOrderId().toString())
                    .build();

            log.info("Creating a new preference to id order{}: payer email: {} total amount: {}", payment.getOrderId(), payer.email(), payment.getAmount().getAmount());
            Preference preference = preferenceClient.create(preferenceRequest);
            log.info("Payment preference created successfully for order: {}", payment.getOrderId());

            return new CreatePaymentResponseDTO(
                    payment.getPaymentId().id(),
                    preference.getId(),
                    preference.getInitPoint()
            );

        } catch (MPApiException e) {
            log.error("API error while creating payment preference for order {}: {}", payment.getOrderId(), e.getMessage(), e);
            throw new RuntimeException("API error when creating payment preference", e);
        } catch (MPException e) {
            log.error("SDK error while creating payment preference for order {}: {}", payment.getOrderId(), e.getMessage(), e);
            throw new RuntimeException("SDK error when creating payment preference", e);
        }

    }

    @Override
    public ProcessStatusDTO getStatus(long id) {

        try {
            PaymentClient paymentClient = new PaymentClient();
            com.mercadopago.resources.payment.Payment paymentMercadoPago = paymentClient.get(id);
            log.info("Retrieved status of payment with paymentId {}: {}", id, paymentMercadoPago.getStatus());
            return new ProcessStatusDTO(paymentMercadoPago.getExternalReference(), mercadoPagoMapper.toDomain(paymentMercadoPago.getStatus()));

        } catch (MPApiException e) {
            log.error("API error while retrieving payment status for paymentId {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("API error when retrieving payment status", e);
        } catch (MPException e) {
            log.error("SDK error while retrieving payment status for paymentId {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("SDK error when retrieving payment status", e);
        }

    }


}
