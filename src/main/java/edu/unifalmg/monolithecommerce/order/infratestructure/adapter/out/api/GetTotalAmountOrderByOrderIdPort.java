package edu.unifalmg.monolithecommerce.order.infratestructure.adapter.out.api;

import edu.unifalmg.monolithecommerce.shared.domain.model.Money;

public interface GetTotalAmountOrderByOrderIdPort {
    Money execute(OrderId orderid);
}
