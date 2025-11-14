package edu.unifalmg.monolithecommerce.payment.infraestructure.adapter.out.persistence.jpa.entities;

import edu.unifalmg.monolithecommerce.payment.domain.model.enums.PaymentClient;
import edu.unifalmg.monolithecommerce.payment.domain.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;

import java.math.BigDecimal;
import java.util.*;

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

    @Transient
    private final transient List<Object> domainEvents = new ArrayList<>();

    @DomainEvents
    public Collection<Object> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    @AfterDomainEventPublication
    public void clearDomainEvents() {
        this.domainEvents.clear();
    }

    public void setDomainEvents(Collection<Object> events) {
        this.domainEvents.addAll(events);
    }
}
