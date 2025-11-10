package edu.unifalmg.monolithecommerce.order.infratestructure.adapter.out.persistence.entities;

import edu.unifalmg.monolithecommerce.order.domain.model.enums.OrderStatus;
import edu.unifalmg.monolithecommerce.order.infratestructure.adapter.out.persistence.entities.embeddable.ItemOrderEmbeddable;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "order_order")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
    @Id
    private UUID id;
    private UUID cartId;
    private UUID customerId;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private BigDecimal totalAmount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Builder.Default
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
    private final List<ItemOrderEmbeddable> orderItems = new ArrayList<>();
}
