package edu.unifalmg.monolithecommerce.order.infratestructure.adapter.out.api;

import java.util.List;

public interface GetItemsByOrderIdPort {
    List<Item> execute(OrderId orderid);
}
