package edu.unifalmg.monolithecommerce.payment.infraestructure.adapter.out.api;

import edu.unifalmg.monolithecommerce.order.infratestructure.api.*;
import edu.unifalmg.monolithecommerce.payment.application.port.out.OrderServicePort;
import edu.unifalmg.monolithecommerce.shared.domain.model.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderServiceAdapter implements OrderServicePort {

    private final GetItemsByOrderIdPort getItemsByOrderIdPort;
    private final GetPayerByOrderIdPort getPayerByOrderIdPort;
    private final GetTotalAmountOrderByOrderIdPort getTotalAmountOrderByOrderIdPort;

    @Override
    public List<OrderItem> getItemListByOrderId(OrderId orderid){
        return getItemsByOrderIdPort.execute(orderid);
    }

    @Override
    public Payer getPayerByOrderId(OrderId orderid){
        return getPayerByOrderIdPort.execute(orderid);
    }

    @Override
    public Money getTotalAmountOrderByOrderId(OrderId orderid) { return getTotalAmountOrderByOrderIdPort.execute(orderid);}
}
