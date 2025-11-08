package edu.unifalmg.monolithecommerce.order.infratestructure.adapter.out.api;

import edu.unifalmg.monolithecommerce.shared.domain.model.Money;

import java.util.UUID;

public record Item (UUID id, String name, Money value){
}
