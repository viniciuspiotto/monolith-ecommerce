package edu.unifalmg.monolithecommerce.payment.domain.model;

import edu.unifalmg.monolithecommerce.payment.domain.model.enums.PaymentClient;
import edu.unifalmg.monolithecommerce.payment.domain.model.enums.PaymentStatus;
import edu.unifalmg.monolithecommerce.payment.domain.model.vo.PaymentId;
import edu.unifalmg.monolithecommerce.shared.domain.model.Money;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class Payment {

    private PaymentId paymentId;
    private String preferenceId;
    private UUID orderId;
    private Money amount;
    private PaymentStatus status;
    private Date createdAt;
    private PaymentClient client;

    public static Payment create(UUID orderId, Money amount, PaymentClient client) {

        if(orderId == null){
            throw new IllegalArgumentException("OrderId cannot be null");
        }

        if(amount == null){
            throw new IllegalArgumentException("Money cannot be null");
        }

        if(client == null){
            throw new IllegalArgumentException("Client cannot be null");
        }

        return Payment.builder()
                .paymentId(new PaymentId(UUID.randomUUID()))
                .orderId(orderId)
                .amount(amount)
                .status(PaymentStatus.WAITING)
                .createdAt(new Date())
                .client(client)
                .build();
    }

    public void setPreferenceId(String preferenceId) {
        if(preferenceId == null || preferenceId.isEmpty()){
            throw new IllegalArgumentException("PreferenceId cannot be null");
        }
        this.preferenceId = preferenceId;
    }

    public void changeStatus(PaymentStatus newStatus) {
        if(newStatus == null){
            throw new IllegalArgumentException("Status cannot be null");
        }
        this.status = newStatus;
    }

    public static Payment rehydrate(
            PaymentId paymentId,
            String preferenceId,
            UUID orderId,
            Money amount,
            PaymentStatus status,
            Date createdAt,
            PaymentClient client
    ) {
        return Payment.builder()
                .paymentId(paymentId)
                .preferenceId(preferenceId)
                .orderId(orderId)
                .amount(amount)
                .status(status)
                .createdAt(createdAt)
                .client(client)
                .build();
    }
}
