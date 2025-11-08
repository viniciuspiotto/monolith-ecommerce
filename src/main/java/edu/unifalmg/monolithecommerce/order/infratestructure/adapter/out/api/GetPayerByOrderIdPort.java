package edu.unifalmg.monolithecommerce.order.infratestructure.adapter.out.api;

public interface GetPayerByOrderIdPort {
    Payer execute(OrderId orderid);
}
