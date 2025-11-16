package edu.unifalmg.monolithecommerce.order.application.usecase;

import edu.unifalmg.monolithecommerce.order.application.dto.OrderDTO;
import edu.unifalmg.monolithecommerce.order.application.dto.commands.GetOrdersByCustomerIdCommand;
import edu.unifalmg.monolithecommerce.order.application.mapper.OrderMapper;
import edu.unifalmg.monolithecommerce.order.application.port.in.GetOrdersByCustomerIdPort;
import edu.unifalmg.monolithecommerce.order.application.port.out.OrderRepositoryPort;
import edu.unifalmg.monolithecommerce.order.domain.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetOrdersByCustomerUseCase implements GetOrdersByCustomerIdPort {

    private final OrderRepositoryPort orderRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> execute(GetOrdersByCustomerIdCommand cmd) {

        List<Order> orders = orderRepository.findByCustomerId(cmd.customerId());

        if(orders == null || orders.isEmpty()) {
            return null;
        }

        log.info("Order Found id {} whith {} items", orders.getFirst().getOrderId(), orders.getFirst().getOrderItems().size());

        return orders.stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }
}
