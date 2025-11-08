package edu.unifalmg.monolithecommerce.payment.application.port.out;

import edu.unifalmg.monolithecommerce.order.infratestructure.adapter.out.api.Item;
import edu.unifalmg.monolithecommerce.order.infratestructure.adapter.out.api.OrderId;
import edu.unifalmg.monolithecommerce.order.infratestructure.adapter.out.api.Payer;
import edu.unifalmg.monolithecommerce.shared.domain.model.Money;

import java.util.List;

public interface OrderServicePort {
    List<Item> getItemListByOrderId(OrderId orderid);
    Payer getPayerByOrderId(OrderId orderid);
    Money getTotalAmountOrderByOrderId(OrderId orderid);
}
