package edu.unifalmg.monolithecommerce.order.application.usecase;

import edu.unifalmg.monolithecommerce.order.application.dto.OrderDTO;
import edu.unifalmg.monolithecommerce.order.application.dto.commands.CreateOrderCommand;
import edu.unifalmg.monolithecommerce.order.application.mapper.OrderMapper;
import edu.unifalmg.monolithecommerce.order.application.port.in.CreateOrderPort;
import edu.unifalmg.monolithecommerce.order.application.port.out.OrderRepositoryPort;
import edu.unifalmg.monolithecommerce.order.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateOrderUseCase implements CreateOrderPort {

    private final OrderRepositoryPort orderRepositoryPort;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderDTO execute(CreateOrderCommand cmd){

        if(orderRepositoryPort.findByCartId(cmd.cartId()).isPresent()){
            throw new IllegalArgumentException("Order already exists for this cart");
        }

        Order order = Order.create(
                cmd.cartId(),
                cmd.customerId(),
                cmd.totalAmount()
        );

        for (CreateOrderCommand.OrderItemCommand orderItemCommand : cmd.orderItemCommandList()) {
            order.addItem(orderItemCommand.modelId(), "temp-temp", orderItemCommand.value());
        }

        Order orderSaved = orderRepositoryPort.save(order);
        return orderMapper.toDTO(orderSaved);

    }

}
