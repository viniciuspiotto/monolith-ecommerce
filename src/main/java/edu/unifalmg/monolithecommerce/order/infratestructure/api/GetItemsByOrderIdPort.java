package edu.unifalmg.monolithecommerce.order.infratestructure.api;

import java.util.List;

public interface GetItemsByOrderIdPort {
    List<OrderItem> execute(OrderId orderid);
}
