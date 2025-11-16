package edu.unifalmg.monolithecommerce.order.application.usecase;

import edu.unifalmg.monolithecommerce.catalog.infrastructure.api.ModelId;
import edu.unifalmg.monolithecommerce.order.application.dto.commands.GetModelDownloadCommand;
import edu.unifalmg.monolithecommerce.order.application.port.in.GetModelDownloadPort;
import edu.unifalmg.monolithecommerce.order.application.port.out.OrderCatalogServicePort;
import edu.unifalmg.monolithecommerce.order.application.port.out.OrderRepositoryPort;
import edu.unifalmg.monolithecommerce.order.domain.model.Order;
import edu.unifalmg.monolithecommerce.order.domain.model.enums.OrderStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetModelDownloadUseCase implements GetModelDownloadPort {

    private final OrderRepositoryPort orderRepository;
    private final OrderCatalogServicePort catalogService;

    @Override
    @Transactional
    public URL execute(GetModelDownloadCommand cmd){

        Optional<Order> order = orderRepository.findById(cmd.orderId());

        if(order.isEmpty()){
            throw new IllegalArgumentException("Order with this id not found");
        }

        if(order.get().getOrderStatus() != OrderStatus.COMPLETED){
            throw new IllegalArgumentException("Order with this id is not completed");
        }

        boolean modelExistsInOrder = order.get().getOrderItems().stream()
                .anyMatch(item -> Objects.equals(item.getId(), cmd.modelId()));

        if(!modelExistsInOrder){
            throw new IllegalArgumentException("Model with this id not found in order ");
        }

        return catalogService.getDownloadLinkModel(new ModelId(cmd.modelId()));

    }

}
