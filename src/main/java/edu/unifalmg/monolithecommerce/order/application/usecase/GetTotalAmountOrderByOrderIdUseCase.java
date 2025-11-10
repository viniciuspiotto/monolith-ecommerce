package edu.unifalmg.monolithecommerce.order.application.usecase;

import edu.unifalmg.monolithecommerce.order.application.port.out.OrderRepositoryPort;
import edu.unifalmg.monolithecommerce.order.domain.model.Order;
import edu.unifalmg.monolithecommerce.order.infratestructure.api.GetTotalAmountOrderByOrderIdPort;
import edu.unifalmg.monolithecommerce.order.infratestructure.api.OrderId;
import edu.unifalmg.monolithecommerce.shared.domain.model.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTotalAmountOrderByOrderIdUseCase implements GetTotalAmountOrderByOrderIdPort {

    private final OrderRepositoryPort orderRepositoryPort;

    public Money execute(OrderId orderid){
        Order order = orderRepositoryPort.findById(orderid.orderId());
        if(order == null){
            throw new RuntimeException("Failed to find order with orderId.");
        }
        return order.getTotalAmount();
    }
}
