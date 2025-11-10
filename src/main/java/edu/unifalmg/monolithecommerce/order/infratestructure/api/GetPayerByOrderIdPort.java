package edu.unifalmg.monolithecommerce.order.infratestructure.api;

public interface GetPayerByOrderIdPort {
    Payer execute(OrderId orderid);
}
