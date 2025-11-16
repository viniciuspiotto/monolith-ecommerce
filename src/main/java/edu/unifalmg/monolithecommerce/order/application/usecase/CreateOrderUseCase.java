package edu.unifalmg.monolithecommerce.order.application.usecase;

import edu.unifalmg.monolithecommerce.catalog.infrastructure.api.GetModelNameByIdPort;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.api.ModelId;
import edu.unifalmg.monolithecommerce.order.application.dto.OrderDTO;
import edu.unifalmg.monolithecommerce.order.application.dto.commands.CreateOrderCommand;
import edu.unifalmg.monolithecommerce.order.application.mapper.OrderMapper;
import edu.unifalmg.monolithecommerce.order.application.port.in.CreateOrderPort;
import edu.unifalmg.monolithecommerce.order.application.port.out.OrderRepositoryPort;
import edu.unifalmg.monolithecommerce.order.domain.model.Order;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class CreateOrderUseCase implements CreateOrderPort {

    private final OrderRepositoryPort orderRepositoryPort;
    private final GetModelNameByIdPort getModelNameByIdPort;
    private final OrderMapper orderMapper;

    private final Timer checkoutFlowTimer;

    public CreateOrderUseCase(OrderRepositoryPort orderRepositoryPort,
                              OrderMapper orderMapper,
                              GetModelNameByIdPort getModelNameByIdPort,
                              MeterRegistry meterRegistry) {

        this.orderRepositoryPort = orderRepositoryPort;
        this.getModelNameByIdPort = getModelNameByIdPort;
        this.orderMapper = orderMapper;

        this.checkoutFlowTimer = Timer.builder("ecommerce.order.checkout.flow")
                .description("Measures the duration of the complete checkout flow")
                .publishPercentileHistogram(true)
                .register(meterRegistry);
    }

    @Override
    @Transactional
    @WithSpan("usecase.createOrder")
    public OrderDTO execute(CreateOrderCommand cmd){
        log.info("Creating a order with cart id {} for a customer id: {}", cmd.cartId(), cmd.customerId());

        return checkoutFlowTimer.record(() -> {
            if(orderRepositoryPort.findByCartId(cmd.cartId()).isPresent()){
                throw new IllegalArgumentException("Order already exists for this cart");
            }

            Order order = Order.create(
                    cmd.cartId(),
                    cmd.customerId(),
                    cmd.totalAmount()
            );


            for (CreateOrderCommand.OrderItemCommand orderItemCommand : cmd.orderItemCommandList()) {
                log.info("Creating a new item in order: {}", orderItemCommand.modelId());
                String modelName = getModelNameByIdPort.execute(new ModelId(orderItemCommand.modelId()));
                order.addItem(orderItemCommand.modelId(), modelName, orderItemCommand.value());
            }

            Order savedOrder = orderRepositoryPort.save(order);

            log.info("Order created successfully");
            return orderMapper.toDTO(savedOrder);
        });
    }
}
