package edu.unifalmg.monolithecommerce.payment.application.port.out;

import edu.unifalmg.monolithecommerce.order.infratestructure.api.OrderId;
import edu.unifalmg.monolithecommerce.order.infratestructure.api.OrderItem;
import edu.unifalmg.monolithecommerce.order.infratestructure.api.Payer;
import edu.unifalmg.monolithecommerce.shared.domain.model.Money;

import java.util.List;

public interface OrderServicePort {
    List<OrderItem> getItemListByOrderId(OrderId orderid);
    Payer getPayerByOrderId(OrderId orderid);
    Money getTotalAmountOrderByOrderId(OrderId orderid);
}
