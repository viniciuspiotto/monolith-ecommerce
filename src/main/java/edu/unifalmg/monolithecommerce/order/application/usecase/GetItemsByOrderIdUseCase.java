package edu.unifalmg.monolithecommerce.order.application.usecase;

import edu.unifalmg.monolithecommerce.order.application.port.out.OrderRepositoryPort;
import edu.unifalmg.monolithecommerce.order.domain.model.Order;
import edu.unifalmg.monolithecommerce.order.infratestructure.api.GetItemsByOrderIdPort;
import edu.unifalmg.monolithecommerce.order.infratestructure.api.OrderId;
import edu.unifalmg.monolithecommerce.order.infratestructure.api.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetItemsByOrderIdUseCase implements GetItemsByOrderIdPort {

    private final OrderRepositoryPort orderRepositoryPort;

    public List<OrderItem> execute (OrderId orderId) {
        Optional<Order> order = orderRepositoryPort.findById(orderId.orderId());
        if(order.isEmpty()){
            throw new RuntimeException("Failed to find order with orderId.");
        }
        return order.get().getOrderItems();
    }

}
