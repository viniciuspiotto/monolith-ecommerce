package edu.unifalmg.monolithecommerce.order.application.usecase;

import edu.unifalmg.monolithecommerce.order.application.dto.OrderDTO;
import edu.unifalmg.monolithecommerce.order.application.dto.commands.UpdateOrderStatusCommand;
import edu.unifalmg.monolithecommerce.order.application.mapper.OrderMapper;
import edu.unifalmg.monolithecommerce.order.application.port.in.UpdateOrderStatusPort;
import edu.unifalmg.monolithecommerce.order.application.port.out.OrderRepositoryPort;
import edu.unifalmg.monolithecommerce.order.domain.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateOrderStatusUseCase implements UpdateOrderStatusPort {

    private final OrderRepositoryPort orderRepositoryPort;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderDTO execute (UpdateOrderStatusCommand cmd){
        Optional<Order> order = orderRepositoryPort.findById(cmd.orderId());
        if(order.isEmpty()){
            throw new IllegalArgumentException("A order with this id not found");
        }
        log.info("Change a status to: {}", cmd.orderStatus());
        order.get().changeOrderStatus(cmd.orderStatus());
        Order orderUpdated = orderRepositoryPort.save(order.get());
        return orderMapper.toDTO(orderUpdated);
    }
}
