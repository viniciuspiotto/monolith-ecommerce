package edu.unifalmg.monolithecommerce.payment.infraestructure.adapter.out.persistence.jpa.entities;

import edu.unifalmg.monolithecommerce.payment.domain.model.enums.PaymentClient;
import edu.unifalmg.monolithecommerce.payment.domain.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "payment_models")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {

    @Id
    private UUID id;
    private UUID orderId;
    private BigDecimal amount;
    private String preferenceId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Enumerated(EnumType.STRING)
    private PaymentClient client;

}
