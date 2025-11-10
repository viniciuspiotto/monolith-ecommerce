package edu.unifalmg.monolithecommerce.order.infratestructure.adapter.out.persistence.entities.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ItemOrderEmbeddable {
    UUID id;
    String name;
    BigDecimal price;
}
